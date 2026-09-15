package com.oneenterprise.securitysession.service.impl;
 
import com.oneenterprise.securitysession.dto.DeviceRequest;
import com.oneenterprise.securitysession.dto.DeviceResponse;
import com.oneenterprise.securitysession.entity.UserDevice;
import com.oneenterprise.securitysession.entity.UserSession;
import com.oneenterprise.securitysession.exception.ResourceNotFoundException;
import com.oneenterprise.securitysession.kafka.SecurityEventProducer;
import com.oneenterprise.securitysession.redis.SessionRedisService;
import com.oneenterprise.securitysession.repository.UserDeviceRepository;
import com.oneenterprise.securitysession.repository.UserSessionRepository;
import com.oneenterprise.securitysession.service.DeviceService;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
import java.util.List;
 
@Service
public class DeviceServiceImpl implements DeviceService {
 
    private final UserDeviceRepository repository;
    private final SecurityEventProducer securityEventProducer;
    private final UserSessionRepository sessionRepository;
    private final SessionRedisService redisService;
 
    public DeviceServiceImpl(UserDeviceRepository repository,
    									SecurityEventProducer securityEventProducer, 
    									UserSessionRepository sessionRepository, 
    									SessionRedisService redisService) {
        this.repository = repository;
        this.securityEventProducer = securityEventProducer;
		this.sessionRepository = sessionRepository;
		this.redisService = redisService;
    }
 
    @Override
    @Transactional
    public DeviceResponse registerDevice(DeviceRequest request) {
 
        UserDevice device = repository
                .findByUserIdAndDeviceId(
                        request.getUserId(),
                        request.getDeviceId()
                )
                .orElse(
                        UserDevice.builder()
                                .userId(request.getUserId())
                                .deviceId(request.getDeviceId())
                                .createdAt(LocalDateTime.now())
                                .build()
                );
 
        device.setDeviceName(request.getDeviceName());
        device.setIpAddress(request.getIpAddress());
        device.setLastUsedAt(LocalDateTime.now());
        device.setActive(true);
 
        UserDevice saved = repository.save(device);
 
        securityEventProducer.publish(
                "CREATE",
                1L,
                saved.getUserId(),
                "UserDevice",
                saved.getId().toString(),
                "User device registered: " + saved.getDeviceId() + " from IP " + saved.getIpAddress()
        );
 
        return map(saved);
    }
 
    @Override
    public DeviceResponse getDevice(Long id) {
 
        UserDevice device = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Device not found with ID: " + id));
 
        return map(device);
    }
 
    @Override
    public List<DeviceResponse> getUserDevices(Long userId) {
 
        return repository
                .findByUserIdAndActiveTrue(userId)
                .stream()
                .map(this::map)
                .toList();
    }
 
    @Override
    @Transactional
    public void deactivateDevice(Long id) {

        UserDevice device =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Device not found with ID: " + id
                                )
                        );

        device.setActive(false);

        UserDevice saved =
                repository.save(device);

        List<UserSession> sessions =
                sessionRepository
                        .findByUserIdAndDeviceIdAndActiveTrue(
                                saved.getUserId(),
                                saved.getDeviceId()
                        );

        for (UserSession session : sessions) {

            session.setActive(false);

            redisService.deleteSession(
                    session.getSessionToken()
            );

            securityEventProducer.publish(
                    "SESSION_INVALID",
                    1L,
                    session.getUserId(),
                    "UserSession",
                    session.getId().toString(),
                    "Session invalidated because device was deactivated"
            );
        }

        sessionRepository.saveAll(sessions);

        securityEventProducer.publish(
                "DELETE",
                1L,
                saved.getUserId(),
                "UserDevice",
                saved.getId().toString(),
                "User device deactivated: "
                        + saved.getDeviceId()
        );
    }
 
    private DeviceResponse map(UserDevice device) {
 
        return DeviceResponse.builder()
                .id(device.getId())
                .userId(device.getUserId())
                .deviceId(device.getDeviceId())
                .deviceName(device.getDeviceName())
                .ipAddress(device.getIpAddress())
                .createdAt(device.getCreatedAt())
                .lastUsedAt(device.getLastUsedAt())
                .active(device.isActive())
                .build();
    }
}
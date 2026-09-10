package com.oneenterprise.userrole.serviceimpl;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.oneenterprise.userrole.dto.UserRoleRequest;
import com.oneenterprise.userrole.dto.UserRoleResponse;
import com.oneenterprise.userrole.entity.UserRole;
import com.oneenterprise.userrole.exception.DuplicateUserRoleException;
import com.oneenterprise.userrole.exception.ResourceNotFoundException;
import com.oneenterprise.userrole.kafka.KafkaProducerService;
import com.oneenterprise.userrole.repository.UserRoleRepository;
import com.oneenterprise.userrole.service.UserRoleService;
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository repository;
    private final KafkaProducerService kafkaProducer;
    private final RedisTemplate<String, Object> redisTemplate;
    public UserRoleServiceImpl(
            UserRoleRepository repository,
            KafkaProducerService kafkaProducer,
            RedisTemplate<String, Object> redisTemplate) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
        this.redisTemplate = redisTemplate;
    }
    // Assign Role to User
    @Override
    public UserRoleResponse assignRoleToUser(UserRoleRequest request) {
        // Duplicate Validation
        if (repository.existsByUserIdAndRoleId(
                request.getUserId(),
                request.getRoleId())) {

            throw new DuplicateUserRoleException(
                    "Role already assigned to this user");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(request.getUserId());
        userRole.setRoleId(request.getRoleId());
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setStatus("ACTIVE");
        UserRole saved = repository.save(userRole);
        // Kafka Event
        kafkaProducer.sendRoleAssignedEvent(
                saved.getUserId(),
                saved.getRoleId());
        // Clear Redis Cache
        redisTemplate.delete("access:" + saved.getUserId());
        return convert(saved);
    }
    // Remove User Role
    @Override
    public void removeUserRole(Long id) {

        UserRole userRole = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User role not found with id: " + id));
        repository.delete(userRole);
        kafkaProducer.sendRoleRemovedEvent(
                userRole.getUserId(),
                userRole.getRoleId());

        redisTemplate.delete("access:" + userRole.getUserId());
    }
    // Get Mapping by ID
    @Override
    public UserRoleResponse getUserRoleById(Long id) {
        UserRole userRole = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User role not found with id: " + id));
        return convert(userRole);
    }
    // Get Roles for User
    @Override
    public List<UserRoleResponse> getUserRoles(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::convert)
                .toList();
    }
    // Get Access Mapping
    @Override
    public List<UserRoleResponse> getUserAccessMapping(Long userId) {
        String key = "access:" + userId;
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return (List<UserRoleResponse>) cached;
        }
        List<UserRoleResponse> access = repository
                .findByUserIdAndStatus(userId, "ACTIVE")
                .stream()
                .map(this::convert)
                .toList();
        redisTemplate.opsForValue()
                .set(key, access, Duration.ofMinutes(10));
        return access;
    }
    // Authorization Lookup
    @Override
    public boolean hasRole(Long userId, Long roleId) {
        return repository.existsByUserIdAndRoleIdAndStatus(
                userId,
                roleId,
                "ACTIVE");
    }
    // Convert Entity to DTO
    private UserRoleResponse convert(UserRole userRole) {
        UserRoleResponse response = new UserRoleResponse();
        response.setId(userRole.getId());
        response.setUserId(userRole.getUserId());
        response.setRoleId(userRole.getRoleId());
        response.setAssignedAt(userRole.getAssignedAt());
        response.setStatus(userRole.getStatus());
        return response;
    }
}
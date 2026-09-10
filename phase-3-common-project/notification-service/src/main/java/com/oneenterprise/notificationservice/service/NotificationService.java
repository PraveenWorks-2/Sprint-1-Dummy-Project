package com.oneenterprise.notificationservice.service;

import com.oneenterprise.notificationservice.dto.NotificationRequestDto;
import com.oneenterprise.notificationservice.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationRequestDto requestDto);

    List<NotificationResponseDto> getAllNotifications();

    NotificationResponseDto getNotificationById(Long id);

    List<NotificationResponseDto> getNotificationsByUserId(Long userId);

    NotificationResponseDto markAsSent(Long id);

    NotificationResponseDto markAsFailed(Long id);

    void deleteNotification(Long id);
}

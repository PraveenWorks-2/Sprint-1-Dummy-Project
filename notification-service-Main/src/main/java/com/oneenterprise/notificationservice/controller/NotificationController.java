package com.oneenterprise.notificationservice.controller;

import com.oneenterprise.notificationservice.dto.ApiResponse;
import com.oneenterprise.notificationservice.dto.NotificationRequestDto;
import com.oneenterprise.notificationservice.dto.NotificationResponseDto;
import com.oneenterprise.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Create Notification
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponseDto>> createNotification(
            @Valid @RequestBody NotificationRequestDto requestDto) {
        NotificationResponseDto response = notificationService.createNotification(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification created successfully", response));
    }

    // Get All Notifications
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getAllNotifications() {
        List<NotificationResponseDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched successfully", notifications));
    }

    // Get Notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponseDto>> getNotificationById(@PathVariable Long id) {
        NotificationResponseDto notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(ApiResponse.success("Notification fetched successfully", notification));
    }

    // Get Notification History for a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getNotificationsByUserId(
            @PathVariable Long userId) {
        List<NotificationResponseDto> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("User notification history fetched successfully", notifications));
    }

    // Mark Notification as Sent
    @PatchMapping("/{id}/mark-sent")
    public ResponseEntity<ApiResponse<NotificationResponseDto>> markAsSent(@PathVariable Long id) {
        NotificationResponseDto response = notificationService.markAsSent(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as sent", response));
    }

    // Mark Notification as Failed
    @PatchMapping("/{id}/mark-failed")
    public ResponseEntity<ApiResponse<NotificationResponseDto>> markAsFailed(@PathVariable Long id) {
        NotificationResponseDto response = notificationService.markAsFailed(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as failed", response));
    }

    // Delete Notification
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully", null));
    }
}

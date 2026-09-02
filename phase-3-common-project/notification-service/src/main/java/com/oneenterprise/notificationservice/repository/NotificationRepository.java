package com.oneenterprise.notificationservice.repository;

import com.oneenterprise.notificationservice.entity.Notification;
import com.oneenterprise.notificationservice.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientUserId(Long recipientUserId);

    List<Notification> findByTenantId(Long tenantId);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByRecipientUserIdAndStatus(Long recipientUserId, NotificationStatus status);
}

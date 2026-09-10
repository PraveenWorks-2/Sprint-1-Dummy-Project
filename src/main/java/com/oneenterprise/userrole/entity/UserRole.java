package com.oneenterprise.userrole.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
@Entity
@Table(
    name = "user_roles",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_user_role",
            columnNames = {"user_id", "role_id"}
        )
    },
    indexes = {
        @Index(
            name = "idx_user_id",
            columnList = "user_id"
        ),
        @Index(
            name = "idx_role_id",
            columnList = "role_id"
        ),
        @Index(
            name = "idx_user_role_status",
            columnList = "user_id, role_id, status"
        )
    }
)
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "user_id",
        nullable = false
    )
    private Long userId;
    @Column(
        name = "role_id",
        nullable = false
    )
    private Long roleId;
    @Column(
        name = "assigned_at",
        nullable = false
    )
    private LocalDateTime assignedAt;
    @Column(
        name = "status",
        nullable = false,
        length = 20
    )
    private String status;
    public UserRole() {
    }
    public UserRole(
            Long userId,
            Long roleId,
            LocalDateTime assignedAt,
            String status) {

        this.userId = userId;
        this.roleId = roleId;
        this.assignedAt = assignedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
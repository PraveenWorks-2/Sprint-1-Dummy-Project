package com.example.permission_management_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "department_permissions",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_department_permission",
            columnNames = {
                "department_id",
                "permission_id"
            }
        )
    }
)
public class DepartmentPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "permission_id",
        nullable = false
    )
    private Permission_Entity permission;

    @Column(nullable = false)
    private Boolean allowed = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (allowed == null) {
            allowed = true;
        }
    }

	public DepartmentPermission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentPermission(Long id, Long departmentId, Permission_Entity permission, Boolean allowed,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.departmentId = departmentId;
		this.permission = permission;
		this.allowed = allowed;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Permission_Entity getPermission() {
		return permission;
	}

	public void setPermission(Permission_Entity permission) {
		this.permission = permission;
	}

	public Boolean getAllowed() {
		return allowed;
	}

	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}

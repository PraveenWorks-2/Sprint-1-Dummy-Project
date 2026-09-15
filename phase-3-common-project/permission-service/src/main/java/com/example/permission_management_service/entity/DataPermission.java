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
    name = "data_permissions",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_data_permission",
            columnNames = {
                "permission_id",
                "data_scope"
            }
        )
    }
)
public class DataPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "permission_id",
        nullable = false
    )
    private Permission_Entity permission;

    @Column(
        name = "data_scope",
        nullable = false,
        length = 50
    )
    private String dataScope;

    @Column(length = 500)
    private String filterExpression;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }
    }

	public DataPermission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Permission_Entity getPermission() {
		return permission;
	}

	public void setPermission(Permission_Entity permission) {
		this.permission = permission;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getFilterExpression() {
		return filterExpression;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DataPermission(Long id, Permission_Entity permission, String dataScope, String filterExpression,
			Boolean active, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.permission = permission;
		this.dataScope = dataScope;
		this.filterExpression = filterExpression;
		this.active = active;
		this.createdAt = createdAt;
	}
}

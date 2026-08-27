package com.oneenterprise.dummyproject.platform.featuremanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "platform_features")
public class PlatformFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public PlatformFeature() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlatformFeature(Long id, String featureName, String description, boolean enabled) {
		super();
		this.id = id;
		this.featureName = featureName;
		this.description = description;
		this.enabled = enabled;
	}

	@Column(nullable = false, unique = true)
    private String featureName;

    private String description;

    @Column(nullable = false)
    private boolean enabled;
}
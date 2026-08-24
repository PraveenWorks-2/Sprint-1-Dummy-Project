package com.oneenterprise.dummyproject.platform.branding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="platform_Entity")
public class Platform_Entity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public String getFaviconUrl() {
		return faviconUrl;
	}

	public void setFaviconUrl(String faviconUrl) {
		this.faviconUrl = faviconUrl;
	}

	public Platform_Entity() {
		// TODO Auto-generated constructor stub
	}

	public Platform_Entity(Long id, String platformName, String logoUrl, String primaryColor, String secondaryColor,
			String faviconUrl) {
		super();
		this.id = id;
		this.platformName = platformName;
		this.logoUrl = logoUrl;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.faviconUrl = faviconUrl;
	}

	@Column(nullable = false)
    private String platformName;

    
    private String logoUrl;

    private String primaryColor;

    private String secondaryColor;

    private String faviconUrl;
}
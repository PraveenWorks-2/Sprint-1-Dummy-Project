package com.oneenterprise.dummyproject.license.management.entity;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public License() {
		super();
		// TODO Auto-generated constructor stub
	}

	public License(Long id, String licenseKey, String licenseType, LocalDate startDate, LocalDate expiryDate,
			boolean active) {
		super();
		this.id = id;
		this.licenseKey = licenseKey;
		this.licenseType = licenseType;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		this.active = active;
	}

	@Column(nullable = false, unique = true)
    private String licenseKey;

    @Column(nullable = false)
    private String licenseType;

    private LocalDate startDate;

    private LocalDate expiryDate;

    @Column(nullable = false)
    private boolean active;
}

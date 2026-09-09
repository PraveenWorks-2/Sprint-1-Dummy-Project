package com.oneenterprise.securitysession.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SsoValidationRequest {

	@NotNull
	private Long userId;
	@NotBlank
	private String provider;
	@NotBlank
	private String externalSubject;
	@NotNull
	private Boolean providerValidated;
	
}

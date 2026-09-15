package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SsoValidationResponse {

	private Long userId;
	private String provider;
	private String externalSubject;
	private boolean valid;
	private boolean accountLocked;
	private String message;
	
}

package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaValidationResponse {

	private Long userId;
	private boolean validated;
	private boolean accountLocked;
	private String message;
	
}

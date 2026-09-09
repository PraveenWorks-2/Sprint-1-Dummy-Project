package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionValidationResponse {

	private boolean valid;
	private Long sessionId;
	private Long userId;
	private String deviceId;
	private boolean redissSessionPresent;
	private boolean databaseSessionActive;
	private boolean deviceActive;
	private boolean expired;
	private String message;
	
}

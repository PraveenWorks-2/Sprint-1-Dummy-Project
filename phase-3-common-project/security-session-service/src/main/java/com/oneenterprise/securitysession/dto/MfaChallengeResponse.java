package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaChallengeResponse {

	private Long userId;
	private String challengeId;
	private Long expiresInSeconds;
	
	/*
	 * Development/testing only. 
	 * In production this should not be returned  
	 */
	private String otp;
}

package com.oneenterprise.dummyproject.authentication.mfa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MfaResponse {

	private boolean success;
	private String message;
	
}

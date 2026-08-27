package com.oneenterprise.dummyproject.authentication.mfa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MfaVerifyRequest {

	@NotBlank(message = "OTP is required")
	private String otp;
	
}

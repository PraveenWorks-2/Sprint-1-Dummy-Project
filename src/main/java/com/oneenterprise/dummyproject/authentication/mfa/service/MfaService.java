package com.oneenterprise.dummyproject.authentication.mfa.service;

public interface MfaService {
	
	void generateOtp(String username);
	
	boolean verifyOtp(String username, String otp);

}

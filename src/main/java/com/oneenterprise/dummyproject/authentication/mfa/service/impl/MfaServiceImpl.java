package com.oneenterprise.dummyproject.authentication.mfa.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.oneenterprise.dummyproject.authentication.mfa.service.MfaService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class MfaServiceImpl implements MfaService {
	
	private final SecureRandom secureRandom =
			new SecureRandom();
	private final Map<String, OtpData> otpStore =
			 new ConcurrentHashMap<>();

	@Override
	public void generateOtp(String username) {
		// Implementation for generating OTP
		String otp = String.format("%06d", 
				secureRandom.nextInt(1_000_000));
		LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
		otpStore.put(username, new OtpData(otp, expiresAt));
		
		System.out.println("*************************************");
		System.out.println("MFA OTP for user " + username ); 
		System.out.println("OTP: " + otp);
		System.out.println("Expires At: " + expiresAt);
		System.out.println("*************************************");
		
	}

	@Override
	public boolean verifyOtp(String username, String otp) {
		// Implementation for verifying OTP
		OtpData otpData = otpStore.get(username);
		if (otpData == null) {
			return false;
		}
		if (LocalDateTime.now().isAfter(otpData.getExpiresAt())) {
			otpStore.remove(username);
			return false;
		}
		boolean valid = otpData.getOtp().equals(otp);
		if (valid) {
			otpStore.remove(username);
		}
		return valid;
	}
	
	@Getter
	@AllArgsConstructor
	private static class OtpData {
		private String otp;
		private LocalDateTime expiresAt;
	}

}

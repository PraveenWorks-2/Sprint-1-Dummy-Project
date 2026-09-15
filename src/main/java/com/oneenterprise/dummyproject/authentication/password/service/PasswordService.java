package com.oneenterprise.dummyproject.authentication.password.service;

import com.oneenterprise.dummyproject.authentication.password.dto.ChangePasswordRequest;

public interface PasswordService {
	
	void changePassword(String username, ChangePasswordRequest request);

}

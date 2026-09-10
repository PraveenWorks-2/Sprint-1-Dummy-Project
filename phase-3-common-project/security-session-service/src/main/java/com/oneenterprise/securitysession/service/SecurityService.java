package com.oneenterprise.securitysession.service;

import com.oneenterprise.securitysession.dto.SecurityValidationResponse;

public interface SecurityService {

	SecurityValidationResponse validateAccount(Long userId);
}

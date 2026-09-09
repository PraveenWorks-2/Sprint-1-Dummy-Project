package com.oneenterprise.securitysession.service;

import com.oneenterprise.securitysession.dto.SsoValidationRequest;
import com.oneenterprise.securitysession.dto.SsoValidationResponse;

public interface SsoService {

	SsoValidationResponse validate(SsoValidationRequest request);
}

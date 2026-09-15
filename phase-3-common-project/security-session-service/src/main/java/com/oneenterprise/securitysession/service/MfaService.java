package com.oneenterprise.securitysession.service;

import com.oneenterprise.securitysession.dto.MfaChallengeRequest;
import com.oneenterprise.securitysession.dto.MfaChallengeResponse;
import com.oneenterprise.securitysession.dto.MfaValidationRequest;
import com.oneenterprise.securitysession.dto.MfaValidationResponse;

public interface MfaService {

	MfaChallengeResponse createChallenge(MfaChallengeRequest request);
	MfaValidationResponse validateChallenge(MfaValidationRequest request);
	
	void enableMfa(Long userId);
	void disableMfa(Long userId);
	
}

package com.oneenterprise.securitysession.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MfaChallengeRequest {

	@NotNull
	private Long userId;
}

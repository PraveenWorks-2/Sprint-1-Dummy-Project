package com.oneenterprise.securitysession.service;

import com.oneenterprise.securitysession.dto.LoginHistoryRequest;
import com.oneenterprise.securitysession.dto.LoginHistoryResponse;

import java.util.List;

public interface LoginHistoryService {

    LoginHistoryResponse recordLogin(LoginHistoryRequest request);

    List<LoginHistoryResponse> getLoginHistory(Long userId);
}
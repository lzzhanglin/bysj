package com.cqjtu.bysj.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getAuthentication();
}

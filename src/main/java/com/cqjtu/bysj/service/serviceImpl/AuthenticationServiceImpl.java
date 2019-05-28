package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//获取当前登录用户信息
@Component
@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }
}

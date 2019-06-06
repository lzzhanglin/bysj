package com.cqjtu.bysj.config;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;

    @Autowired
    private AdminUserService adminUserService;

    public MyAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;





    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        //通过用户名从数据库中查询该用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        //判断密码是否正确
        String dbPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(password,dbPassword)) {
            logger.info("密码错误");
            throw new BadCredentialsException("密码错误");
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        AdminUser userInfo = adminUserService.getUserInfoByJobNo(username);
        ((UsernamePasswordAuthenticationToken) auth).setDetails(userInfo);
        return auth;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.info("是否匹配：{}",UsernamePasswordAuthenticationToken.class.equals(authentication));
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }


}

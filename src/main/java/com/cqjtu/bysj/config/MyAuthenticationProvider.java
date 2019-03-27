package com.cqjtu.bysj.config;

import com.cqjtu.bysj.security.GrantedAuthorityImpl;
import com.cqjtu.bysj.service.serviceImpl.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;

    public MyAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        String username = token.getName();
//        //从数据库找到的用户
//        UserDetails userDetails = null;
//        if (username != null) {
//            userDetails = userDetailsService.loadUserByUsername(username);
//        }
//        //
//        if (userDetails == null) {
//            throw new UsernameNotFoundException("用户名/密码无效");
//        }
//        if (!userDetails.isEnabled()) {
//            throw new DisabledException("用户已被禁用");
//        }
//        if (!userDetails.isAccountNonExpired()) {
//            throw new AccountExpiredException("账号已过期");
//        }
//        if (!userDetails.isAccountNonLocked()) {
//            throw new LockedException("账号已被锁定");
//        }
//        if (!userDetails.isCredentialsNonExpired()) {
//            throw new LockedException("凭证已过期");
//        }
//        //数据库用户的密码 经过加密的
//        String dbPassword = userDetails.getPassword();
//        //前端传回的密码
//        String password = new MyPasswordEncoder().encode(token.getCredentials().toString());
//        //与authentication里面的credentials相比较
//        if (!Objects.equals(dbPassword,password)) {
//            throw new BadCredentialsException("Invalid username/password");
//        }
//        //授权
//        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//    }

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
        logger.info("密码是否正确：{}",passwordEncoder.matches(password,dbPassword));

        // 还可以从数据库中查出该用户所拥有的权限,设置到 authorities 中去,这里模拟数据库查询.
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        return auth;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.info("是否匹配：{}",UsernamePasswordAuthenticationToken.class.equals(authentication));
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }


}

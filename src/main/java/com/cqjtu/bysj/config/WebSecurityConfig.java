package com.cqjtu.bysj.config;

import com.cqjtu.bysj.filter.JwtAuthenticationFilter;
import com.cqjtu.bysj.filter.JwtLoginFilter;
import com.cqjtu.bysj.filter.SimpleCorsFilter;
import com.cqjtu.bysj.service.serviceImpl.MyUserDetailsService;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@EnableGlobalMethodSecurity(jsr250Enabled=true)
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
@ComponentScan({"com.cqjtu.bysj.service.serviceImpl"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new MyUserDetailsService();
        return userDetailsService;
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider authenticationProvider = new MyAuthenticationProvider(userDetailsService());
        return authenticationProvider;

    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());

    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/websocket/**");//允许访问websocket定义的stomp协议资源
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("进入websecurityconfig");

        http.cors().and().csrf().disable()
                .authorizeRequests()

                //允许以下请求
                .antMatchers("/user/hello").permitAll()
                // 所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                //验证登陆
                .addFilter(new JwtLoginFilter(authenticationManager()))
                //验证token

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))

                .formLogin()
                .failureHandler(failureHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());


    }


    private AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setStatus(401);
                PrintWriter out = response.getWriter();
                out.write("{\"status\":\"401\",\"msg\":\"Authentication failure\"}");
                out.flush();
                out.close();
            }
        };
    }
    private AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                response.setStatus(403);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write("{\"status\":\"403\",\"msg\":\"Access denied\"}");
                out.flush();
                out.close();
            }
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setStatus(401);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write("{\"status\":\"401\",\"msg\":\"Not authenticated\"}");
                out.flush();
                out.close();
            }
        };
    }



}

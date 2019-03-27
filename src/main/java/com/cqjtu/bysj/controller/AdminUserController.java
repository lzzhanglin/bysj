package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import com.cqjtu.bysj.service.AdminUserService;
import com.cqjtu.bysj.service.AuthenticationService;
import com.cqjtu.bysj.service.serviceImpl.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class AdminUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Resp login(HttpServletRequest request) {
        Authentication authentication = authenticationService.getAuthentication();
        String jobNo = authentication.getName();
        UserDetails userDetails = adminUserService.getUserInfoByJobNo(jobNo);


            return new Resp(RespCode.SUCCESS,userDetails);
    }



    @RequestMapping("/hello")
    public Resp hello() {
        return new Resp(RespCode.SUCCESS, "hello");
    }
}

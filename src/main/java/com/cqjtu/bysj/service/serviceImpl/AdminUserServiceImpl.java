package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import com.cqjtu.bysj.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Component
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Override
    public AdminUser getUserInfoByJobNo(String jobNo) {
        AdminUser adminUser = adminUserMapper.findUserByJobNo(jobNo);

        Collection<GrantedAuthority> authList = myUserDetailsService.getAuthorities(jobNo);
        adminUser.setAuthorities(authList);
        adminUser.setPassword(null);
        return adminUser;
    }
}

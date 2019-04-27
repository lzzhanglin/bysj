package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Role;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username即为jobNo
        AdminUser adminUser = adminUserMapper.findUserByJobNo(username);
        if (adminUser == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        logger.info("执行MyUserDetailService");
        Collection<GrantedAuthority> authList = getAuthorities(username);
        adminUser.setAuthorities(authList);
        return adminUser;
    }
//获取角色信息 ROLE_TEACHER ROLE_STUDENT
    protected Collection<GrantedAuthority> getAuthorities(String jobNo){
        List<GrantedAuthority> authList = new ArrayList<>();
        List<Role> roleList = adminUserMapper.getRoleListByJobNo(jobNo);
        for (Role role : roleList) {
            authList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authList;
    }


}

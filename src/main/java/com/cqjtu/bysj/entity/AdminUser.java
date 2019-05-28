package com.cqjtu.bysj.entity;

import com.cqjtu.bysj.mapper.AdminUserMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
@Getter
@Setter
public class AdminUser implements UserDetails {


    private Long userId;

    private String jobNo;

    private String email;

    private String phone;

    private String sex;

    private String birthday;

    private String username;

    private String password;



    private Collection<GrantedAuthority> authorities;


    public AdminUser(String jobNo, String password) {
        this.jobNo = jobNo;
        this.password = password;
    }

    public AdminUser() {

    }

    public AdminUser(Long userId, String jobNo, String email, String phone, String sex, String birthday, String username, String password) {
        this.userId = userId;
        this.jobNo = jobNo;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
    }

    public AdminUser(Long userId, String jobNo,
                     String email, String phone,
                     String sex, String birthday,
                     String username, String password,
                     Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.jobNo = jobNo;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
        this.setAuthorities(authorities);
    }



    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }


}

package com.cqjtu.bysj.entity;

import com.cqjtu.bysj.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AdminUser implements UserDetails {

    @Autowired
    private AdminUserMapper adminUserMapper;

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
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.AdminUser;

public interface AdminUserService {
    AdminUser getUserInfoByJobNo(String jobNo);
}

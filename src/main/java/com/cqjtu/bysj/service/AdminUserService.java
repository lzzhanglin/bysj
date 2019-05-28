package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Resp;
import org.springframework.web.multipart.MultipartFile;


public interface AdminUserService {
    AdminUser getUserInfoByJobNo(String jobNo);

    String getPasswordByJobNo(String jobNo);

    void updatePwdByJobNo(String jobNo, String newPassword);

    Resp batchImport(MultipartFile file);
}

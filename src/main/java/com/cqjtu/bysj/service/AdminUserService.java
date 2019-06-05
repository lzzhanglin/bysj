package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Resp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AdminUserService {
    AdminUser getUserInfoByJobNo(String jobNo);

    String getPasswordByJobNo(String jobNo);

    void updatePwdByJobNo(String jobNo, String newPassword);

    Resp batchImport(MultipartFile file);

    List<AdminUser> getAllTeacherAndStudent();

    void deleteUser(String jobNo);

    void updateUserProfile(String jobNo, String email, String phone, String birthday);



}

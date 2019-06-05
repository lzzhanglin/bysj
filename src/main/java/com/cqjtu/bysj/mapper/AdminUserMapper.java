package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Role;
import com.cqjtu.bysj.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {

    String getPasswordByJobNo(@Param("jobNo") String jobNo);

    AdminUser findUserByJobNo(@Param("jobNo") String jobNo);

    List<Role> getRoleListByJobNo(@Param("jobNo") String jobNo);

    void updatePwdByJobNo(@Param("jobNo") String jobNo, @Param("newPassword") String newPassword);

    void batchImport(List<AdminUser> userList);

    void batchImportUserRole(List<UserRole> userRoleList);

    List<AdminUser> getAllTeacherAndStudent();

    void deleteUser(@Param("jobNo") String jobNo);

    void deleteUserRole(@Param("jobNo") String jobNo);

    List<String> getAllJobNo();

    void updateUserProfile(@Param("jobNo") String jobNo,
                           @Param("email") String email,
                           @Param("phone") String phone,
                           @Param("birthday") String birthday);


}

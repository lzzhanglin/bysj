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

}

package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {

    String getPasswordByJobNo(@Param("jobNo") String jobNo);

    AdminUser findUserByJobNo(@Param("jobNo") String jobNo);

    List<Role> getRoleListByJobNo(@Param("jobNo") String jobNo);
}

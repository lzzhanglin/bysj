package com.cqjtu.bysj.entity;

public class UserRole {
    private Long userRoleId;

    private String jobNo;

    private Integer roleId;

    public UserRole() {

    }
    public UserRole(String jobNo, Integer roleId) {
        this.jobNo = jobNo;
        this.roleId = roleId;
    }
}

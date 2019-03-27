package com.cqjtu.bysj.entity;

public class Role {
    private Integer roleId;

    private String roleName;

    public Role(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}

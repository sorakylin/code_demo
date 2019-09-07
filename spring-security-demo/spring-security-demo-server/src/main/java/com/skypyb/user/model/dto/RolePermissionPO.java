package com.skypyb.user.model.dto;

import java.io.Serializable;

/**
 * 角色权限表的实体
 * 角色和权限的映射
 */
public class RolePermissionPO implements Serializable{
    private Long id;
    private Long roleId;
    private Long permissionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}


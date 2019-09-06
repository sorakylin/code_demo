package com.skypyb.user.model.dto;

import java.util.Objects;

//最基本的角色信息
public class MinimumRoleDTO {

    private Long roleId;

    private Long parentId;

    private String name;

    private String enName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinimumRoleDTO that = (MinimumRoleDTO) o;
        return Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleId);
    }
}

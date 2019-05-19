package com.skypyb.entity;


import java.io.Serializable;

/**
 * 权限表
 */
public class Authority implements Serializable {
    private Long authorityId;
    private String authorityName;


    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Authority{");
        sb.append("authorityId=").append(authorityId);
        sb.append(", authorityName='").append(authorityName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
package com.whr.rbac_shiro.domain;

import lombok.Data;

@Data
public class UserRole {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String remarks;
}
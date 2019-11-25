package com.whr.rbac_shiro.domain;

import lombok.Data;

@Data
public class RolePermission {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;
}
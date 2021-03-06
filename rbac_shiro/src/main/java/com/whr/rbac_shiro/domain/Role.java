package com.whr.rbac_shiro.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Role implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 权限集合
     */
    private Set<Permission> permissions;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
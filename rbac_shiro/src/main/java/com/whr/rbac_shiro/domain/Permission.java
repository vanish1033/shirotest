package com.whr.rbac_shiro.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 接口路径
     */
    private String url;
}
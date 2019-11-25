package com.whr.rbac_shiro.domain;

import lombok.Data;

@Data
public class Permission {
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
package com.whr.rbac_shiro.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class User implements Serializable {
    private Integer id;
    /**
     * 角色集合
     */
    private Set<Role> roles;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 加盐
     */
    private String salt;
}
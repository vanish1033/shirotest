package com.whr.shiro.shirotest.domain;

import lombok.Data;

@Data
public class Users {
    private Long id;

    private String username;

    private String password;

    private String passwordSalt;
}
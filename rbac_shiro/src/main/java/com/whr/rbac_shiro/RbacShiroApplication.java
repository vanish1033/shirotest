package com.whr.rbac_shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.whr.rbac_shiro.dao")
@SpringBootApplication
public class RbacShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacShiroApplication.class, args);
    }

}

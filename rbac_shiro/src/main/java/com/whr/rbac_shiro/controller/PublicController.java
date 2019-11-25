package com.whr.rbac_shiro.controller;

import com.whr.rbac_shiro.domain.User;
import com.whr.rbac_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:whr 2019/11/25
 */
@RestController
@RequestMapping("/pub")
public class PublicController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUser/{uid}")
    public User getUser(@PathVariable Integer uid) {
       return userService.findAllUserInfoByUid(uid);
    }

}

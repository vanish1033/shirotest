package com.whr.rbac_shiro.controller;

import com.whr.rbac_shiro.domain.JsonData;
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

    @RequestMapping("/need_login")
    public JsonData needLogin() {
        JsonData jsonData = new JsonData();
        jsonData.setCode(-2);
        jsonData.setData("请输入账号密码登录");
        jsonData.setMsg("请输入账号密码登录");
        return jsonData;
    }

    @RequestMapping("/UnauthorizedUrl")
    public JsonData noPermission() {
        JsonData jsonData = new JsonData();
        jsonData.setCode(-1);
        jsonData.setData("拒绝访问, 你没有权限");
        jsonData.setMsg("拒绝访问, 你没有权限");
        return jsonData;
    }

}

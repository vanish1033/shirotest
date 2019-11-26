package com.whr.rbac_shiro.controller;

import com.google.common.collect.Maps;
import com.whr.rbac_shiro.domain.JsonData;
import com.whr.rbac_shiro.domain.User;
import com.whr.rbac_shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
@RestController
@RequestMapping("/pub")
public class PublicController {

    @Autowired
    UserService userService;

    /**
     * 测试接口
     *
     * @param uid
     * @return
     */
    @RequestMapping("/getUser/{uid}")
    public User getUser(@PathVariable Integer uid) {
        return userService.findAllUserInfoByUid(uid);
    }

    /**
     * 没有登录回调接口
     *
     * @return
     */
    @RequestMapping("/need_login")
    public JsonData needLogin() {
        JsonData jsonData = new JsonData();
        jsonData.setCode(-2);
        jsonData.setData("请输入账号密码登录");
        jsonData.setMsg("请输入账号密码登录");
        return jsonData;
    }

    /**
     * 没有权限回调接口
     *
     * @return
     */
    @RequestMapping("/UnauthorizedUrl")
    public JsonData noPermission() {
        JsonData jsonData = new JsonData();
        jsonData.setCode(-1);
        jsonData.setData("拒绝访问, 你没有权限");
        jsonData.setMsg("拒绝访问, 你没有权限");
        return jsonData;
    }

    @PostMapping("/login")
    public JsonData login(User user) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(user.getId() + "", user.getPassword()));
            JsonData jsonData = new JsonData();
            jsonData.setMsg("登录成功");
            HashMap<Object, Object> data = Maps.newHashMap();
            data.put("session_id", subject.getSession().getId());
            jsonData.setData(data);
            jsonData.setCode(1);

            return jsonData;
        } catch (AuthenticationException e) {
            log.error(e.getMessage(), e);
            JsonData jsonData = new JsonData();
            jsonData.setMsg("登录失败");
            jsonData.setCode(-1);

            return jsonData;
        }

    }


}

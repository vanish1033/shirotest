package com.whr.rbac_shiro.controller;

import com.whr.rbac_shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:whr 2019/11/26
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    @RequestMapping("/test")
    public JsonData adminTest() {
        return JsonData.buildSuccess("admin您好");
    }

}

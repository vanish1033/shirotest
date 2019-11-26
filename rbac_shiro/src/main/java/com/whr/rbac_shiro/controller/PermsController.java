package com.whr.rbac_shiro.controller;

import com.whr.rbac_shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:whr 2019/11/26
 */
@RequestMapping("/video")
@RestController
public class PermsController {

    @RequestMapping("/update")
    public JsonData videoUpdate() {
        return JsonData.buildSuccess("videoUpdata成功！");
    }
}

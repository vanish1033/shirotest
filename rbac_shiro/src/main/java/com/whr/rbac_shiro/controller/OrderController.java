package com.whr.rbac_shiro.controller;

import com.whr.rbac_shiro.domain.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:whr 2019/11/26
 */
@Slf4j
@RestController
@RequestMapping("/authc")
public class OrderController {

    @RequestMapping("/video/findMyOrder")
    public JsonData findMyOrder() {
        return JsonData.buildSuccess("我的订单信息[模拟数据]");
    }


}

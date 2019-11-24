package com.whr.shiro.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;


/**
 * @author:whr 2019/11/24
 */
public class QuickStartTest4_2 {

    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();

    private DefaultSecurityManager securityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        // 初始化数据
        accountRealm.addAccount("whr1", "12261");
        accountRealm.addAccount("whr2", "12262");
        accountRealm.addAccount("whr3", "12263");

        //构建环境
        securityManager.setRealm(accountRealm);
    }

    @Test
    public void testAuthentication() {
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken whr1 = new UsernamePasswordToken("whr1", "12261");
        subject.login(whr1);
        System.out.println("认证结果: " + subject.isAuthenticated());
    }

}

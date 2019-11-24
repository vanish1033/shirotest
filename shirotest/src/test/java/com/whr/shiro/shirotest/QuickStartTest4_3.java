package com.whr.shiro.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import java.security.Security;


/**
 * @author:whr 2019/11/24
 */
public class QuickStartTest4_3 {

    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();

    private DefaultSecurityManager securityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        // 初始化数据源
        accountRealm.addAccount("whr1", "12261", "admin", "user");
        accountRealm.addAccount("whr2", "12262");
        accountRealm.addAccount("whr3", "12263");

        //构建环境
        securityManager.setRealm(accountRealm);
    }

    /**
     * test认证
     */
    @Test
    public void testAuthentication() {

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken whr1 = new UsernamePasswordToken("whr1", "12261");
        subject.login(whr1);
        System.out.println("认证结果: " + subject.isAuthenticated());

        // 是否有对应的角色
        System.out.println("subject.hasRole(\"admin\") = " + subject.hasRole("admin"));

        // 获取 subject 名( 登录的用户名 )
        System.out.println("subject.getPrincipal() = " + subject.getPrincipal());

        // 退出登录
        subject.logout();

        System.out.println("认证结果: " + subject.isAuthenticated());
    }

}

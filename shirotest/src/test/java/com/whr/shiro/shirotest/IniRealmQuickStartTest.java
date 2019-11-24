package com.whr.shiro.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * @author:whr 2019/11/24
 */
public class IniRealmQuickStartTest {


    /**
     * test认证
     */
    @Test
    public void testAuthentication() {
/*        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("whr2", "1232"));
        System.out.println("认证结果: " + subject.isAuthenticated());
        System.out.println("subject.hasRole(\"root\") = " + subject.hasRole("root"));
//        subject.checkPermission("video:buy1");
        System.out.println("subject.isPermitted(\"video:buy\") = " + subject.isPermitted("video:buy"));*/

        Factory<SecurityManager> managerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = managerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        System.out.println("=============================root登录=============================");
        subject.login(new UsernamePasswordToken("whr1", "1231"));
        System.out.println("subject.getPrincipal() = " + subject.getPrincipal());
        System.out.println("认证结果 = " + subject.isAuthenticated());
        System.out.println("subject.hasRole(\"root\") = " + subject.hasRole("root"));
        System.out.println("subject.hasRole(\"uuu\") = " + subject.hasRole("uuu"));
        System.out.println("subject.isPermitted(\"video:123\") = " + subject.isPermitted("video:123"));

        System.out.println("=============================user登录=============================");
        subject.login(new UsernamePasswordToken("whr2", "1232"));
        System.out.println("subject.getPrincipal() = " + subject.getPrincipal());
        System.out.println("subject.isAuthenticated() = " + subject.isAuthenticated());
        System.out.println("subject.hasRole(\"root\") = " + subject.hasRole("root"));
        System.out.println("subject.hasRole(\"user\") = " + subject.hasRole("user"));
        System.out.println("subject.isPermitted(\"video:buy\") = " + subject.isPermitted("video:buy"));
        System.out.println("subject.isPermitted(\"video:look\") = " + subject.isPermitted("video:look"));

    }

}

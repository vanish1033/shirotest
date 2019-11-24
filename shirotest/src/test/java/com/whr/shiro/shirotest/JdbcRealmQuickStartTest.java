package com.whr.shiro.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * @author:whr 2019/11/24
 */
public class JdbcRealmQuickStartTest {


    /**
     * test认证
     */
    @Test
    public void testAuthentication() {
        Factory<SecurityManager> managerFactory = new IniSecurityManagerFactory("classpath:jdbcRealm.ini");
        SecurityManager securityManager = managerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("jack", "123"));
        System.out.println("subject.isAuthenticated() = " + subject.isAuthenticated());

    }

}

package com.whr.shiro.shirotest.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:whr 2019/11/25
 */
public class MyRealmTest {

    private MyRealm myRealm = new MyRealm();

    private DefaultSecurityManager securityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        securityManager.setRealm(myRealm);
    }

    @Test
    public void testAuthentication() {
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("whr1", "1"));
        System.out.println("subject.isAuthenticated() = " + subject.isAuthenticated());
        System.out.println("subject.isPermitted(\"all\") = " + subject.isPermitted("all"));
        System.out.println("subject.isPermitted(\"select\") = " + subject.isPermitted("select"));
    }


}

package com.whr.shiro.shirotest.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
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
        log.info("subject.isAuthenticated() = " + subject.isAuthenticated());
        log.info("subject.isPermitted(\"all\") = " + subject.isPermitted("all"));
        log.info("subject.hasRole(\"user\") = " + subject.hasRole("user"));
        log.info("subject.hasRole(\"root\") = " + subject.hasRole("root"));
        log.info("subject.isPermitted(\"select\") = " + subject.isPermitted("select"));

    }


}

package com.whr.shiro.shirotest;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * @author:whr 2019/11/24
 */
public class JdbcRealmQuickStartTest {


    /**
     * 方式一: 用 ini 配置文件加 jdbc 认证
     */
    @Test
    public void testAuthentication1() {
        Factory<SecurityManager> managerFactory = new IniSecurityManagerFactory("classpath:jdbcRealm.ini");
        SecurityManager securityManager = managerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("jack", "123"));
        System.out.println("subject.isAuthenticated() = " + subject.isAuthenticated());
        System.out.println("subject.isPermitted(\"video:buy\") = " + subject.isPermitted("video:buy"));
        System.out.println("subject.isPermitted(\"video:buy1\") = " + subject.isPermitted("video:buy1"));
        System.out.println("subject.isPermitted(\"video:buy2\") = " + subject.isPermitted("video:buy2"));
    }


    /**
     * 方式二: jdbc 认证
     */
    @Test
    public void testAuthentication2() {
        /**
         * 创建 SecurityManager 和 DataSource
         */
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/shirotest?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        ds.setUsername("root");
        ds.setPassword("111111");

        /**
         * 创建 JdbcRealm 并装配到 securityManager 里
         */
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);


        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealm(jdbcRealm);

        /**
         * 用 SecurityUtils 初始化环境, 开始测试
         */
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("jack", "123"));
        System.out.println("subject.isAuthenticated() = " + subject.isAuthenticated());
        System.out.println("subject.isPermitted(\"video:buy\") = " + subject.isPermitted("video:buy"));
        System.out.println("subject.isPermitted(\"video:buy1\") = " + subject.isPermitted("video:buy1"));
        System.out.println("subject.isPermitted(\"video:buy2\") = " + subject.isPermitted("video:buy2"));
    }

}

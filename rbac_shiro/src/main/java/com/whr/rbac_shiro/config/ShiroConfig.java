package com.whr.rbac_shiro.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.*;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 配置过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("ShiroConfig.shiroFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 需要登录的接口，如果访问一个需要登录的接口，但没有登录，就调用这个接口登录
        shiroFilterFactoryBean.setLoginUrl("/pub/login");
        // 登录成功后的回调接口
        shiroFilterFactoryBean.setSuccessUrl("/pub/common");
        // 访问没有权限访问的接口，就会转跳到这个接口
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/UnauthorizedUrl");
        // 创建拦截路径集合，必须用 LinkedHashMap， 因为 LinkedHashMap 有序，过滤链从上到下顺序执行
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        // 退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要登录就可以访问的路径
        filterChainDefinitionMap.put("/pub/**", "anon");
        // 登录用户才可以访问
        filterChainDefinitionMap.put("/authc/**", "authc");
        // admin角色过滤器
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        // 权限授权拦截器，验证用户是否拥有权限
        filterChainDefinitionMap.put("/video/update", "perms[video_update]");
        // 其他路径过滤器
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置 SecurityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        log.info("ShiroConfig.securityManager()");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }

    /**
     * 配置自定义 Realm
     *
     * @return
     */
    @Bean
    public Realm realm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 配置 CredentialsMatcher 加密规则
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        /**
         * md5加密1024次
         */
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    /**
     * 配置 SessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        return customSessionManager;
    }

}

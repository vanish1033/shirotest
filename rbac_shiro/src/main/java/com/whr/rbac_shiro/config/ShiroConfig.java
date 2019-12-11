package com.whr.rbac_shiro.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

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
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");
        // 登录成功后的回调接口
        shiroFilterFactoryBean.setSuccessUrl("/pub/common");
        // 访问没有权限访问的接口，就会转跳到这个接口
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/UnauthorizedUrl");
        // 创建拦截路径集合，必须用 LinkedHashMap， 因为 LinkedHashMap 有序，过滤链从上到下顺序执行
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        /**
         * 自定义角色过滤器
         */
        Map<String, Filter> filtersMap = Maps.newLinkedHashMap();
        filtersMap.put("roleOrFilter", new CustomRolesOrAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        // 退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要登录就可以访问的路径
        filterChainDefinitionMap.put("/pub/**", "anon");
        // 登录用户才可以访问
        filterChainDefinitionMap.put("/authc/**", "authc");
        // admin角色过滤器
        filterChainDefinitionMap.put("/admin/**", "roleOrFilter[admin,root]");
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

        // 在 securityManager 中配置 SessionManager，完全前后端分离的项目采用配置，非前后端分离的项目不用配置
        securityManager.setSessionManager(sessionManager());
        // 在 securityManager 中配置 CacheManager
        securityManager.setCacheManager(redisCacheManager());
        // 在 securityManager 中配置 Realm， 这一步推荐放在最后
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
//        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
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
     * 自定义 SessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        // 设置 session 过期时间，默认30min，单位毫秒
        customSessionManager.setGlobalSessionTimeout(100 * 1000L);
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 配置 RedisManager
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setPort(6379);
        redisManager.setHost("192.168.64.135");
        return redisManager;
    }

    /**
     * 配置 cache 的具体实现类
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(20);
        return redisCacheManager;
    }

    /**
     * 自定义 session 持久化
     *
     * @return
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

}

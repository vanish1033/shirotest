package com.whr.rbac_shiro.config;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.whr.rbac_shiro.domain.User;
import com.whr.rbac_shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 用户授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("授权 doGetAuthorizationInfo");
        Integer userId = (Integer) principals.getPrimaryPrincipal();
        User user = userService.findAllUserInfoByUid(userId);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(user.getRoles().stream().map(x -> x.getName()).collect(Collectors.toSet()));
        HashSet<String> permissions = Sets.newHashSet();
        user.getRoles().stream().forEach(role -> {
            String[] urls = (String[]) role.getPermissions().stream().map(permission -> permission.getName()).toArray();
            Collections.addAll(permissions, urls);
        });
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * 用户登录时调用
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证 doGetAuthenticationInfo");
        // 从 token 中获取 username 和 password
        Integer userId = (Integer) token.getPrincipal();
        User user = userService.findAllUserInfoByUid(userId);
        if (ObjectUtils.isEmpty(user)) {
            log.info("用户输入账号有误！");
            return null;
        }
        return new SimpleAuthenticationInfo(userId, user.getPassword(), this.getName());
    }
}

package com.whr.rbac_shiro.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.whr.rbac_shiro.domain.Permission;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
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
        User newUser = (User) principals.getPrimaryPrincipal();
        User user = userService.findAllUserInfoByUid(newUser.getId());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(
                user.getRoles().
                        stream().
                        map(x -> x.getName()).
                        collect(Collectors.toSet()));

        HashSet<String> permissions = Sets.newHashSet();
        user.getRoles().stream().forEach(role -> {
            Set<Permission> permissions1 = role.getPermissions();
            String[] strings = permissions1.stream().map(permission -> permission.getName() + "").toArray(String[]::new);
            Collections.addAll(permissions, strings);
        });

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
        // 从 token 中获取 userId 和 password
        Integer userId = Integer.valueOf(token.getPrincipal() + "");
        User user = userService.findAllUserInfoByUid(userId);
        if (ObjectUtils.isEmpty(user)) {
            log.info("用户输入账号有误！");
            return null;
        }
        // 返回正确的 userId 和 password
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
    }
}

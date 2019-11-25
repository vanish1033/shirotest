package com.whr.shiro.shirotest.realm;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author:whr 2019/11/25
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    // username -> password
    private final Map<String, String> userMap = Maps.newHashMap();
    // user -> role
    private final Map<String, Set<String>> roleMap = Maps.newHashMap();
    // role -> permission
    private final Map<String, Set<String>> permissionMap = Maps.newHashMap();

    /**
     * 初始化数据
     */ {
        userMap.put("whr1", "1");
        userMap.put("whr2", "2");
        userMap.put("whr3", "3");

        roleMap.put("whr1", Sets.newHashSet("root"));
        roleMap.put("whr2", Sets.newHashSet("admin"));
        roleMap.put("whr3", Sets.newHashSet("user"));

        permissionMap.put("root", Sets.newHashSet("all"));
        permissionMap.put("admin", Sets.newHashSet("update"));
        permissionMap.put("user", Sets.newHashSet("select"));
    }

    /**
     * 授权方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限 MyRealm.doGetAuthorizationInfo");
        String name = (String) principals.getPrimaryPrincipal();

        // 获取该用户具有的所有角色
        Set<String> roles = getRoleByName(name);
        Set<String> permissions = Sets.newHashSet();

        // 获取所有角色具有的所有权限
        roles.stream().map(role -> getPermissionByRole(role)).forEach(set -> {
            set.forEach(permissions::add);
        });
        log.info("用户[{}]的所有权限为{}", name, permissions.toString());

        // 封装到 SimpleAuthorizationInfo 对象中
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    private Set<String> getPermissionByRole(String role) {
        Set<String> permissions = permissionMap.get(role);
        return permissions;
    }

    private Set<String> getRoleByName(String name) {
        Set<String> roles = roleMap.get(name);
        return roles;
    }

    /**
     * 认证方法
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证 MyRealm.doGetAuthenticationInfo");
        String name = (String) token.getPrincipal();
        log.info("从token中获取用户名: {}", name);
        // 模拟获取用户的密码
        String pwd = getPassWord(name);
        if (StringUtils.isEmpty(pwd)) return null;

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());
        return simpleAuthenticationInfo;
    }

    private String getPassWord(String name) {
        return userMap.get(name);
    }
}

package com.whr.rbac_shiro.service;

import com.whr.rbac_shiro.domain.User;

/**
 * @author:whr 2019/11/25
 */
public interface UserService {

    User findAllUserInfoByUid(Integer uid);

}

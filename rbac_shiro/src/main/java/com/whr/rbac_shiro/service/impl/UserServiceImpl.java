package com.whr.rbac_shiro.service.impl;

import com.whr.rbac_shiro.dao.UserMapper;
import com.whr.rbac_shiro.domain.User;
import com.whr.rbac_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:whr 2019/11/25
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findAllUserInfoByUid(Integer uid) {
        return userMapper.getUserByUid(uid);
    }
}

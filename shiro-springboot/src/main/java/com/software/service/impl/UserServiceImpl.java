package com.software.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.entity.User;
import com.software.mapper.UserMapper;
import com.software.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Wang Hao
 * @date 2022/10/2 23:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfoByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(User::getName, name);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> queryUserRoleListByUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            return userMapper.queryUserRoleListByUsername(username);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> queryPermissionListByRoleName(List<String> roleNames) {
        if (roleNames != null && roleNames.size() > 0) {
            return userMapper.queryPermissionListByRoleName(roleNames);
        }
        return null;
    }
}

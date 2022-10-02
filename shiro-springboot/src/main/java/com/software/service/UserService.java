package com.software.service;

import com.software.entity.User;

/**
 * @author Wang Hao
 * @date 2022/10/2 23:40
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @param name 用户名
     * @return
     */
    User getUserInfoByName(String name);
}

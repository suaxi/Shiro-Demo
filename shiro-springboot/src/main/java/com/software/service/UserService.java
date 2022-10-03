package com.software.service;

import com.software.entity.User;

import java.util.List;

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

    /**
     * 根据用户名查询角色信息
     *
     * @param username 用户名
     * @return
     */
    List<String> queryUserRoleListByUsername(String username);

    /**
     * 根据角色名查询权限信息
     *
     * @param roleNames 角色名称list
     * @return
     */
    List<String> queryPermissionListByRoleName(List<String> roleNames);
}

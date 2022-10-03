package com.software.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wang Hao
 * @date 2022/10/2 23:38
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询角色信息
     *
     * @param username 用户名
     * @return
     */
    @Select("SELECT " +
            "  NAME " +
            "FROM `role` " +
            "WHERE " +
            "id IN ( " +
            "SELECT " +
            "  role_id " +
            "FROM `role_user` " +
            "WHERE " +
            "user_id = ( SELECT id FROM `user` WHERE NAME = #{username} ) " +
            ")")
    List<String> queryUserRoleListByUsername(@Param("username") String username);

    /**
     * 根据角色名查询权限信息
     *
     * @param roleNames 角色名称list
     * @return
     */
    @Select("<script>" +
                "SELECT NAME " +
                "FROM" +
                "`permission` " +
                "WHERE " +
                "`id` IN (" +
                "SELECT " +
                "  `permission_id` " +
                "FROM " +
                "`role_permission` " +
                "WHERE " +
                "`role_id` IN (" +
                "SELECT `id` FROM `role` WHERE `name` IN " +
                "<foreach collection = 'roleNames' item = 'name' open='(' separator = ',' close = ')'>" +
                    "#{name}" +
                "</foreach>" +
                ")) " +
            "</script>")
    List<String> queryPermissionListByRoleName(@Param("roleNames") List<String> roleNames);
}

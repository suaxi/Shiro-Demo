package com.software.config;

import com.software.entity.User;
import com.software.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Wang Hao
 * @date 2022/10/2 23:49
 */
@Slf4j
@Configuration
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();
        //角色
        List<String> roleList = userService.queryUserRoleListByUsername(username);
        //权限
        List<String> permissionList = userService.queryPermissionListByRoleName(roleList);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(permissionList);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取用户身份信息
        String username = authenticationToken.getPrincipal().toString();

        //2.查询用户身份信息
        User user = userService.getUserInfoByName(username);

        //3.封装token
        if (user != null) {
            return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), user.getPassword(), ByteSource.Util.bytes("123456"), authenticationToken.getPrincipal().toString());
        }
        return null;
    }
}

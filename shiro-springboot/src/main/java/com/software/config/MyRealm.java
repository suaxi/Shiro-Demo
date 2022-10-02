package com.software.config;

import com.software.entity.User;
import com.software.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wang Hao
 * @date 2022/10/2 23:49
 */
@Configuration
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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

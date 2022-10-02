package com.software.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @author Wang Hao
 * @date 2022/10/2 22:31
 */
public class MyRealm extends AuthenticatingRealm {

    //自定义登录认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取身份深吸
        Object principal = authenticationToken.getPrincipal();

        //2.获取凭证
        String password = new String((char[]) authenticationToken.getCredentials());
        System.out.println("用户信息：" + principal.toString() + " - " + password);

        //3.获取数据库中存储的用户信息
        if ("sunxiaochuan".equals(principal)) {
            //模拟数据库中存储的相关数据
            String pwd = "8eda1535b635e6b2c3672cc502249fe0";

            //4.封装
            return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), pwd, ByteSource.Util.bytes("123"), authenticationToken.getCredentials().toString());
        }
        return null;
    }
}

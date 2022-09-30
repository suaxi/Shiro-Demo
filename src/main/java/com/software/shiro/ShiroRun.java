package com.software.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author Wang Hao
 * @date 2022/10/1 0:16
 */
public class ShiroRun {
    public static void main(String[] args) {
        //初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //获取subject对象
        Subject subject = SecurityUtils.getSubject();

        //创建token
        AuthenticationToken token = new UsernamePasswordToken("sunxiaochuan", "123");

        try {
            //完成登录
            subject.login(token);
            System.out.println("登录成功");

            //判断角色
            System.out.println("是否拥有 [admin] 角色：" + subject.hasRole("admin"));

            //判断权限
            System.out.println("是否拥有 [user:select] 权限：" + subject.isPermitted("user:select"));
            //无返回值
            subject.checkPermission("user:add");

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }
}

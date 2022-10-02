package com.software.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang Hao
 * @date 2022/10/3 0:23
 */
@Slf4j
@RequestMapping("/user")
@RestController
@Api(tags = "用户接口")
public class UserController {

    @ApiOperation("登录")
    @GetMapping("/login")
    public String login(String name, String password) {
        //1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.封装token
        AuthenticationToken token = new UsernamePasswordToken(name, password);
        try {
            //3.调用login方法进行登录认证
            subject.login(token);
            return "登录成功";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "登录失败";
        }
    }

}

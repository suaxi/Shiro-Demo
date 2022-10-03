package com.software.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author Wang Hao
 * @date 2022/10/3 0:23
 */
@Slf4j
@Controller
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {

    @ApiOperation("跳转登录页面")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ApiOperation("登录")
    @GetMapping("/doLogin")
    public String doLogin(@RequestParam("name") String name,
                          @RequestParam("password") String password,
                          @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe,
                          HttpSession session) {
        //1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.封装token
        AuthenticationToken token = new UsernamePasswordToken(name, password, rememberMe);
        try {
            //3.调用login方法进行登录认证
            subject.login(token);
            session.setAttribute("username", token.getPrincipal().toString());
            return "main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "登录失败";
        }
    }

    @ApiOperation("记住我")
    @GetMapping("/rememberMe")
    public String rememberMe(HttpSession session) {
        session.setAttribute("username", "rememberMe");
        return "main";
    }

}

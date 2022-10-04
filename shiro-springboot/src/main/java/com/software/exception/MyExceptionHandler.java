package com.software.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wang Hao
 * @date 2022/10/4 13:27
 * @description 统一异常处理
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(Exception e) {
        return "403，无权限:" + e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    public String unauthenticatedException(Exception e) {
        return "401，未认证:" + e.getMessage();
    }
}

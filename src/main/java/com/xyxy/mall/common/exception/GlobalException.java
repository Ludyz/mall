package com.xyxy.mall.common.exception;

import com.xyxy.mall.common.lang.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result authorizatinException(AuthorizationException e){
        return Result.fail(HttpStatus.FORBIDDEN.value(),"你没有权限访问",null);
    }
}

package com.xyxy.mall.controller;


import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.User;
import com.xyxy.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;
    @GetMapping("/getUser")
    public Result getUser(){
        User user=userService.getById("01");
        return Result.success(user);

    }



}

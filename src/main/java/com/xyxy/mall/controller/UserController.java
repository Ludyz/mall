package com.xyxy.mall.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.User;
import com.xyxy.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /*根据用户名更新用户信息*/
    @PutMapping("/updUserMessage")
    public Result updUserMessage(User user){
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("username",user.getUsername());
        boolean result=userService.update(user,updateWrapper);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("更新失败");
        }
    }



}

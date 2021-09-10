package com.xyxy.mall.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.User;
import com.xyxy.mall.service.IUserService;
import com.xyxy.mall.util.JwtUtil;
import com.xyxy.mall.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AccountController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IUserService userService;

    @PostMapping("/login")
    public Result Login(@RequestBody User loginDto, HttpServletResponse response){
        User user=userService.getOne(new QueryWrapper<User>().eq("username",loginDto.getUsername()));
        if (user==null || !MD5Utils.valid(loginDto.getPassword(),user.getPassword())){
            return Result.fail("密码错误");
        }
        String jwt=jwtUtil.generateToken(user.getUserid());
        response.setHeader("authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","authorization");
        Map data=new HashMap<String,Object>();
        data.put("userid",user.getUserid());
        data.put("userName",user.getUsername());
        return Result.success(data);
    }

    @RequiresAuthentication//认证权限
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User registerDto){
        if (registerDto.getPassword()==null|| registerDto.getUsername()==null){
            return Result.fail("用户名或密码不能为空");
        }
        if(null != userService.getOne(new QueryWrapper<User>().eq("username",registerDto.getUsername()))){
            return Result.fail("用户名已存在");
        }
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String passwordMD5= MD5Utils.MD5(registerDto.getPassword());
        User userDB=new User();
        userDB.setUserid(uuid);
        userDB.setUsername(registerDto.getUsername());
        userDB.setPassword(passwordMD5);
        userService.save(userDB);

        return Result.success("注册成功");

    }

}

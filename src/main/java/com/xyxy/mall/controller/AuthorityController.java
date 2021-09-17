package com.xyxy.mall.controller;

import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.Permission;
import com.xyxy.mall.pojo.Role;
import com.xyxy.mall.pojo.RolePermission;
import com.xyxy.mall.pojo.UserRole;
import com.xyxy.mall.service.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserRoleService userRoleService;
    @Autowired
    IPermissionService permissionService;
    @Autowired
    IRolePermissionService rolePermissionService;

    /**
     * 添加一个角色
     * @param role
     * @return
     */
    @RequiresRoles("admin")
    @PostMapping("/insRole")
    public Result insRole(@RequestBody Role role){
        boolean bo=roleService.save(role);
        if (bo==true){
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    /**
     *查询角色
     * @return
     */
    @RequiresRoles(value = {"admin","root"},logical = Logical.OR)//有其中一个角色可以访问
    @GetMapping("/selRole")
    public Result selRole(){
        List<Role> roleList=roleService.list();
        return Result.success(roleList);
    }

    /**
     * 给用户设定角色
     * @param userid
     * @param rid
     * @return
     */
    @PostMapping("/setUserRole")
    public Result setUserRole(String userid,Integer rid){
        UserRole userRole=new UserRole();
        userRole.setUid(userid);
        userRole.setRid(rid);
        boolean bo=userRoleService.save(userRole);
        if (bo==true){
            return Result.success("设置成功");
        }
        return Result.fail("设置失败");
    }


    @RequiresRoles(value = {"admin,root"},logical = Logical.OR)//有其中一个角色可以访问
    @GetMapping("/selPermission")
    public Result selPermission(){
        List<Permission> permissionList=permissionService.list();
        return Result.success(permissionList);
    }

    /**
     * 添加权限
     * @param permission
     * @return
     */
    @RequiresRoles("root")
    @PostMapping("/insPermission")
    public Result insPermission(@RequestBody Permission permission){
        boolean bo=permissionService.save(permission);
        if (bo==true){
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    /**
     * 给角色设定权限
     * @param roleId
     * @param permissionId
     * @return
     */
    @PostMapping("/setRolePermisson")
    public Result setRolePermisson(Integer roleId,Integer permissionId){
        RolePermission rolePermission=new RolePermission();
        rolePermission.setPid(permissionId);
        rolePermission.setRid(roleId);
        boolean bo=rolePermissionService.save(rolePermission);
        if (bo==true){
            return Result.success("设置成功");
        }
        return Result.fail("设置失败");
    }


}

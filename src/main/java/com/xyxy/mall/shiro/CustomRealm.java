package com.xyxy.mall.shiro;

import com.xyxy.mall.mapper.PermissionMapper;
import com.xyxy.mall.mapper.RoleMapper;
import com.xyxy.mall.pojo.Permission;
import com.xyxy.mall.pojo.Role;
import com.xyxy.mall.pojo.User;
import com.xyxy.mall.service.*;
import com.xyxy.mall.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IUserService userService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println(principalCollection.getPrimaryPrincipal().toString());
        String userId=((UserProfile)(principalCollection.getPrimaryPrincipal())).getUserid();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user=userService.getById(userId);

        Set<Role> roleSet=roleMapper.getRolesByUserid(userId);
        Set<String> roleNameSet= new HashSet<>();
        Set<Integer> roleIdSet=new HashSet<>();
        for (Role r:roleSet
             ) {
            roleIdSet.add(r.getRoleId());
            roleNameSet.add(r.getRoleName());
        }

        Set<Permission> permissionSet=permissionMapper.getPermissionByRoleIdSet(roleIdSet);
        Set<String> permissionNameSet=new HashSet<>();
        for (Permission p:permissionSet
             ) {
            permissionNameSet.add(p.getPermissionName());
        }
        simpleAuthorizationInfo.setRoles(roleNameSet);
        simpleAuthorizationInfo.setStringPermissions(permissionNameSet);


        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtil.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(userId);
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setUserid(user.getUserid());
        userProfile.setUsername(user.getUsername());
        return new SimpleAuthenticationInfo(userProfile,jwtToken.getCredentials(), getName());
    }
}

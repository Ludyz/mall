package com.xyxy.mall.shiro;

import com.xyxy.mall.pojo.User;
import com.xyxy.mall.service.IUserService;
import com.xyxy.mall.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IUserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtil.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(Long.valueOf(userId));
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setUserid(user.getUserid());
        userProfile.setUserid(user.getUsername());
        return new SimpleAuthenticationInfo(userProfile,jwtToken.getCredentials(), getName());
    }
}

package com.itsun.bos.realm;

import com.itsun.bos.service.system.PermissionService;
import com.itsun.bos.service.system.RoleService;
import com.itsun.bos.service.system.UserService;
import com.itsun.domain.system.Permission;
import com.itsun.domain.system.Role;
import com.itsun.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 19:28
 */
@Service
public class bosRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权管理");
        SimpleAuthorizationInfo  simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        List<Role> roles = roleService.findByUser(user);
        for (Role role : roles) {
              simpleAuthorizationInfo.addRole(role.getKeyword());
        }

        List<Permission> permissions = permissionService.findByUser(user);
        for (Permission permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission.getKeyword());
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("Shiro 认证管理");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();

        if (username != null && !"".equals(username)) {
            User user = userService.findByUsername(usernamePasswordToken.getUsername());
            if (user != null) {
                return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            }
        }

        return null;
    }
}

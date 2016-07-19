package com.comm;


import com.po.ActiveUser;
import com.po.Permission;
import com.po.User;
import com.service.PermissionService;
import com.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>User: liuhonger
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     *授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ActiveUser activeUser = (ActiveUser)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Permission> menulist=permissionService.selectPermissionByUserId(Integer.parseInt(activeUser.getUserid()));
        List<String> permissionlist = new ArrayList<String>();
       for(Permission permission:menulist){
           permissionlist.add(permission.getPermission());
       }
        authorizationInfo.addStringPermissions(permissionlist);
        return authorizationInfo;
    }
    /**
     * 认证回调函数,登录时调用.
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
        String username = (String) token.getPrincipal();
        User user = userService.findUserName(username);
        if(user == null) {
            return null;
        }
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserid(user.getId().toString());
        activeUser.setUsername(user.getUsername());
        List<Permission> menus =  permissionService.selectUrllist(user.getId());
        activeUser.setMenus(menus);
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                activeUser, //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }


}

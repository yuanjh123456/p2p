package com.hxzy.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.hxzy.pojo.Permission;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.User;
import com.hxzy.service.IUserService;
public class MyShiroRealm extends AuthorizingRealm{

	@Autowired
	private IUserService userService;
	/**
	 * 权限信息，包括角色以及权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String  username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        Role role = userService.findRoleByName(username);
        Set<String> roleNames = new HashSet<String>();
        roleNames.add(role.getName()==null?"-1":role.getName());
        // 将角色名称提供给info
        authorizationInfo.setRoles(roleNames);
        // 根据用户名查询当前用户权限
        Set<Permission> permissions = userService.findPermissions(username);
        Set<String> permissionNames = new HashSet<String>();
        for (Permission permission : permissions) {
        	System.out.println(permission);
            permissionNames.add(permission.getUrl()==null?"-1":permission.getUrl());
        }
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(permissionNames);
        return authorizationInfo;
	}

	/**
	 * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("123456");
		String username = (String) token.getPrincipal();
        List<User> list = userService.findByName(username);
        User user = list.get(0);
        System.out.println("user.get(0)   "+user);
        //User user = findByUsername(username);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        /*if ("1".equals(user.getState())) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }*/
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getName(),
        		user.getPassword(), ByteSource.Util.bytes(user.getName()+MD5.SECRET_KEY), getName());
        return authenticationInfo;
	}

}

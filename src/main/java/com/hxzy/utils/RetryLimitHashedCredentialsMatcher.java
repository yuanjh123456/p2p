package com.hxzy.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.hxzy.pojo.User;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{
/*	
	@Autowired
	private UserServiceImpl userService;*/

	public RetryLimitHashedCredentialsMatcher() {
	}
	
	// 声明一个缓存接口，这个接口是Shiro缓存管理的一部分，它的具体实现可以通过外部容器注入
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
        String password=new String(usertoken.getPassword());
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        // 自定义一个验证过程：当用户连续输入密码错误3次以上禁止用户登录一段时间
        if (retryCount.incrementAndGet() > 30) {
        	//通过用户名称查询用户信息
			/*User user = userService.findByUsername(username);
			if (user != null && "0".equals(user.getState())) {
				// 数据库字段 默认为 0 就是正常状态 所以 要改为1
				// 修改数据库的状态字段为锁定
				user.setState("1");
				userService.saveOrUpdateUser(user);
			}*/
            throw new ExcessiveAttemptsException();
        }
        boolean match = super.doCredentialsMatch(token, info);
        if (match) {
            passwordRetryCache.remove(username);
        }
        return match;
    }
/*	//用户解锁
    public void unlockAccount(String username){
        User user = userService.findByUsername(username);
        if (user != null){
            //修改数据库的状态字段为锁定
            user.setState("0");
            userService.saveOrUpdateUser(user);
            passwordRetryCache.remove(username);
        }
    }*/
}

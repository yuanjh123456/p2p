package com.hxzy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册TestInterceptor拦截器
		InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
		registration.addPathPatterns("/**");
		registration.excludePathPatterns(
			"/main/login", "/main/loginForm", "/main/end", 
			"/**/*.html","/**/*.js", "/**/*.css"
		);
	}

}
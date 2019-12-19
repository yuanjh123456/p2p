package com.hxzy.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

/*@Component
@WebFilter(urlPatterns="/**",filterName="LoginFilter")*/
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		//请求路径
		String url=req.getRequestURI();
		System.out.println("Filter 请求路径=="+url);
		//如果用户没有登录，并且不是 tologin，与dologin，如果此时请求的是其他的url，则应该跳转到登录页面
		HttpSession session=req.getSession();
		Object obj=session.getAttribute("user");
		/*if(url.indexOf(".css")>0 || url.indexOf(".js")>0 || url.indexOf(".html")>0 || url.indexOf("error")>0){
			
		}else if(obj==null && url.indexOf("login")<0 ){
			System.out.println("/main/login");
			res.sendRedirect("https://www.baidu.com/");
		}*/
		res.sendRedirect("https://www.baidu.com/");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}

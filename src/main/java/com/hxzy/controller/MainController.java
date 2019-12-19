package com.hxzy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hxzy.pojo.User;
import com.hxzy.service.IUserService;

@Controller
@RequestMapping("/main")
public class MainController {

	@Autowired
	private IUserService userService;

	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/tomain")
	public String tomain(){
		return "main";
	}
	
	@RequestMapping("/end")
	public String end(HttpServletRequest req){
		req.getSession().removeAttribute("user");
		return "login";
	}
	
	@RequestMapping("/loginForm")
	@ResponseBody
	public String loginForm(User user,HttpServletRequest req){
		System.out.println("1231321231231313123");
		String str = "";
		User u = userService.login(user);
		System.out.println(u);
		if(u!=null){
			str = "success";
			HttpSession session = req.getSession();
			session.setAttribute("user", u);
		}else{
			str = "error";
		}
		System.out.println(str);
		return str;
	}
	
	/*@RequestMapping("/loginForm")
	@ResponseBody
	public String loginForm(User user,HttpServletRequest req){
		System.out.println(user);
		String message = "success";
		UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        User userinfo=(User)subject.getSession().getAttribute("user");
        List<User> list = userService.findByName(user.getName());
        subject.getSession().setAttribute("user", list.get(0));
        //如果session中的用户信息  与 登录名称一致，则直接跳转到主页面
        if(userinfo!=null && userinfo.getName().equals(username)){
        	return new ModelAndView("index");
        }
        
        if(username==null || "".equals(username.trim())){
        	return mv;
        }
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
           message = "密码错误!";
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
        	message = "用户名错误!";
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
        	message =  "错误次数超3次";
        }catch (LockedAccountException le) {
            // 捕获账户被锁定
        	message =  "账户被锁定";
        }
        return "success";
	}*/
	
	@RequestMapping("/getChart")
	@ResponseBody
	public String getChart(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", "123132123");
		String[] str = new String[]{"非洲", "美洲", "亚洲", "欧洲", "大洋洲"};
		map.put("y", str);
		map.put("name", "1800 年");
		int[] i = new int[]{107, 31, 20, 203, 2};
		map.put("data", i);
		String json = JSON.toJSONString(map);
		return json;
	}
}

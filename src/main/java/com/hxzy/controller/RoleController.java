package com.hxzy.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.Tree;
import com.hxzy.pojo.User;
import com.hxzy.pojo.pageBean;
import com.hxzy.service.IRoleService;
import com.hxzy.utils.RedisUtil;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping("/toRole")
	public String toRole(){
		return "role";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public String findAll(HttpServletRequest res){
		String page = res.getParameter("page");
		String rows = res.getParameter("rows");
		String search = res.getParameter("search");
		pageBean bean = new pageBean(search,Integer.parseInt(page),Integer.parseInt(rows));
		String str = roleService.findAll(bean);
		return str;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id){
		System.out.println(id);
		roleService.delete(Integer.parseInt(id));
		return "success";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(Role role){
		roleService.save(role);
		return null;
	}
	
	@RequestMapping("/delAll")
	@ResponseBody
	public String delAll(String str){
		String[] split = str.split("_");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < split.length; i++) {
			list.add(Integer.parseInt(split[i]));
		}
		roleService.delAll(list);
		return "success";
	}
	
	@RequestMapping("/treeInit")
	@ResponseBody
	public String treeInit(String rid){
		List<Tree> list = roleService.treeInit(Integer.parseInt(rid));
		String str = JSONArray.toJSONString(list);
		System.out.println(str);
		return str;
	}
	
	@RequestMapping("/insertTreeRole")
	@ResponseBody
	public String insertTreeRole(String rid,String str){
		String[] split = null;
		if(str!=""){
			split = str.split(",");
		}
		roleService.insertTreeRole(Integer.parseInt(rid),split);
		return "success";
	}
	
	@RequestMapping("/menu")
	@ResponseBody
	public String menu(HttpServletRequest req){
		System.out.println("123");
		User user = (User) req.getSession().getAttribute("user");	
		String str = null;
		try {
			Object obj = redisUtil.get(user.getName());
			if(obj == null){
				List<Tree> list = roleService.finTreeByUid(user.getId());
				str = JSONArray.toJSONString(list);
				redisUtil.set(user.getName(),str,7*24*60*60*1000);
				System.out.println("从集合中获取菜单");
			}else{
				str = (String)obj;
				System.out.println("从redis中获取菜单");
			}
		}catch (Exception e) {
			System.out.println("12356");
			List<Tree> list=roleService.finTreeByUid(user.getId());	
			str=JSONObject.toJSONString(list);
			e.printStackTrace();
		}
		System.out.println(str);
		return str;
	}
}

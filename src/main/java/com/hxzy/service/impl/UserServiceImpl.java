package com.hxzy.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxzy.dao.IUserMapper;
import com.hxzy.pojo.Permission;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.User;
import com.hxzy.pojo.pageBean;
import com.hxzy.service.IUserService;
import com.hxzy.utils.ExcelUtil;
import com.hxzy.utils.MD5;
@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserMapper userMapper;
	
	public String findAll(pageBean bean) {
		//绑定本地线程
		Page<Object> page=PageHelper.startPage(bean.getPage(), bean.getRows());
		
		List<User> list = userMapper.findAll(bean);
		for (User u : list) {
			System.out.println(u);
		}
		Map<String,Object> jsonmap=new HashMap<String,Object>();
		
		jsonmap.put("total", page.getTotal());
		jsonmap.put("rows", list);
		
		String str = JSONObject.toJSONString(jsonmap,SerializerFeature.WriteDateUseDateFormat);
		
		System.out.println(str);
		
		return str;
	}

	public User findById(Integer id) {
		User user = userMapper.findById(id);
		return user;
	}

	public Integer save(User user) {
		Integer i = 0;	
		if(user.getId() == null){
			String md5 = MD5.encodeMD5(user.getName(), user.getPassword());
			user.setPassword(md5);
			i = userMapper.insert(user);
		}else{
			i = userMapper.update(user);
		}
		return i;
	}

	public Integer delete(Integer id) {
		Integer i = userMapper.delete(id);
		return i;
	}

	@Override
	public List<User> findByName(String name) {
		List<User> list = userMapper.findByName(name);
		return list;
	}

	@Override
	public void delAll(List list) {
		userMapper.delAll(list);
	}

	@Override
	public HSSFWorkbook exportExcel(pageBean bean) {
		// 定义表头
		String[] headers = { "编号","姓名","年龄","出生日期" };
		// 获取数据集合
		HSSFWorkbook workbook = null;
		try {
			List<User> list = userMapper.findAll(bean);
			String[][] values = new String[list.size()][headers.length];
			for (int i = 0; i < list.size(); i++) {
				User u = list.get(i);
				values[i][0] = u.getId()+"";
				values[i][1] = u.getName();
				values[i][2] = u.getSexStr();
				values[i][3] = u.getBirthdayStr();
			}
			workbook = ExcelUtil.getHSSFWorkbook("用户管理", headers, values, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	@Override
	public User login(User user) {
/*		String md5 = MD5.encodeMD5(user.getName(),user.getPassword());*/
		User u = userMapper.login(user);
		return u;
	}

	@Override
	public void submitUppass(User user) {
		String md5 = MD5.encodeMD5(user.getName(),user.getPassword());
		user.setPassword(md5);
		userMapper.submitUppass(user.getId(),user.getPassword());
	}

	@Override
	public String download(int uid) {
		String str = userMapper.download(uid);
		return str;
	}

	@Override
	public Role findRoleByName(String name) {
		Role role = userMapper.findRoleByName(name);
		return role;
	}

	@Override
	public Set<Permission> findPermissions(String name) {
		Set<Permission> set = userMapper.findPermissions(name);
		return set;
	}
}

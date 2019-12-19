package com.hxzy.service;

import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hxzy.pojo.Permission;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.User;
import com.hxzy.pojo.pageBean;

public interface IUserService {
	
	public User login(User user);

	public String findAll(pageBean bean);
	
	public List<User> findByName(String name);
	
	public User findById(Integer id);
	
	public Integer save(User user);
	
	public Integer delete(Integer id);
	
	public void delAll(List list);
	
	public HSSFWorkbook exportExcel(pageBean bean);
	
	public void submitUppass(User user);
	
	public String download(int uid);
	
	public Role findRoleByName(String name);
	
	public Set<Permission> findPermissions(String name);
	
}

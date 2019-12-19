package com.hxzy.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.hxzy.pojo.Permission;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.User;
import com.hxzy.pojo.pageBean;

public interface IUserMapper {
	
	public User login(User user);
	
	public List<User> findAll(pageBean bean);
	
	public List<User> findByName(String name);
	
	public User findById(Integer id);
	
	public Integer update(User user);
	
	public Integer insert(User user);
	
	public Integer delete(Integer id);
	
	public void delAll(List list);
	
	public void submitUppass(@Param("id")int id,@Param("password")String password);
	
	public String download(int uid);
	
	public Role findRoleByName(String name);
	
	public Set<Permission> findPermissions(String name);
}

package com.hxzy.service;

import java.util.List;

import com.hxzy.pojo.Role;
import com.hxzy.pojo.Tree;
import com.hxzy.pojo.pageBean;

public interface IRoleService {

	public String findAll(pageBean bean);
	
	public void save(Role role);
	
	public Integer delete(Integer id);
	
	public void delAll(List list);
	
	public List<Role> findAllRole();
	
	public void insertRoleAndUser(Integer uid,Integer id);
	
	public List<Tree> treeInit(int rid);
	
	public void insertTreeRole(int rid,String[] split);
	
	public List<Tree> finTreeByUid(int uid);
	
}

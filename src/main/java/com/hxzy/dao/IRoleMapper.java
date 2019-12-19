package com.hxzy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hxzy.pojo.Role;
import com.hxzy.pojo.Tree;
import com.hxzy.pojo.pageBean;

public interface IRoleMapper {
	
	public List<Role> findAll(pageBean bean);
	
	public Integer delete(Integer id);
	
	public void delAll(List list);
	
	public void insert(Role role);
	
	public List<Role> findAllRole();
	
	public void insertRoleAndUser(Map<String,Integer> map);
	
	public List<Tree> findTreeByPid(int pid);
	
	public void insertTreeRole(@Param("rid")int rid,@Param("mid")int mid);
	
	public List<Tree> findTreeByRid(@Param("rid")int rid);
	
	public void deleteTreeRole(int rid);
	
	public void deleteRoleAndUser(int uid);
	
	public List<Tree> findParentTreeByRid(int rid);
	
	public List<Tree> findChildenTreeByPid(@Param("uid")int uid,@Param("pid")int pid);
}

package com.hxzy.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxzy.dao.IRoleMapper;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.Tree;
import com.hxzy.pojo.pageBean;
import com.hxzy.service.IRoleService;
@Service
public class RolerServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleMapper roleMapper;
	
	@Autowired
	private MongoTemplate mongoTemplate;

/*	@Override
	public String findAll(pageBean bean) {
		Page<Object> page=PageHelper.startPage(bean.getPage(), bean.getRows());
		
		List<Role> list = roleMapper.findAll(bean);
		Map<String,Object> jsonmap=new HashMap<String,Object>();
		
		jsonmap.put("total", page.getTotal());
		jsonmap.put("rows", list);
		
		String str = JSON.toJSONString(jsonmap);
		
		return str;
	}*/
	@Override
	public String findAll(pageBean bean) {
		String json = null;
		Query query = new Query();
		if(bean.getName()!=null){
			query=new Query(Criteria.where("name").is(bean.getName()));
		}
		query.skip(bean.getRows()*(bean.getPage()-1)).limit(bean.getRows());
		List<Role> list = mongoTemplate.find(query,Role.class,"mycol");
		long count = mongoTemplate.count(query, "mycol");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",count);
		map.put("rows", list);
		json = JSON.toJSONString(map);
		return json;
	}

/*	@Override
	public void save(Role role) {
		roleMapper.insert(role);
	}*/

	@Override
	public void save(Role role) {
		role.setId(1);
		mongoTemplate.insert(role,"mycol");
	}
	@Override
	public Integer delete(Integer id) {
		Integer i = roleMapper.delete(id);
		return i;
	}

	@Override
	public void delAll(List list) {
		roleMapper.delAll(list);
		
	}

	@Override
	public List<Role> findAllRole() {
		List<Role> list = roleMapper.findAllRole();
		return list;
	}

	@Override
	public void insertRoleAndUser(Integer uid, Integer rid) {
		//删除以前的数据
		roleMapper.deleteRoleAndUser(uid);
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("uid", uid);
		map.put("rid", rid);
		roleMapper.insertRoleAndUser(map);
	}


	@Override
	public void insertTreeRole(int rid, String[] split) {
		//先删除以前的插入
		roleMapper.deleteTreeRole(rid);
		if(split != null){
			for (int i = 0; i < split.length; i++) {
				roleMapper.insertTreeRole(rid,Integer.parseInt(split[i]));
			}
		}
	}

	@Override
	public List<Tree> treeInit(int rid) {
		int pid = 0;
		List<Tree> list = roleMapper.findTreeByPid(pid);
		List<Tree> list2 = roleMapper.findTreeByRid(rid);
		for (Tree t : list) {
			List<Tree> list1 = roleMapper.findTreeByPid(t.getId());
			for (Tree t1 : list1) {
				for (Tree t2 : list2) {
					if(t1.getId()==t2.getId()){
						t1.setChecked(true);
					}
				}
			}
			t.setChildren(list1);
		}
		return list;
	}

	@Override
	public List<Tree> finTreeByUid(int uid) {
		//查找父节点
		int pid = 0;
		List<Tree> plist = roleMapper.findParentTreeByRid(uid);
		for (Tree t : plist) {
			List<Tree> clist = roleMapper.findChildenTreeByPid(uid,t.getId());
			t.setChildren(clist);
		}
		
		return plist;
	}
}

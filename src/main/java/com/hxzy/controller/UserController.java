package com.hxzy.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.hxzy.pojo.Role;
import com.hxzy.pojo.User;
import com.hxzy.pojo.pageBean;
import com.hxzy.service.IRoleService;
import com.hxzy.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/toUser")
	public String toUser(){
		return "list";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public String findAll(HttpServletRequest res){
		String page = res.getParameter("page");
		String rows = res.getParameter("rows");
		String search = res.getParameter("search");
		pageBean bean = new pageBean(search,Integer.parseInt(page),Integer.parseInt(rows));
		String str = userService.findAll(bean);
		return str;
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(Integer id){
		User user = userService.findById(id);
		String str = JSON.toJSONString(user);
		return str;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id){
		userService.delete(Integer.parseInt(id));
		return "success";
	}
	
	@RequestMapping("/delAll")
	@ResponseBody
	public String delAll(String str){
		String[] split = str.split("_");
		System.out.println(Arrays.toString(split));
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < split.length; i++) {
			list.add(Integer.parseInt(split[i]));
		}

		userService.delAll(list);
		
		return "success";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(User user,HttpServletRequest req){
		System.out.println(user);
		//获取文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile file = multipartRequest.getFile("img");
		//判断
		if(file!=null){
			String filePath = "D:\\upload";
			File f = new File(filePath);
			if(!f.exists()){
				f.mkdir();
			}
			//获取文件名称
			String filename=file.getOriginalFilename();
			//将文件上传到指定命令
			try {
				file.transferTo(new File(f,filename));
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setFilepath(filePath+File.separator+filename);
		}
		userService.save(user);
		return "1";
	}
	
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletResponse resp,HttpServletRequest req){
		try {
			String page = req.getParameter("page");
			String rows = req.getParameter("rows");
			String name = req.getParameter("name");
			pageBean bean = new pageBean(name,null,null);
			System.out.println(bean);
			HSSFWorkbook wb=userService.exportExcel(bean);
			String fileName = "用户信息表"+System.currentTimeMillis()+".xls";
            fileName = new String(fileName.getBytes(),"ISO8859-1");
            resp.setContentType("application/octet-stream;charset=ISO8859-1");
            resp.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            resp.addHeader("Pargam", "no-cache");
            resp.addHeader("Cache-Control", "no-cache");
            OutputStream os = resp.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping("/findAllRole")
	@ResponseBody
	public String findAllRole(){
		List<Role> list = roleService.findAllRole();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		String str = JSON.toJSONString(map);
		return str;
	}
	
	@RequestMapping("/insertRoleAndUser")
	@ResponseBody
	public String insertRoleAndUser(String uid,String rid){
		roleService.insertRoleAndUser(new Integer(Integer.parseInt(uid)),new Integer(Integer.parseInt(rid)));
		return "success";
	}
	
	@RequestMapping("/submitUppass")
	@ResponseBody
	public String submitUppass(User user){
		userService.submitUppass(user);
		return "success";
	}
	
	@RequestMapping("/download")
	@ResponseBody
	public void download(int uid,HttpServletResponse resp) throws Exception{
		String filepatname = userService.download(uid);
		String filename=filepatname.substring(filepatname.lastIndexOf("\\")+1,filepatname.length());
		File file=new File(filepatname);
		if(file==null || !file.exists()){
			return;
		}
		filename = new String(filename.getBytes(),"ISO8859-1");
        resp.setContentType("application/octet-stream;charset=ISO8859-1");
        resp.setHeader("Content-Disposition", "attachment;filename="+ filename);
        resp.addHeader("Pargam", "no-cache");
        resp.addHeader("Cache-Control", "no-cache");
        OutputStream out = resp.getOutputStream();
        out.write(FileUtils.readFileToByteArray(file));
        out.flush();
        out.close();
	}
}

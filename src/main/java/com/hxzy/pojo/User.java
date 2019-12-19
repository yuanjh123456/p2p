package com.hxzy.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class User {

	private Integer id;
	
	private String name;
	
	private String password;
	
	private String sex;
	
	private String sexStr;
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date birthday;
	
	private String birthdayStr;
	
	private String filepath;

	public String getSexStr() {
		if(sex.equals("0")){
			sexStr = "男";
		}else{
			sexStr = "女";
		}
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public String getBirthdayStr() {
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" ); 

		birthdayStr = sdf.format(birthday); 
        
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		if(birthday == null){
			birthday = new Date();
		}
		return birthday;
	}

	public void setBirthday(Date birthday) {
		if(birthday == null){
			birthday = new Date();
		}
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", sex=" + sex + ", sexStr=" + sexStr
				+ ", birthday=" + birthday + ", birthdayStr=" + birthdayStr + ", filepath=" + filepath + "]";
	}
}

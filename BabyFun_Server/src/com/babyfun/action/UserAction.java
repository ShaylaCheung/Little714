package com.babyfun.action;

import java.util.HashMap;
import java.util.Map;

import com.babyfun.bean.User;
import com.babyfun.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	
	public Map<String,Object> data = new HashMap<String,Object>();
	
	private String user_name;
	private String user_password;
	private String user_phone ;
	
	public String login(){
		data.clear();
		User user = new UserDao().confirmUserLogin(user_phone,user_password);
		if(user!=null){
			data.put("user",user);
			return SUCCESS;
		}else{
			return ERROR;
		}

	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
	public String addUser(){
		data.clear();
		User user = new UserDao().userSignIn(user_name, user_password, user_phone);
		if(user!=null){
			data.put("user", user);
			return SUCCESS;
		}else{
			data.put("user", null);
			return ERROR;
		}
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	
}

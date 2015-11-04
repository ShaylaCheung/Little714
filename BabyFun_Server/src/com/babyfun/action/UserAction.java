package com.babyfun.action;

import java.util.HashMap;
import java.util.Map;

import com.babyfun.bean.User;
import com.babyfun.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	
	public Map<String,Object> data = new HashMap<String,Object>();
	private Long user_id;
	private String user_name;
	private String user_password;
	private String user_phone ;
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
	
	/**
	 * 登录接口
	 * @return
	 */
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


	/**
	 * 注册接口
	 * @return
	 */
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

	/**
	 * 判断欲注册的手机号码是否已注册
	 * @param user_phone
	 * @return
	 */
	public String isPhoneSignIn(){
		System.out.println("nnnnnnnnnn");
		data.clear();
		boolean isPhoneSignIn = new UserDao().isPhoneSignIn(user_phone);
		if(isPhoneSignIn){
			data.put("isPhoneSignIn",isPhoneSignIn);
			return SUCCESS;
		}else{
			data.put("isPhoneSignIn",isPhoneSignIn);
			return SUCCESS;
		}
		
	}

	/**
	 * 判断欲注册的用户昵称是否已注册
	 * @param user_phone
	 * @return
	 */
	public String isNameSignIn(){
		data.clear();
		boolean isNameSignIn = new UserDao().isNameSignIn(user_name);
		if(isNameSignIn){
			data.put("isNameSignIn",isNameSignIn);
		}else{
			data.put("isNameSignIn",isNameSignIn);
		}
		return SUCCESS;
	}
	
	public String updateUserInformation(){
		data.clear();
		boolean update_state = new UserDao().updateUserInformation(user_name, user_phone, user_id);
		if(update_state){
			data.put("update_state",update_state);
		}else{
			data.put("update_state",update_state);
		}
		return SUCCESS;
	}
}

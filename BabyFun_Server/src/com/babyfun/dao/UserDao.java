package com.babyfun.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.babyfun.bean.User;
import com.babyfun.util.DatabaseConnection;

public class UserDao {
//	private static ResultSet rs=null;       //定义结果集
//	private static PreparedStatement psmt=null;   //定义数据库操作对象
	
	/**
	 * 用户注册
	 * @return
	 */
	public User userSignIn(String user_name,String user_password,String user_phone){
		
		String sql = "insert into bf_user(user_name,user_password,user_phone) values(?,?,?)";
		DatabaseConnection dbc = new DatabaseConnection();
		User user = null;
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setString(1, user_name);
			ps.setString(2, user_password);
			ps.setString(3, user_phone);
			ps.execute();
			user = confirmUserLogin(user_phone, user_password);
			ps.close();
			dbc.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return user;
	}
	
	
	public User confirmUserLogin(String user_phone,String user_password){
		String sql = "select id,user_name,user_password,user_phone from bf_user where user_phone=?";
		User user = new User();
		DatabaseConnection dbc = new DatabaseConnection();
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setString(1, user_phone);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				String passwordFromdb = rs.getString(3);
				if(user_password!=null&&passwordFromdb!=null){
					if(user_password.equals(passwordFromdb)){
						user.setId(rs.getLong(1));
						user.setUser_name(rs.getString(2));
						user.setUser_password(rs.getString(3));
						user.setUser_phone(rs.getString(4));
					}
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return user;
	}
}

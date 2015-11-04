package com.babyfun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	//定义数据库驱动程序
		public static final String DBDRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//定义数据库的连接地址
		public static final String DBURL="jdbc:sqlserver://localhost:1433;DatabaseName=BabyFunSystem";
		
		public static final String USER_NAME="sa";
		public static final String USER_PASSWORD="123456";
		
		private static Connection con=null;    //定义数据库连接对象
		private static PreparedStatement pstmt=null;    //定义数据库操作对象
		
		
		public DatabaseConnection(){
			try{
				Class.forName(DBDRIVER);             //加载数据库驱动
				con=DriverManager.getConnection(DBURL,USER_NAME,USER_PASSWORD);        //连接数据库  
				
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		public Connection getCon() {
			return con;
		}
		
		public void close(){
			try{
				this.con.close();
			}catch(SQLException  e){
				e.printStackTrace();
			}
		}
		
		
}

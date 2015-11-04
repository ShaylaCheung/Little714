package com.babyfun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	//�������ݿ���������
		public static final String DBDRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//�������ݿ�����ӵ�ַ
		public static final String DBURL="jdbc:sqlserver://localhost:1433;DatabaseName=BabyFunSystem";
		
		public static final String USER_NAME="sa";
		public static final String USER_PASSWORD="123456";
		
		private static Connection con=null;    //�������ݿ����Ӷ���
		private static PreparedStatement pstmt=null;    //�������ݿ��������
		
		
		public DatabaseConnection(){
			try{
				Class.forName(DBDRIVER);             //�������ݿ�����
				con=DriverManager.getConnection(DBURL,USER_NAME,USER_PASSWORD);        //�������ݿ�  
				
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

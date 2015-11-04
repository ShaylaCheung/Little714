package com.babyfun.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.babyfun.bean.Music;
import com.babyfun.util.DatabaseConnection;

public class MusicDao {
	
	/**
	 * 查询所有的所有的音乐信息
	 * @return
	 */
	public List<Music> queryAllMusic(){
		String sql = "select id,music_name,singer_name,music_path from bf_music order by id asc";
		DatabaseConnection dbc = new DatabaseConnection();
		
		List<Music> musiclist = new ArrayList<Music>();
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Music music = new Music();
				music.setId(rs.getLong(1));
				music.setMusic_name(rs.getString(2));
				music.setSinger_name(rs.getString(3));
				music.setMusic_path(rs.getString(4));
				musiclist.add(music);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return musiclist;
	}
	
	
	public List<Music> queryLocalMusic(String singer_name){
		String sql = "select id,music_name,singer_name,music_path from bf_music where singer_name=?";
		DatabaseConnection dbc = new DatabaseConnection();
		
		List<Music> musiclist = new ArrayList<Music>();
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setString(1, singer_name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Music music = new Music();
				music.setId(rs.getLong(1));
				music.setMusic_name(rs.getString(2));
				music.setSinger_name(rs.getString(3));
				music.setMusic_path(rs.getString(4));
				musiclist.add(music);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return musiclist;
	}
}

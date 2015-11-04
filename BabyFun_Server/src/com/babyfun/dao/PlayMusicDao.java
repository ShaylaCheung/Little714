package com.babyfun.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.babyfun.bean.Music;
import com.babyfun.bean.User;
import com.babyfun.util.DatabaseConnection;

public class PlayMusicDao {
	private List<Music> musiclist = new ArrayList<Music>();
	
	/**
	 * 获取播放列表，返回音乐id列表
	 */
	public List<Music> getPlayList(Long user_id){
		String sql = "select id,user_id,music_id from bf_user_music where user_id=? order by id asc";
		DatabaseConnection dbc = new DatabaseConnection();
		
		List<Music> musicList = new ArrayList<Music>();
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setLong(1, user_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Long music_id = rs.getLong(3);
				Music music = getMusicInfo(music_id);
				musicList.add(music);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return musicList;
	}
	
	
	/**
	 * 根据music_id得到music信息
	 */
	public Music getMusicInfo(Long music_id){
		String sql = "select id,music_name,singer_name,music_path from bf_music where id=?";
		DatabaseConnection dbc = new DatabaseConnection();
		Music music = null;
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setLong(1, music_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				music = new Music();
				music.setId(rs.getLong(1));
				music.setMusic_name(rs.getString(2));
				music.setSinger_name(rs.getString(3));
				music.setMusic_path(rs.getString(4));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return music;
	}
	
	
	/**
	 * 添加信息到user_music表中
	 */
	public boolean addUserMusic(Long user_id,Long music_id){
		String sql = "insert into bf_user_music(user_id,music_id) values(?,?)";
		DatabaseConnection dbc = new DatabaseConnection();
		boolean add_status = false;
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setLong(1, user_id);
			ps.setLong(2, music_id);
			ps.execute();
//			music_idList = getPlayList(user_id);
			add_status = true;
			ps.close();
			dbc.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return add_status;
	}
	
	
	/**
	 * 从user_music表中删除信息
	 */
	public boolean deleteUserMusic(Long user_id,Long music_id){
		String sql = "delete from bf_user_music where user_id=? and music_id=?";
		DatabaseConnection dbc = new DatabaseConnection();
		boolean delete_status = false;
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setLong(1, user_id);
			ps.setLong(2, music_id);
			ps.execute();
//			music_idList = getPlayList(user_id);
			delete_status = true;
			ps.close();
			dbc.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		return delete_status;
	}
	
	/**
	 * 判断歌曲是否已经在播放列表中
	 * @param user_id
	 * @param music_id
	 * @return
	 */
	public boolean isInPlayList(Long user_id,Long music_id){
		String sql = "select * from bf_user_music where user_id=? and music_id=?";
		DatabaseConnection dbc = new DatabaseConnection();
		boolean isInPlay = false;
		try{
			PreparedStatement ps = dbc.getCon().prepareStatement(sql);
			ps.setLong(1, user_id);
			ps.setLong(2, music_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				isInPlay = true;
			}
			ps.close();
			dbc.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			dbc.close();
		}
		System.out.println(isInPlay+"");
		return isInPlay;
	}
}


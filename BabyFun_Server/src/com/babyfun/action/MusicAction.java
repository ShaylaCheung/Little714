package com.babyfun.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.babyfun.bean.Music;
import com.babyfun.dao.MusicDao;
import com.opensymphony.xwork2.ActionSupport;

public class MusicAction extends ActionSupport{
	
	private Long id;
	private String music_name;
	private String singer_name;
	private String music_path;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMusic_name() {
		return music_name;
	}

	public void setMusic_name(String music_name) {
		this.music_name = music_name;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}

	public String getMusic_path() {
		return music_path;
	}

	public void setMusic_path(String music_path) {
		this.music_path = music_path;
	}

	public Map<String,Object> data = new HashMap<String,Object>();
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public String getAllMusicList(){
		List<Music> musiclist = new MusicDao().queryAllMusic();
		data.clear();
		data.put("musicList", musiclist);
		return SUCCESS;
	}
	
	public String getLocalMusicList(){
		List<Music> musiclist = new MusicDao().queryLocalMusic(singer_name);
		data.clear();
		data.put("musicList", musiclist);
		return SUCCESS;
	}
	
}

package com.babyfun.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.babyfun.bean.Music;
import com.babyfun.bean.User;
import com.babyfun.dao.PlayMusicDao;
import com.babyfun.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

public class PlayMusicAction extends ActionSupport{
	
	public Map<String,Object> data = new HashMap<String,Object>();
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	private Long user_id;
	private Long music_id;
	
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getMusic_id() {
		return music_id;
	}

	public void setMusic_id(Long music_id) {
		this.music_id = music_id;
	}

	/**
	 * 获取所有音乐
	 * @return
	 */
	public String getPlayList(){
		List<Music> musicList = new PlayMusicDao().getPlayList(user_id);
		data.clear();
		data.put("musicList", musicList);
		return SUCCESS;
	}
	
	/**
	 * 添加到播放列表的接口
	 * @return
	 */
	public String addUserMusic(){
		data.clear();
		boolean addMusic_status = new PlayMusicDao().addUserMusic(user_id, music_id);
		
		
		if(addMusic_status){
			data.put("addMusic_status",addMusic_status);
			return SUCCESS;
		}else{
			data.put("addMusic_status",addMusic_status);
			return ERROR;
		}
	}
	
	/**
	 * 从播放列表账中删除歌曲
	 * @return
	 */
	public String deleteUserMusic(){
		data.clear();
		boolean delete_status = new PlayMusicDao().deleteUserMusic(user_id, music_id);
		if(delete_status){
			data.put("delete_status",delete_status);
			return SUCCESS;
		}else{
			data.put("delete_status",delete_status);
			return ERROR;
		}
	}
	
	/**
	 * 判断该音乐是否在播放列表中的接口
	 * @return
	 */
	public String isInPlayList(){
		data.clear();
		boolean isInPlay = new PlayMusicDao().isInPlayList(user_id, music_id);
		if(isInPlay){
			data.put("isInPlay",isInPlay);
			return SUCCESS;
		}else{
			data.put("isInPlay",isInPlay);
			return SUCCESS;
		}
		
	}
}

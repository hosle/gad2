package com.game;

import com.userim.User;

import cn.bmob.v3.BmobObject;

/**
 * @author HenryTam
 *
 */
public class Game extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gameId;
	private User gameOwnerUser;//游戏生成者
	private String source;//html5游戏的地址，内嵌游戏为“innerGame”
	private String preference;//游戏修改的元素 拼图游戏为图片的名字
	
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public User getGameOwnerUser() {
		return gameOwnerUser;
	}
	public void setGameOwnerUser(User gameOwnerUser) {
		this.gameOwnerUser = gameOwnerUser;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPreference() {
		return preference;
	}
	public void setPreference(String preference) {
		this.preference = preference;
	}
	
	
	
	
	
}

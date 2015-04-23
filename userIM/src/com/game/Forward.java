package com.game;

import com.userim.User;

import cn.bmob.v3.BmobObject;

/**
 * @author HenryTam
 *记录游戏的转发
 */
public class Forward extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User senderUser;//发送者
	private User receiverUser;//接收者
	private Game game;//游戏
	
	public User getSenderUser() {
		return senderUser;
	}
	public void setSenderUser(User senderUser) {
		this.senderUser = senderUser;
	}
	public User getReceiverUser() {
		return receiverUser;
	}
	public void setReceiverUser(User receiverUser) {
		this.receiverUser = receiverUser;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	

}

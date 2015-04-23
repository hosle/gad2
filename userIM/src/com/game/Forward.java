package com.game;

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
	
	private String senderName;//发送者
	private String receiverName;//接收者
	private String gameId;//游戏
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderUser) {
		this.senderName = senderUser;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverUser) {
		this.receiverName = receiverUser;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String game) {
		this.gameId = game;
	}
	
	

}

package com.game;

import cn.bmob.v3.BmobObject;

/**
 * @author HenryTam
 *��¼��Ϸ��ת��
 */
public class Forward extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String senderName;//������
	private String receiverName;//������
	private Game gameforward;//��Ϸ
	
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
	public Game getGameforward() {
		return gameforward;
	}
	public void setGameforward(Game gameforward) {
		this.gameforward = gameforward;
	}
	
	

}

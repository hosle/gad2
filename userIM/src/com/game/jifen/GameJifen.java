package com.game.jifen;

import com.userim.User;

import cn.bmob.v3.BmobObject;

public class GameJifen extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int jifen;
	private User player;//��Ϸ���
	private String gameId;//��Ϸ���
	private float exRate;//�һ�����
	private String ader;//��û��ֵĹ��������
	
	
	public int getJifen() {
		return jifen;
	}
	public void setJifen(int jifen) {
		this.jifen = jifen;
	}
	public User getPlayer() {
		return player;
	}
	public void setPlayer(User player) {
		this.player = player;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public float getExRate() {
		return exRate;
	}
	public void setExRate(float exRate) {
		this.exRate = exRate;
	}
	public String getAder() {
		return ader;
	}
	public void setAder(String ader) {
		this.ader = ader;
	}
	
	
	
	
}

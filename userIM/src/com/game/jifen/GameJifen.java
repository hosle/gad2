package com.game.jifen;

import android.text.TextUtils;
import android.widget.Toast;

import com.userim.User;

import cn.bmob.v3.BmobObject;

public class GameJifen extends BmobObject {

	private User player;//��Ϸ���
	//private String playerName;
	private String gameId;//��Ϸ���
	private float exRate;//�һ�����
	
	
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
	
	
	
	
}

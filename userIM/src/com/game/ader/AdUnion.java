package com.game.ader;

import cn.bmob.v3.BmobObject;

public class AdUnion extends BmobObject {
	private static final long serialVersionUID = 1L;

	private String aderName;
	private int ratio;
	private int timeGap;
	private int personalShowUpLimit;
	private int totalShowLowerLimit;
	private int personalShowLowerLimit;
	private int showTimeUpLimit;
	
	public String getAderName() {
		return aderName;
	}
	public int getRatio() {
		return ratio;
	}
	public int getTimeGap() {
		return timeGap;
	}
	public int getPersonalShowUpLimit() {
		return personalShowUpLimit;
	}
	public int getTotalShowLowerLimit() {
		return totalShowLowerLimit;
	}
	public int getPersonalShowLowerLimit() {
		return personalShowLowerLimit;
	}
	public int getShowTimeUpLimit() {
		return showTimeUpLimit;
	}
	
	
	

}

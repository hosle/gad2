package com.game.config;

import java.util.List;

import cn.bmob.im.bean.BmobChatUser;

import com.userim.User;
import com.userim.adapter.UserFriendAdapter;

import android.util.DisplayMetrics;

public class Config {
	public static DisplayMetrics metrics;
	public static int nandu = 3;
	public static int time = 0;
	public static int bushu = 0;
	public static long startTime;
	public static int imageId;
	
	public static List<BmobChatUser> mbcuser;
	public static List<User> mUser;
	public static UserFriendAdapter mFriendAdapter;
	
	
}

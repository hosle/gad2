package com.game.pintu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.ManagerFactoryParameters;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;


import com.game.config.Config;
import com.game.util.CharacterParser;
import com.game.util.PinyinComparator;

import com.userim.view.MyLetterView;
import com.userim.view.MyLetterView.OnTouchingLetterChangedListener;
import com.userim.User;
import com.userim.adapter.UserFriendAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactActivity extends  Activity implements OnItemClickListener {

	TextView dialog;

	ListView list_friends;
	MyLetterView right_letter;
	
	//要发送的好友对象
	private BmobChatUser targetUser;
	private String targetId="";
	//要发送的命令内容
	private  String commandString="111";

	private BmobChatManager manager;
	
	private UserFriendAdapter userAdapter;// 好友

	List<User> friends=new ArrayList<User>();


	/**
	 * 汉字转换成拼音的类
	 */
	
	
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		manager=BmobChatManager.getInstance(this);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}

	private void init() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();

		initListView();
		initRightLetterView();

	}

	ImageView iv_msg_tips;
	TextView tv_new_name;

	// LinearLayout layout_new;//新朋友
	// LinearLayout layout_near;//附近的人

	private void initListView() {
		list_friends = (ListView) findViewById(R.id.list_friends_activity);
	
		userAdapter = new UserFriendAdapter(this, friends);
		list_friends.setAdapter(userAdapter);
		list_friends.setOnItemClickListener(this);
		
		
		
		filledData(Config.mbcuser);
	
		

	}

	private void initRightLetterView() {
		right_letter = (MyLetterView) findViewById(R.id.right_letter_activity);
		dialog = (TextView) findViewById(R.id.dialog_activity);
		right_letter.setTextView(dialog);
		right_letter
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(String s) {
			// 该字母首次出现的位置
			int position = userAdapter.getPositionForSection(s.charAt(0));
			if (position != -1) {
				list_friends.setSelection(position);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		
		//初始化聊天对象
		targetUser =(BmobChatUser) userAdapter.getItem(position);
		targetId=targetUser.getObjectId();
		
		//设置发送的命令
		commandString="#"+Config.nandu+"#"+Config.imageId;
		
		BmobMsg commandBmobMsg=BmobMsg.createTextSendMsg(this, targetId, commandString);
		manager.sendTextMessage(targetUser, commandBmobMsg);
	
		
		// 默认发送完成，将数据保存到本地消息表和最近会话表中
		
		// 刷新界面
		//refreshMessage(message);
		this.finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh();
	}


	
	private void refresh() {
		// TODO Auto-generated method stub
		try {
			this.runOnUiThread(new Runnable() {
				public void run() {
					//queryMyfriends();
					initListView();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void filledData(List<BmobChatUser> datas) {
		// TODO Auto-generated method stub
		/**
		 * 为ListView填充数据
		 * 
		 * @param date
		 * @return
		 */

		friends.clear();
		int total = datas.size();
		for (int i = 0; i < total; i++) {
			BmobChatUser user = datas.get(i);
			User sortModel = new User();
			sortModel.setAvatar(user.getAvatar());
			sortModel.setNick(user.getNick());
			sortModel.setUsername(user.getUsername());
			sortModel.setObjectId(user.getObjectId());
			sortModel.setContacts(user.getContacts());
			// 汉字转换成拼音
			String username = sortModel.getUsername();
			// 若没有username
			if (username != null) {
				String pinyin = characterParser.getSelling(sortModel
						.getUsername());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					sortModel.setSortLetters(sortString.toUpperCase());
				} else {
					sortModel.setSortLetters("#");
				}
			} else {
				sortModel.setSortLetters("#");
			}
			friends.add(sortModel);
		}
		// 根据a-z进行排序
		Collections.sort(friends, pinyinComparator);
	}

}

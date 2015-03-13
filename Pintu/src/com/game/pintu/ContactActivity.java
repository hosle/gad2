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
	
	//Ҫ���͵ĺ��Ѷ���
	private BmobChatUser targetUser;
	private String targetId="";
	//Ҫ���͵���������
	private final String commandString="111";

	private BmobChatManager manager;
	
	private UserFriendAdapter userAdapter;// ����

	List<User> friends=new ArrayList<User>();


	/**
	 * ����ת����ƴ������
	 */
	
	
	private CharacterParser characterParser;
	/**
	 * ����ƴ��������ListView�����������
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

	// LinearLayout layout_new;//������
	// LinearLayout layout_near;//��������

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
			// ����ĸ�״γ��ֵ�λ��
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
		
		//��ʼ���������
		targetUser =(BmobChatUser) userAdapter.getItem(position);
		targetId=targetUser.getObjectId();
		
		BmobMsg commandBmobMsg=BmobMsg.createTextSendMsg(this, targetId, commandString);
		manager.sendTextMessage(targetUser, commandBmobMsg);
	
		
		// Ĭ�Ϸ�����ɣ������ݱ��浽������Ϣ��������Ự����
		
		// ˢ�½���
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
		 * ΪListView�������
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
			// ����ת����ƴ��
			String username = sortModel.getUsername();
			// ��û��username
			if (username != null) {
				String pinyin = characterParser.getSelling(sortModel
						.getUsername());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				// �������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
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
		// ����a-z��������
		Collections.sort(friends, pinyinComparator);
	}

}
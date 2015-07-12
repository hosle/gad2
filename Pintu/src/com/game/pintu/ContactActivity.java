package com.game.pintu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bmob.btp.file.BTPFileResponse;
import com.game.Game;
import com.game.config.Config;
import com.game.operator.GameManager;
import com.game.util.CharacterParser;
import com.game.util.PinyinComparator;
import com.userim.view.MyLetterView;
import com.userim.view.MyLetterView.OnTouchingLetterChangedListener;
import com.userim.User;
import com.userim.adapter.UserFriendAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



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

	private GameManager gameManager;

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
		gameManager=GameManager.getInstance(this);
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
		//upload();

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
		commandString = "#g"+gameManager.getCurrentGame().getSource()+"#p"+gameManager.getCurrentGame().getPreference();
		
		BmobMsg commandBmobMsg=BmobMsg.createTextSendMsg(this, targetId, commandString);
		manager.sendTextMessage(targetUser, commandBmobMsg);
		
		Game sendgame=gameManager.getCurrentGame();
		//发送游戏
		gameManager.sendGame(sendgame,targetUser.getUsername());
		
		
		// 默认发送完成，将数据保存到本地消息表和最近会话表中
		
		// 刷新界面
		//refreshMessage(message);
		//upload();
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

	String downLoadUrl = "";
	/**
	 * @Description:单一文件上传
	 * @param  
	 * @return void
	 * @throws
	 */
	ProgressDialog updialog =null;

	
	
	private void upload(){

		//Game tempGame=GameManager.getInstance(this).getCurrentGame();
		
		updialog = new ProgressDialog(ContactActivity.this);
		//saveMyBitmap(str,bmp);
		String newimg[];
		newimg = new String[1];
		com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/newimage.txt",newimg);
		String bitName = newimg[0];
		//showToast(bitName);//测试是否读取到了图像的名称
		
		String filePath = "/mnt/sdcard/gameimage/" + bitName +".jpg";//实验发现同一图片只能传一次
		//showToast(filePath);
		
		updialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		updialog.setTitle("上传中...");
		updialog.setIndeterminate(false);               
		updialog.setCancelable(true);       
		updialog.setCanceledOnTouchOutside(false);  
		updialog.show();//"cc9a6ee19b0211fc6a46b1a4bce30c72"
		BTPFileResponse response = BmobProFile.getInstance(ContactActivity.this).upload(filePath, new UploadListener() {

			@Override
			public void onSuccess(String fileName,String url) {
				// TODO Auto-generated method stub
				//downloadName = fileName;
				updialog.dismiss();
				//如果你想得到一个可以直接在客户端显示的图片地址，那么可以使用BmobProFile类的静态方法获取可访问的URL地址,且不建议开启URL签名认证
				String URL = BmobProFile.getInstance(ContactActivity.this).signURL(fileName,url,"填入你web后台管理应用密钥中的AccessKey",0,null);
				showLog("MainActivity -onSuccess :"+fileName+",签名后的URL = "+ URL);
				showToast("文件已上传成功："+fileName);
			}

			@Override
			public void onProgress(int ratio) {
				// TODO Auto-generated method stub
				showLog("MainActivity -onProgress :"+ratio);
				updialog.setProgress(ratio);
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				//				showLog("MainActivity -onError :"+statuscode +"--"+errormsg);
				updialog.dismiss();
				showToast("上传出错："+errormsg);
			}
		});

		showLog("upload方法返回的code = "+response.getStatusCode());
	}


	
	Toast mToast;

	public void showToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
	
	public void showToast(int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), resId,
					Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
	}
	
	public static void showLog(String msg) {
		Log.i("BmobPro", msg);
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

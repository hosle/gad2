package com.game.xxh;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.game.config.Config;
import com.game.jifen.GameJifen;
import com.game.pintu.ContactActivity;
import com.game.pintu.R;
import com.game.pintu.SelectImage;
import com.game.xxh.view.GamePintuLayout;
import com.userim.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityXXH extends Activity  {
	
	private TextView nandu,time;
	private Handler handler = new Handler();
	
	private ImageButton sendButton,personalButton;
	GamePintuLayout mGameView;
	
	private BmobUserManager userManager = BmobUserManager.getInstance(this);
	private User mUser;
	private GameJifen mJifen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xxh_main);
		
		mUser =  userManager.getCurrentUser(User.class);
		
		Bundle bundle=getIntent().getExtras();
		
		
		nandu = (TextView) findViewById(R.id.nandu);
		

		sendButton=(ImageButton)findViewById(R.id.btn_sendgame);
		personalButton = (ImageButton)findViewById(R.id.btn_personial);
		personalButton.setEnabled(false);

		sendButton=(ImageButton)findViewById(R.id.btn_sendgame);
		personalButton = (ImageButton)findViewById(R.id.btn_personial);


		
		time = (TextView) findViewById(R.id.time);
		Config.startTime = System.currentTimeMillis();
		
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable,50); 
		
		
		switch(Config.nandu){
		case 3:
			nandu.setText("难度:简单");
			break;
		case 4:
			nandu.setText("难度:一般");
			break;
		case 5:
			nandu.setText("难度:困难");
			break;
	
		}
		
		mGameView = (GamePintuLayout) findViewById(R.id.id_gameview);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivityXXH.this, ContactActivity.class);
				startActivity(intent);
			}
		});

		//handler.removeCallbacks(runnable);
		personalButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivityXXH.this, SelectImage.class);
				startActivity(intent);
			}
		});
	}
	
    private Runnable runnable = new Runnable() {
         public void run () {
             update();
         handler.postDelayed(this,50); 
         
      }
    };

    private void update(){
    	
    	Config.time = (int)((System.currentTimeMillis() - Config.startTime+Config.pauseTime) / 1000);
    	time.setText("时间:"+Config.time);
    	
    	
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if (keyCode==KeyEvent.KEYCODE_BACK) {
			showDialogJifen();
			Config.pauseTime=Config.time;
			//handler.removeCallbacks(runnable);
			return true;
		}
    	else
    	{
    		//runnable.run();
    		//handler.notify();
    		return super.onKeyDown(keyCode, event);
    	}
    }
    
    private void showDialogJifen(){
    	Builder mDialog=new AlertDialog.Builder(this);
    	mDialog.setTitle("你的积分");
    	mDialog.setMessage("确定退出吗？你当前积分是:"+Config.time);
    	mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				saveGameJifen(Config.time);//上传积分
				finish();
			}
		});
    	
    	mDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
    	
    	mDialog.show();
    }
    
    //上传本次游戏积分
    
    /**
     * @param vJifen 本次游戏的积分
     */
    private void saveGameJifen(int vJifen) {
    	
    	
    	 mJifen=new GameJifen();
    	 mJifen.setPlayer(mUser);
    	 mJifen.setJifen(vJifen);
    	 mJifen.setGameId("0303003");
    	 mJifen.setExRate((float)0.38);
    	 
    	 mJifen.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("你成功获得一次积分");
				addGameJifenToUser();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("fail");
			}
		});
    	  
    	  
	}
    
    /**
	 * 添加积分到用户的积分信息中
	 */
	private void addGameJifenToUser(){
		if(TextUtils.isEmpty(mUser.getObjectId()) || 
				TextUtils.isEmpty(mJifen.getObjectId())){
			toast("当前用户或者当前Card对象的object为空");
			return;
		}
		
		BmobRelation jifens = new BmobRelation();
		jifens.add(mJifen);
		mUser.setGameJifen(jifens);
		mUser.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("已成功添加到用户的积分信息中");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("很遗憾，用户的积分信息添加失败");
			}
		});
	}

	/**
	 * 自定义一个Toast方法
	 * 
	 * @param msg
	 *            要输出的提示信息
	 */
	private void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}



}
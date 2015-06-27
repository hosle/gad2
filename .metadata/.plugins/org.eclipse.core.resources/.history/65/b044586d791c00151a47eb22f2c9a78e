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
import com.game.operator.*;

import a.This;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityXXH extends Activity  {
	
	private TextView time;
	private Handler handler = new Handler();
	
	private ImageButton sendButton,personalButton;
	GamePintuLayout mGameView;
	
	private JifenManager jifenManager=JifenManager.getInstance(this);
	
	//private BmobUserManager userManager = BmobUserManager.getInstance(this);
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xxh_main);
		
		Bundle bundle=getIntent().getExtras();
		
	
		sendButton=(ImageButton)findViewById(R.id.btn_sendgame);
		personalButton = (ImageButton)findViewById(R.id.btn_personial);
		personalButton.setEnabled(false);

		sendButton=(ImageButton)findViewById(R.id.btn_sendgame);
		personalButton = (ImageButton)findViewById(R.id.btn_personial);


		
		time = (TextView) findViewById(R.id.time);
		Config.startTime = System.currentTimeMillis();
		
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable,50); 
		

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
				//Intent intent = new Intent(MainActivityXXH.this, SelectImage.class);
				//startActivity(intent);
				Toast t = Toast.makeText(getApplicationContext(),"当前处于定制中，要再次定制请返回！", Toast.LENGTH_LONG); 
	        	t.setGravity(Gravity.CENTER, 0, 0); 
	        	t.show(); 
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
    	
    	Config.time = (int)((System.currentTimeMillis() - Config.startTime) / 1000);
    	time.setText("时间:"+Config.time);
    	
    	
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if (keyCode==KeyEvent.KEYCODE_BACK) {
			showDialogJifen();
			//Config.pauseTime=Config.time;
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
    	final int score=Config.time;
    	mDialog.setTitle("你的积分");
    	mDialog.setMessage("确定退出吗？你当前积分是:"+score);
    	mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				jifenManager.saveGameJifen(score);//上传积分
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
    
  



}
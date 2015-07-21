package com.game.xxh;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.game.adshow.showBannerAd;
import com.game.config.Config;
import com.game.jifen.GameJifen;
import com.game.pintu.ContactActivity;
import com.game.pintu.R;
import com.game.pintu.SelectImage;
import com.game.xxh.view.AutoGamePintuLayout;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PintuMainActivity_customized extends Activity  {
	
	private TextView time;
	private Handler handler = new Handler();
	
	private ImageButton sendButton,personalButton;
	GamePintuLayout mGameView;
	
	//广告
	private AdView adView_youmi;
	
	//private AdJifenManager adJifenManager;
	
	//private BmobUserManager userManager = BmobUserManager.getInstance(this);
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xxh_main_customized);
		
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
		
		//adJifenManager=AdJifenManager.getInstance(this);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PintuMainActivity_customized.this, ContactActivity.class);
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
		//添加广告
		new showBannerAd(this).showBanner();
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
    		
    		new Quit_PostRecord(this).showDialogQuit();
			//adJifenManager.showDialogQuit();
			//Config.pauseTime=Config.time;
			//handler.removeCallbacks(runnable);
			return true;
		}
    	else
    	{
    		return super.onKeyDown(keyCode, event);
    	}
    }
    
}
package com.game.xxh;

import java.util.TimerTask;

import com.game.config.Config;
import com.game.pintu.ContactActivity;
import com.game.pintu.R;
import com.game.pintu.SelectImage;
import com.game.xxh.view.GamePintuLayout;
import com.userim.util.SerializableBCU;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
<<<<<<< HEAD
import android.util.DisplayMetrics;
import android.view.KeyEvent;
=======
>>>>>>> 6f21da7730e4f3a2d6d8052ea570d6d61c867c14
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivityXXH extends Activity  {
	
	private TextView nandu,time;
	private Handler handler = new Handler();
	
	private ImageButton sendButton,personalButton;
	GamePintuLayout mGameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xxh_main);
		
		
		
		Bundle bundle=getIntent().getExtras();
		
		//SerializableBCU serializableBCU=(SerializableBCU)bundle.get("userlist");	
		//Config.mbcuser=serializableBCU.getUsr();
		
		/*String fatherName=bundle.getString("fatherName");
		if (fatherName=="gameFrag") {
			Config.imageId=R.id.iv1;
			Config.nandu=3;
			
			//Config.metrics = new DisplayMetrics();
			//getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
		}*/
		
		nandu = (TextView) findViewById(R.id.nandu);
		
<<<<<<< HEAD
		sendButton=(Button)findViewById(R.id.btn_sendgame);
		personalButton = (Button)findViewById(R.id.btn_personial);
		personalButton.setEnabled(false);
=======
		sendButton=(ImageButton)findViewById(R.id.btn_sendgame);
		personalButton = (ImageButton)findViewById(R.id.btn_personial);
>>>>>>> 6f21da7730e4f3a2d6d8052ea570d6d61c867c14

		
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
    	
    	Config.time = (int)((System.currentTimeMillis() - Config.startTime) / 1000);
    	time.setText("时间:"+Config.time);
    	
    	
    }






}
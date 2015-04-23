package com.game.xxh;


import com.game.config.Config;
import com.game.pintu.ContactActivity;
import com.game.pintu.R;
import com.game.pintu.SelectImage;
import com.game.xxh.view.AutoGamePintuLayout;
import com.userim.util.SerializableBCU;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AutoImageMainActivityXXH extends Activity  {
	
	private TextView time;
	private Handler handler = new Handler();
	
	private Button sendButton, personalButton;
	AutoGamePintuLayout mGameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xxh_mainautoimage);
		
		Bundle bundle=getIntent().getExtras();
		SerializableBCU serializableBCU=(SerializableBCU)bundle.get("userlist");
			
		Config.mbcuser=serializableBCU.getUsr();
		//nandu = (TextView) findViewById(R.id.nandu);
		
		sendButton = (Button)findViewById(R.id.btn_sendgame_a);
		personalButton = (Button)findViewById(R.id.btn_personial_a);

		
		time = (TextView) findViewById(R.id.time_a);
		Config.startTime = System.currentTimeMillis();
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable,50); 
		
		
		mGameView = (AutoGamePintuLayout) findViewById(R.id.id_gameview_a);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AutoImageMainActivityXXH.this, ContactActivity.class);
				startActivity(intent);
			}
		});

		//handler.removeCallbacks(runnable);
		personalButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AutoImageMainActivityXXH.this, SelectImage.class);
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
    	time.setText("ʱ��:"+Config.time);
    	
    	
    }






}
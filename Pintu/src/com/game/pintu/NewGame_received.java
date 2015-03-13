package com.game.pintu;

import java.util.TimerTask;

import com.game.config.Config;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NewGame_received extends Activity  {
	
	private TextView nandu,time;
	private Handler handler = new Handler();
	
	private Button sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
		
		//���նԷ���ͼƬֵ
		Intent intent=getIntent();
		
		//Config.nandu=Integer.parseInt(intent.getExtras().getString("nandu"));
		Config.imageId=Integer.parseInt(intent.getExtras().getString("picid"));
		//Config.imageId=R.id.iv1;
		//Config.nandu=intent.getExtra("nandu");
		//Config.imageId=intent.getIntExtra("picid", R.id.iv1);
		
		setContentView(R.layout.new_game);
		
		nandu = (TextView) findViewById(R.id.nandu);
		
		sendButton=(Button)findViewById(R.id.btn_sendgame);

		
		time = (TextView) findViewById(R.id.time);
		Config.startTime = System.currentTimeMillis();
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable,50); 
		
		switch(Config.nandu){
		case 3:
			nandu.setText("�Ѷ�:��");
			break;
		case 4:
			nandu.setText("�Ѷ�:һ��");
			break;
		case 5:
			nandu.setText("�Ѷ�:����");
			break;
	
		}
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NewGame_received.this, ContactActivity.class);
				startActivity(intent);
			}
		});

		//handler.removeCallbacks(runnable);

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



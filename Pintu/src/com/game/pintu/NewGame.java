package com.game.pintu;

import java.util.TimerTask;

import com.game.config.Config;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NewGame extends Activity  {
	
	private TextView  time;
	private Handler handler = new Handler();
	
	private Button sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game);
		
		
		sendButton=(Button)findViewById(R.id.btn_sendgame);

		
		time = (TextView) findViewById(R.id.time);
		Config.startTime = System.currentTimeMillis();
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable,50); 
		

		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NewGame.this, ContactActivity.class);
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
    	time.setText("时间:"+Config.time);
    	
    	
    }






}



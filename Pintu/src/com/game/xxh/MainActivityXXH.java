package com.game.xxh;

import java.util.TimerTask;

import com.game.config.Config;
import com.game.pintu.ContactActivity;
import com.game.pintu.R;
import com.game.pintu.SelectImage;
import com.game.xxh.view.GamePintuLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityXXH extends Activity  {
	
	private TextView nandu,time;
	private Handler handler = new Handler();
	
	private Button sendButton,personalButton;
	GamePintuLayout mGameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xxh_main);
		
		nandu = (TextView) findViewById(R.id.nandu);
		
		sendButton=(Button)findViewById(R.id.btn_sendgame);
		personalButton = (Button)findViewById(R.id.btn_personial);

		
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
		
		sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivityXXH.this, ContactActivity.class);
				startActivity(intent);
			}
		});

		//handler.removeCallbacks(runnable);
		personalButton.setOnClickListener(new View.OnClickListener() {
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

   //退出时，更新本次游戏的积分
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if (keyCode==KeyEvent.KEYCODE_BACK) {
		showdialog();
	}
	return false;
	
	
   }
    	
    	
   

    protected void showdialog() {
    	AlertDialog.Builder builder = new Builder(MainActivityXXH.this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        
        builder.setPositiveButton("确认", new OnClickListener() {

    		@Override
    		public void onClick(DialogInterface arg0, int arg1) {
    			// TODO Auto-generated method stub
    			arg0.dismiss();
      	        MainActivityXXH.this.finish();
    		}
            });
        
        builder.setNegativeButton("取消", new OnClickListener() {
    		
    		@Override
    		public void onClick(DialogInterface arg0, int arg1) {
    			// TODO Auto-generated method stub
    			arg0.dismiss();
    		}
          });
        builder.create().show();
	}
  
}
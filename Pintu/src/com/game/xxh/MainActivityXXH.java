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
		// TODO �Զ����ɵķ������
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
			nandu.setText("�Ѷ�:��");
			break;
		case 4:
			nandu.setText("�Ѷ�:һ��");
			break;
		case 5:
			nandu.setText("�Ѷ�:����");
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
    	time.setText("ʱ��:"+Config.time);
    	
    	
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
    	mDialog.setTitle("��Ļ���");
    	mDialog.setMessage("ȷ���˳����㵱ǰ������:"+Config.time);
    	mDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				saveGameJifen(Config.time);//�ϴ�����
				finish();
			}
		});
    	
    	mDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
    	
    	mDialog.show();
    }
    
    //�ϴ�������Ϸ����
    
    /**
     * @param vJifen ������Ϸ�Ļ���
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
				toast("��ɹ����һ�λ���");
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
	 * ��ӻ��ֵ��û��Ļ�����Ϣ��
	 */
	private void addGameJifenToUser(){
		if(TextUtils.isEmpty(mUser.getObjectId()) || 
				TextUtils.isEmpty(mJifen.getObjectId())){
			toast("��ǰ�û����ߵ�ǰCard�����objectΪ��");
			return;
		}
		
		BmobRelation jifens = new BmobRelation();
		jifens.add(mJifen);
		mUser.setGameJifen(jifens);
		mUser.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�ѳɹ���ӵ��û��Ļ�����Ϣ��");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("���ź����û��Ļ�����Ϣ���ʧ��");
			}
		});
	}

	/**
	 * �Զ���һ��Toast����
	 * 
	 * @param msg
	 *            Ҫ�������ʾ��Ϣ
	 */
	private void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}



}
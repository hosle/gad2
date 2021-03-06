package com.game.xxh;

import java.util.Arrays;
import java.util.List;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

import com.game.Game;
import com.game.adshow.showBannerAd;
import com.game.config.Config;
import com.game.operator.AdJifenManager;
import com.game.operator.GameManager;
import com.game.operator.Quit_PostRecord;
import com.game.pintu.ContactActivity;
import com.game.pintu.R;
import com.game.util.HttpUtils;
import com.game.xxh.view.AutoGamePintuLayout;
import com.game.xxh.view.GamePintuLayout;
import com.userim.util.SerializableBCU;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PintuMainActivity_0 extends Activity {

	private TextView time;
	private Handler handler = new Handler();

	private ImageButton sendButton, personalButton;
	private Button mButton;
	AutoGamePintuLayout mGameView;
	private Game currentGame;
	private final String[] GFgameID = { "00001", "00002", "10001", "00003" };
	private List<String> GFgameIDList;
	
	private  Handler mhandler;
	private showBannerAd mShowBannerAd;
	
	//private AdJifenManager adJifenManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_xxh_main);

		Bundle bundle = getIntent().getExtras();
		SerializableBCU serializableBCU = (SerializableBCU) bundle
				.get("userlist");

		Config.mbcuser = serializableBCU.getUsr();
		// nandu = (TextView) findViewById(R.id.nandu);

		//adJifenManager=AdJifenManager.getInstance(this);
				
		sendButton = (ImageButton) findViewById(R.id.btn_sendgame);
		personalButton = (ImageButton) findViewById(R.id.btn_personial);


		time = (TextView) findViewById(R.id.time);
		Config.startTime = System.currentTimeMillis();
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, 50);

		currentGame = GameManager.getInstance(this).getCurrentGame();
		// tempGame.getPreference();
		GFgameIDList = Arrays.asList(GFgameID);

		mGameView = (AutoGamePintuLayout) findViewById(R.id.id_gameview);

		mhandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x1233) {
					String resultString=msg.obj.toString();
					Log.i("test", "handler"+resultString);
					toast(resultString);
				}
				
			}
		};
		
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				toast(currentGame.getGameId());

				if (GFgameIDList.contains(currentGame.getGameId())) {
					toast("此游戏来自官方，无须分享！");

				} else {

					Intent intent = new Intent(PintuMainActivity_0.this,
							ContactActivity.class);
					startActivity(intent);
				}

			}
		});

		// handler.removeCallbacks(runnable);
		personalButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(AutoImageMainActivityXXH.this,
						SelectImage.class);
				startActivity(intent);
				finish();*/
				Intent intent = new Intent(PintuMainActivity_0.this,
						PersonalPageGameActivity.class);
				startActivity(intent);
				finish();
			}
		});

/*		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("test", "click");
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg=new Message();
						String result = HttpUtils.doGet("http://219.223.240.65:3000/bmob");
						msg.what=0x1233;
						msg.obj=result;
						mhandler.sendMessage(msg);
					}
				}.start();
				

				if (result!=null) {
					toast(result);
				}else {
					toast("result 为空");
				}
				//toast(result);
				//mHttpUtils.execute("http://219.223.240.65:3000/bmob");

			}
		});*/
		
		//添加广告banner
		mShowBannerAd=new showBannerAd(this);
		mShowBannerAd.showBanner();

	}
	

	private Runnable runnable = new Runnable() {
		public void run() {
			update();
			handler.postDelayed(this, 50);

		}
	};

	private void update() {

		Config.time = (int) ((System.currentTimeMillis() - Config.startTime) / 1000);
		time.setText("时间:" + Config.time);

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			//adJifenManager.showDialogQuit();
			new Quit_PostRecord(this).showDialogQuit();
			//showDialogJifen();
			
			return true;
		}
    	else
    	{
    		return super.onKeyDown(keyCode, event);
    	}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mShowBannerAd.setIsrun(false);
		mShowBannerAd.rmThread();
		super.onDestroy();
	}
	

	public void toast(String t) {
		Toast.makeText(this, t, 200).show();
	}

}
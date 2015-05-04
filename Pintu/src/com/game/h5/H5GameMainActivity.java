package com.game.h5;


import com.game.Game;
import com.game.config.Config;
import com.game.operator.GameManager;
import com.game.pintu.R;
import com.userim.util.SerializableBCU;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class H5GameMainActivity extends Activity  {
	
	private TextView time;
	private Handler handler = new Handler();
	
	private ImageButton sendButton, personalButton;
	private WebView webViewGame;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_h5);
		
		bundle=getIntent().getExtras();
		SerializableBCU serializableBCU=(SerializableBCU)bundle.get("userlist");
			
		Config.mbcuser=serializableBCU.getUsr();
		//nandu = (TextView) findViewById(R.id.nandu);
		
		sendButton = (ImageButton)findViewById(R.id.btn_sendgame_h);
		personalButton = (ImageButton)findViewById(R.id.btn_personial_h);
		webViewGame=(WebView)findViewById(R.id.webview_h5game);

		
		initWebView();
		
		time = (TextView) findViewById(R.id.time_h);
		Config.startTime = System.currentTimeMillis();
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable,50); 
		
		
		
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(AutoImageMainActivityXXH.this, ContactActivity.class);
				//startActivity(intent);
				Toast t = Toast.makeText(getApplicationContext(),"����Ϸ���Թٷ������������", Toast.LENGTH_LONG); 
	        	t.setGravity(Gravity.CENTER, 0, 0); 
	        	t.show(); 
			}
		});

		//handler.removeCallbacks(runnable);
		personalButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(H5GameMainActivity.this, SettingPersonalH5.class);
				startActivity(intent);
				finish();
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
    
    private void initWebView(){
    	// ��ʼ��web����
    	Game tempGame=GameManager.getInstance(this).getCurrentGame();
    	String webUrlString="";
    	
    	if (bundle.getString("gameUrl")==null) {
    		//toast("��preferences�õ�");
    		webUrlString=tempGame.getPreference();
		}else{
			//toast("�Ӵ��εõ�");
			webUrlString=bundle.getString("gameUrl");
			
		}
			
    	toast(webUrlString);
    	
    	WebSettings webSettings = webViewGame.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//�û����û���,û��������

        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);

        //��ȡ����
        webViewGame.setFocusable(true);
        webViewGame.setFocusableInTouchMode(true);
        webViewGame.requestFocus();
        webViewGame.requestFocusFromTouch();
        webViewGame.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			};
		});
    	webViewGame.loadUrl(webUrlString);
    	
    	}
    

    public void toast(String t){
		Toast.makeText(this, t, 200).show();
	}





}
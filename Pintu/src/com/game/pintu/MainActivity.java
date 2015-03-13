package com.game.pintu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;

import com.game.config.Config;
import com.userim.util.SerializableBCU;

public class MainActivity extends Activity implements OnClickListener {
	private Button[] button;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
		
		
		setContentView(R.layout.activity_main_game);
		button = new Button[4];
		
		button[0] = (Button) findViewById(R.id.btnnewGame);
		button[1] = (Button) findViewById(R.id.ranked);	
		button[2] = (Button) findViewById(R.id.help);
		button[3] = (Button) findViewById(R.id.exit);
		for(int i = 0; i < 4; i++){
			button[i].setOnClickListener(this);
		}
		
		Bundle bundle=getIntent().getExtras();
		SerializableBCU serializableBCU=(SerializableBCU)bundle.get("userlist");
			
		Config.mbcuser=serializableBCU.getUsr();
		
		
		//Toast.makeText(getBaseContext(), Config.metrics.widthPixels + " "+Config.metrics.heightPixels, 0).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent;
		
		
		int id = v.getId();
		if (id == R.id.btnnewGame) {
			intent = new Intent(this,SelectImage.class);
			startActivity(intent);
		} else if (id == R.id.ranked) {
			intent = new Intent(this,Ranked.class);
			startActivity(intent);
		} else if (id == R.id.help) {
			intent = new Intent(this,Help.class);
			startActivity(intent);
		} else if (id == R.id.exit) {
			finish();
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	

}

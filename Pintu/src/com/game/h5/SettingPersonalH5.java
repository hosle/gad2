package com.game.h5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.xxh.MainActivityXXH;
import com.game.operator.*;
import com.game.pintu.R;


public class SettingPersonalH5 extends Activity {
	
	private TextView[] imgView=new TextView[6];
	private final int[] imgId={R.id.iv1_h5,R.id.iv2_h5,R.id.iv3_h5,R.id.iv4_h5,R.id.iv5_h5,R.id.iv6_h5};
	private final String[] mUrls={"http://mail.163.com/","http://mail.qq.com","http://m.baidu.com","http://www.bmob.cn","http://m.taobao.com","http://m.jd.com"};
	private GameManager gamemanager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_setting_personalgame);
		
		gamemanager=GameManager.getInstance(this);
		
		imgView[0]=(TextView)this.findViewById(R.id.iv1_h5);
		imgView[1]=(TextView)this.findViewById(R.id.iv2_h5);
		imgView[2]=(TextView)this.findViewById(R.id.iv3_h5);
		imgView[3]=(TextView)this.findViewById(R.id.iv4_h5);
		imgView[4]=(TextView)this.findViewById(R.id.iv5_h5);
		imgView[5]=(TextView)this.findViewById(R.id.iv6_h5);
		
		for (int i = 0; i < imgId.length; i++) {
			imgView[i].setOnClickListener(new myOnClickListener(i));
			
		}
		
	}
	
	
	Toast mToast;

	public void showToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
	
	public void showToast(int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), resId,
					Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
	}
	
	private class myOnClickListener implements OnClickListener {

		private int position;
		private myOnClickListener(int x) {
			// TODO Auto-generated method stub
			position=x;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			gamemanager.saveMyGame(mUrls[position],"h5");//上传自定义游戏
			
			Intent intent = new Intent(SettingPersonalH5.this, H5GameMainActivityPersonal.class);
			intent.putExtra("gameURL", mUrls[position]);
			startActivity(intent);
			finish();
		}
		
	}
	

}

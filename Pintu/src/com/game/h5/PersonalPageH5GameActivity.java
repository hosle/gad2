package com.game.h5;

import com.game.adapter.PersonalGameH5SettingAdapter;
import com.game.config.Config;
import com.game.operator.GameManager;
import com.game.pintu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class PersonalPageH5GameActivity extends Activity {
	
	private GameManager gamemanager;
	
	private ListView mListView;
	private EditText mEditTextDescription;
	private EditText mEditTextURL;
	private Button btnStartGame;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_h5game);
      
        gamemanager=GameManager.getInstance(this);
		
        mListView = (ListView) findViewById(R.id.game_info_h5);
        //添加并且显示
        mListView.setAdapter(new PersonalGameH5SettingAdapter(this));
      
       /* mListView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				
				return true;
			}
		});*/
        btnStartGame = (Button)findViewById(R.id.btn_startGame_h5);
        btnStartGame.setOnClickListener(new btnOnClickListener());
     
        
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
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
	
	
	private class btnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			//游戏说明
	        //View view=mListView.getChildAt(0);	        
			//mEditTextDescription=(EditText)view.findViewById(R.id.edt_personallist);
			
			//游戏地址
			View view2=mListView.getChildAt(1);
			mEditTextURL=(EditText)view2.findViewById(R.id.edt_personallist);
			
			String url=mEditTextURL.getText().toString().trim();
			if (!TextUtils.isEmpty(url)) {
				//做网址正则表达式判断
				gamemanager.saveMyGame(getFullUrl(url),"h5");//上传自定义游戏
				
				Intent intent = new Intent(PersonalPageH5GameActivity.this, H5GameMainActivity_customized.class);
				intent.putExtra("gameURL", getFullUrl(url));
				startActivity(intent);
				finish();
			}else {
				showToast("请输入游戏地址或选择上面的游戏项");
			}
			
		}
		
	}
	
	private String getFullUrl(String str){
		String result=null;
		if (str.matches("^http://.*")||str.matches("^https://.*")) {
			result=str;
		}else {
			result="http://"+str;
		}
		
		return result;
	}

}

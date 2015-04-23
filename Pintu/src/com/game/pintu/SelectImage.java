package com.game.pintu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.game.config.Config;
import com.game.xxh.MainActivityXXH;
import com.userim.util.SerializableBCU;


public class SelectImage extends Activity implements OnClickListener,OnCheckedChangeListener {
	private ImageView[] imgView = new ImageView[6];
	private RadioGroup r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.select_image);
		imgView[0] = (ImageView) findViewById(R.id.iv1);
		imgView[1] = (ImageView) findViewById(R.id.iv2);
		imgView[2] = (ImageView) findViewById(R.id.iv3);
		imgView[3] = (ImageView) findViewById(R.id.iv4);
		imgView[4] = (ImageView) findViewById(R.id.iv5);
		imgView[5] = (ImageView) findViewById(R.id.iv6);
		
		for(int i = 0; i < 6 ; i++){
			imgView[i].setOnClickListener(this);
		}
		
		
		r = (RadioGroup) findViewById(R.id.radioGroup1);
		r.setOnCheckedChangeListener(this);
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
		
		
		/*Bundle bundle=getIntent().getExtras();
		SerializableBCU serializableBCU=(SerializableBCU)bundle.get("userlist");
			
		Config.mbcuser=serializableBCU.getUsr();*/
		
	}
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Config.imageId = v.getId();
		//Intent intent = new Intent(this,NewGame.class);
		Intent intent = new Intent(this,MainActivityXXH.class);
		startActivity(intent);
		finish();
		/*Toast t = Toast.makeText(getApplicationContext(),"数据保存完成！", Toast.LENGTH_LONG); 
    	t.setGravity(Gravity.CENTER, 0, 0); 
    	t.show();*/
		
		
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.radio0) {
			Config.nandu = 3;
		} else if (checkedId == R.id.radio1) {
			Config.nandu = 4;
		} else if (checkedId == R.id.radio2) {
			Config.nandu = 5;
		}
		
		
		
	}
	
	

}

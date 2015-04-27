package com.game.pintu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.game.config.Config;
import com.game.xxh.MainActivityXXH;
import com.game.operator.*;


public class SelectImage extends Activity implements OnCheckedChangeListener {
	
	private ImageView[] imgView = new ImageView[6];
	private RadioGroup r;
	private GameManager gamemanager;
	private int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.select_image);
		
		gamemanager=GameManager.getInstance(this);
		
		imgView[0] = (ImageView) findViewById(R.id.iv1);
		imgView[1] = (ImageView) findViewById(R.id.iv2);
		imgView[2] = (ImageView) findViewById(R.id.iv3);
		imgView[3] = (ImageView) findViewById(R.id.iv4);
		imgView[4] = (ImageView) findViewById(R.id.iv5);
		imgView[5] = (ImageView) findViewById(R.id.iv6);
		
		
		imgView[0].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					OnSelectImage(1);
					// TODO Auto-generated method stub
					Config.imageId = v.getId();
					
					gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
					Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
					startActivity(intent);
					finish();
				}
			});
		imgView[1].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnSelectImage(2);
				// TODO Auto-generated method stub
				Config.imageId = v.getId();
				
				gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
				Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
				startActivity(intent);
				finish();
			}
		});
		imgView[2].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnSelectImage(3);
				// TODO Auto-generated method stub
				Config.imageId = v.getId();
				
				gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
				Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
				startActivity(intent);
				finish();
			}
		});
		imgView[3].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnSelectImage(4);
				// TODO Auto-generated method stub
				Config.imageId = v.getId();
				
				gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
				Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
				startActivity(intent);
				finish();
			}
		});
		imgView[4].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnSelectImage(5);
				// TODO Auto-generated method stub
				Config.imageId = v.getId();
				
				gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
				Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
				startActivity(intent);
				finish();
			}
		});
		imgView[5].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnSelectImage(6);
				// TODO Auto-generated method stub
				Config.imageId = v.getId();
				
				gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
				Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
				startActivity(intent);
				finish();
			}
		});

		
		
		r = (RadioGroup) findViewById(R.id.radioGroup1);
		r.setOnCheckedChangeListener(this);
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
		
		
	}
	
	/*public class OnSelectImage implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根	
			Config.imageId = v.getId();
			
			gamemanager.saveMyGame(Config.imageId+"");//上传自定义游戏
			Intent intent = new Intent(,MainActivityXXH.class);
			startActivity(intent);
			finish();
		}
		
	}*/
	
	@SuppressLint("SdCardPath") private void OnSelectImage(int num)
	{
		int imgName = 0;
		int icon = 0;
		if(num == 1)
		{
		    imgName = R.id.iv1;
		    icon = R.drawable.icon1;
		}
		if(num == 2)
		{
		    imgName = R.id.iv2;
		    icon = R.drawable.icon2;
		}
		if(num == 3)
		{
		    imgName = R.id.iv3;
		    icon = R.drawable.icon3;
		}
		if(num == 4)
		{
		    imgName = R.id.iv4;
		    icon = R.drawable.icon4;
		}
		if(num == 5)
		{
		    imgName = R.id.iv5;
		    icon = R.drawable.icon5;
		}
		if(num == 6)
		{
		    imgName = R.id.iv6;
		    icon = R.drawable.icon6;
		}
		
		
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;
		String str = "_"+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
		
		String nameImg = Integer.toString(imgName)+str;
		String PnameImg = Integer.toString(imgName);
		
		String newimg[];
		newimg = new String[1];
		newimg[0] = nameImg;//str
		File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }

		com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
		Bitmap img = BitmapFactory.decodeResource(this.getResources(), icon);
		
		saveMyBitmapxxh(nameImg,img);
		saveMyBitmapxxh(PnameImg,img);//用于定制进读取
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
	
	public void saveMyBitmapxxh(String bitName,Bitmap mBitmap)
	{
		File f = new File("/mnt/sdcard/gameimage/" + bitName + ".jpg");
	    try {
		   f.createNewFile();
		}catch (IOException e) {
		   // TODO Auto-generated catch block
		}
		FileOutputStream fOut = null;
		try {
		   fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
		   e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
		    fOut.flush();
		} catch (IOException e) {
		e.printStackTrace();
		}
	    try {
		   fOut.close();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}	
	

}

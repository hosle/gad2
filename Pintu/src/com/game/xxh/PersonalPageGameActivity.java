package com.game.xxh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bmob.btp.file.BTPFileResponse;
import com.game.adapter.PersonalGame1SettingAdapter;
import com.game.config.Config;
import com.game.operator.GameManager;
import com.game.pintu.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class PersonalPageGameActivity extends Activity {
	
	private GameManager gamemanager;
	
	//private String innerPintu="innerPintu";
	private Button btnStartGame;
	private ListView mListView;
	private PersonalGame1SettingAdapter adapter;
	//照片的原始资源地址
	private Uri mUri=null;
	private EditText mEditText=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_game);
        gamemanager=GameManager.getInstance(this);
        //绑定Layout里面的ListView
        mListView = (ListView) findViewById(R.id.game_info);
        
        adapter=new PersonalGame1SettingAdapter(this);
        mListView.setAdapter(adapter);
        
       
        btnStartGame = (Button)findViewById(R.id.btn_startGame);
        btnStartGame.setOnClickListener(new startGameOnClickListener());
		
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			View view=mListView.getChildAt(1);
			mEditText=(EditText)view.findViewById(R.id.edt_personallist);
			mUri=data.getData();
			mEditText.setText(getRealPathFromURI(mUri));
			
		}
	}
	
	private class startGameOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (mUri!=null) {
				ContentResolver resolver = getContentResolver();
                //照片的原始资源地址
                //Uri originalUri = mUri;
                try 
                {
                    //使用ContentProvider通过URI获取原始图片
                    Bitmap mBitmap  = MediaStore.Images.Media.getBitmap(resolver, mUri); 
                    Bitmap photo= Bitmap.createScaledBitmap(mBitmap, (int)(mBitmap.getWidth()*0.3), (int)(mBitmap.getHeight()*0.3), true);  
                    try {
                    	
                    	Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
                		t.setToNow(); // 取得系统时间。
                		int year = t.year;
                		int month = t.month;
                		int date = t.monthDay;
                		int hour = t.hour; // 0-23
                		int minute = t.minute;
                		int second = t.second;
                		String str = "_"+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
                		
                    	saveMyBitmapxxh(str,photo);//用于定制进读取
                    	
                    	String newimg[];
                		newimg = new String[1];
                		newimg[0] = str;
                		File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
                		  if (!destDir.exists()) {
                		   destDir.mkdirs();
                		  }

                		com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
						
                		//清空输入框
                		mEditText.setText("");
                		//adapter.notifyDataSetChanged();
                		//保存相应的参数！
     
                		//newimg = new String[1];
                		//com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
                		//final String NanDu = newimg[0];
                		
                		//Config.imageId = v.getId();
    					
    					//gamemanager.saveMyGame(Config.imageId+"",NanDu);//上传自定义游戏
    					upload();
        				
    					//转换Activity!
    					Intent intent_game = new Intent(PersonalPageGameActivity.this, PintuMainActivity_customized.class);
    					startActivity(intent_game);
        				
        				
        				
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    //process
                  
                } catch (FileNotFoundException e) 
                {
                    e.printStackTrace();
                } catch (IOException e) 
                {
                    e.printStackTrace();
                }
			}else {
				showToast("请选择游戏图片");
			}
		}
		
	}
	
	
	public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
		   //showToast("本地保存完成");
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
	
	
	String downLoadUrl = "";
	/**
	 * @Description:单一文件上传
	 * @param  
	 * @return void
	 * @throws
	 */
	ProgressDialog updialog =null;

	
	
	private void upload(){

		//Game tempGame=GameManager.getInstance(this).getCurrentGame();
		
		updialog = new ProgressDialog(PersonalPageGameActivity.this);
		//saveMyBitmap(str,bmp);
		String newimg[];
		newimg = new String[1];
		com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/newimage.txt",newimg);
		String bitName = newimg[0];
		//showToast(bitName);//测试是否读取到了图像的名称
		
		String filePath = "/mnt/sdcard/gameimage/" + bitName +".jpg";//实验发现同一图片只能传一次
		//showToast(filePath);
		
		updialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		updialog.setTitle("上传中...");
		updialog.setIndeterminate(false);               
		updialog.setCancelable(true);       
		updialog.setCanceledOnTouchOutside(false);  
		updialog.show();//"cc9a6ee19b0211fc6a46b1a4bce30c72"
		BTPFileResponse response = BmobProFile.getInstance(PersonalPageGameActivity.this).upload(filePath, new UploadListener() {

			@Override
			public void onSuccess(String fileName,String url) {
				// TODO Auto-generated method stub
				//downloadName = fileName;
				updialog.dismiss();
				//如果你想得到一个可以直接在客户端显示的图片地址，那么可以使用BmobProFile类的静态方法获取可访问的URL地址,且不建议开启URL签名认证
				String URL = BmobProFile.getInstance(PersonalPageGameActivity.this).signURL(fileName,url,"填入你web后台管理应用密钥中的AccessKey",0,null);
				showLog("MainActivity -onSuccess :"+fileName+",签名后的URL = "+ URL);
				showToast("文件已上传成功："+fileName);
				
				String newimg[];
				newimg = new String[1];
				com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
				final String NanDu = newimg[0];
				//showToast("Nandu:"+NanDu);
				gamemanager.saveMyGame(fileName,NanDu);//上传自定义游戏信息
				
				
				finish();
				
			}

			@Override
			public void onProgress(int ratio) {
				// TODO Auto-generated method stub
				showLog("MainActivity -onProgress :"+ratio);
				updialog.setProgress(ratio);
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				//				showLog("MainActivity -onError :"+statuscode +"--"+errormsg);
				updialog.dismiss();
				showToast("上传出错："+errormsg);
			}
		});

		showLog("upload方法返回的code = "+response.getStatusCode());
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
	
	public static void showLog(String msg) {
		Log.i("BmobPro", msg);
	}

}

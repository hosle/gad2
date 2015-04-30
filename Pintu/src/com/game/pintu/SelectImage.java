package com.game.pintu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.game.config.Config;
import com.game.xxh.MainActivityXXH;
import com.game.operator.*;


public class SelectImage extends Activity implements OnCheckedChangeListener {
	
	private ImageView[] imgView = new ImageView[6];
	private GameManager gamemanager;
	private int flag = 0;
	//private String innerPintu="innerPintu";
	private Button selectimagebnt_xxh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
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
					//Config.imageId = v.getId();
					
					//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
					upload();
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
				//Config.imageId = v.getId();
				
				//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
				upload();
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
				//Config.imageId = v.getId();
				
				//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
				upload();
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
				//Config.imageId = v.getId();
				
				//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
				upload();
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
				//Config.imageId = v.getId();
				
				//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
				upload();
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
				//Config.imageId = v.getId();
				
				//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
				upload();
				Intent intent = new Intent(SelectImage.this, MainActivityXXH.class);
				startActivity(intent);
				finish();
			}
		});

		selectimagebnt_xxh = (Button)findViewById(R.id.selectimagebnt_xxh);
		selectimagebnt_xxh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent;
				if (Build.VERSION.SDK_INT < 19) {
					intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
				} else {
					intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				}
				startActivityForResult(intent, 0);
			}
		});
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
		
		
	}
	
	/*public class OnSelectImage implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������	
			Config.imageId = v.getId();
			
			gamemanager.saveMyGame(Config.imageId+"");//�ϴ��Զ�����Ϸ
			Intent intent = new Intent(,MainActivityXXH.class);
			startActivity(intent);
			finish();
		}
		
	}*/
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) //������Ľ��ʹ��ProcessClickListenerOpenIamge
    {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == RESULT_OK) 
	        {
	                ContentResolver resolver = getContentResolver();
	                //��Ƭ��ԭʼ��Դ��ַ
	                Uri originalUri = data.getData();
	                try 
	                {
	                    //ʹ��ContentProviderͨ��URI��ȡԭʼͼƬ
	                    Bitmap mBitmap  = MediaStore.Images.Media.getBitmap(resolver, originalUri); 
	                    Bitmap photo= Bitmap.createScaledBitmap(mBitmap, (int)(mBitmap.getWidth()*0.08), (int)(mBitmap.getHeight()*0.08), true);  
	                    try {
	                    	
	                    	Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
	                		t.setToNow(); // ȡ��ϵͳʱ�䡣
	                		int year = t.year;
	                		int month = t.month;
	                		int date = t.monthDay;
	                		int hour = t.hour; // 0-23
	                		int minute = t.minute;
	                		int second = t.second;
	                		String str = "_"+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
	                		
	                    	saveMyBitmapxxh(str,photo);//���ڶ��ƽ���ȡ
	                    	
	                    	String newimg[];
	                		newimg = new String[1];
	                		newimg[0] = str;
	                		File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
	                		  if (!destDir.exists()) {
	                		   destDir.mkdirs();
	                		  }

	                		com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
							
	                		//������Ӧ�Ĳ�����
	     
	                		//newimg = new String[1];
	                		//com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
	                		//final String NanDu = newimg[0];
	                		
	                		//Config.imageId = v.getId();
	    					
	    					//gamemanager.saveMyGame(Config.imageId+"",NanDu);//�ϴ��Զ�����Ϸ
	    					upload();
	        				
	        				
	        				//ת��Activity!
	        				Intent intent_game = new Intent(SelectImage.this, MainActivityXXH.class);
	        				startActivity(intent_game);
	        				finish();
	        				
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
	         }
    }
    
	
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
		
		
		/*Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
		t.setToNow(); // ȡ��ϵͳʱ�䡣
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;
		String str = "_"+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
		
		String nameImg = Integer.toString(imgName)+str;*/
		String PnameImg = Integer.toString(imgName);
		
		String newimg[];
		newimg = new String[1];
		newimg[0] = PnameImg;//str
		File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }

		com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
		Bitmap img = BitmapFactory.decodeResource(this.getResources(), icon);
		
		//saveMyBitmapxxh(nameImg,img);
		saveMyBitmapxxh(PnameImg,img);//���ڶ��ƽ���ȡ
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

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}	
	
	
	String downLoadUrl = "";
	/**
	 * @Description:��һ�ļ��ϴ�
	 * @param  
	 * @return void
	 * @throws
	 */
	ProgressDialog updialog =null;

	
	
	private void upload(){

		//Game tempGame=GameManager.getInstance(this).getCurrentGame();
		
		updialog = new ProgressDialog(SelectImage.this);
		//saveMyBitmap(str,bmp);
		String newimg[];
		newimg = new String[1];
		com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/newimage.txt",newimg);
		String bitName = newimg[0];
		//showToast(bitName);//�����Ƿ��ȡ����ͼ�������
		
		String filePath = "/mnt/sdcard/gameimage/" + bitName +".jpg";//ʵ�鷢��ͬһͼƬֻ�ܴ�һ��
		//showToast(filePath);
		
		updialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		updialog.setTitle("�ϴ���...");
		updialog.setIndeterminate(false);               
		updialog.setCancelable(true);       
		updialog.setCanceledOnTouchOutside(false);  
		updialog.show();//"cc9a6ee19b0211fc6a46b1a4bce30c72"
		com.bmob.btp.file.BTPFileResponse response = BmobProFile.getInstance(SelectImage.this).upload(filePath, new UploadListener() {

			@Override
			public void onSuccess(String fileName,String url) {
				// TODO Auto-generated method stub
				//downloadName = fileName;
				updialog.dismiss();
				//�������õ�һ������ֱ���ڿͻ�����ʾ��ͼƬ��ַ����ô����ʹ��BmobProFile��ľ�̬������ȡ�ɷ��ʵ�URL��ַ,�Ҳ����鿪��URLǩ����֤
				String URL = BmobProFile.getInstance(SelectImage.this).signURL(fileName,url,"������web��̨����Ӧ����Կ�е�AccessKey",0,null);
				showLog("MainActivity -onSuccess :"+fileName+",ǩ�����URL = "+ URL);
				showToast("�ļ����ϴ��ɹ���"+fileName);
				
				String newimg[];
				newimg = new String[1];
				com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
				final String NanDu = newimg[0];
				
				gamemanager.saveMyGame(fileName,NanDu);//�ϴ��Զ�����Ϸ
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
				showToast("�ϴ�����"+errormsg);
			}
		});

		showLog("upload�������ص�code = "+response.getStatusCode());
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

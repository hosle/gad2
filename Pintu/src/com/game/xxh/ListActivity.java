package com.game.xxh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.game.config.Config;
import com.game.operator.GameManager;
import com.game.pintu.R;
import com.game.pintu.SelectImage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListActivity extends Activity {
	
	private GameManager gamemanager;
	private int flag = 0;
	//private String innerPintu="innerPintu";
	private Button selectimagebnt_xxh;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preasonal_game);
        gamemanager=GameManager.getInstance(this);
        //��Layout�����ListView
        ListView list = (ListView) findViewById(R.id.game_info);
        
        //���ɶ�̬���飬��������
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.game_bar);//ͼ����Դ��ID
        map.put("ItemTitle", "˵��");
        listItem.add(map);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("ItemImage", R.drawable.game_bar);//ͼ����Դ��ID
        map1.put("ItemTitle", "����ͼƬ");
        listItem.add(map1);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("ItemImage", R.drawable.game_bar);//ͼ����Դ��ID
        map2.put("ItemTitle", "��ϷͼƬ");
        listItem.add(map2);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("ItemImage", R.drawable.game_bar);//ͼ����Դ��ID
        map3.put("ItemTitle", "����");
        listItem.add(map3);
        
        //������������Item�Ͷ�̬�����Ӧ��Ԫ��
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ 
            R.layout.list_items,//ListItem��XMLʵ��
            //��̬������ImageItem��Ӧ������        
            new String[] {"ItemImage","ItemTitle"}, 
            //ImageItem��XML�ļ������һ��ImageView,1��TextView ID
            new int[] {R.id.ItemImage,R.id.ItemTitle}
        );
       
        //��Ӳ�����ʾ
        list.setAdapter(listItemAdapter);
        
        //��ӵ��
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				setTitle("�����"+arg2+"����Ŀ");
			}
		});
        
      //��ӳ������
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("�����˵�-ContextMenu");   
				menu.add(0, 0, 0, "���������˵�0");
				menu.add(0, 1, 0, "���������˵�1");   
			}
		}); 
        
        selectimagebnt_xxh = (Button)findViewById(R.id.selectimagebnt_game);
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
	
	//�����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ"); 
		return super.onContextItemSelected(item);
	}
	
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
	        				Intent intent_game = new Intent(ListActivity.this, MainActivityXXH.class);
	        				startActivity(intent_game);
	        				//finish();
	        				
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
		
		updialog = new ProgressDialog(ListActivity.this);
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
		com.bmob.btp.file.BTPFileResponse response = BmobProFile.getInstance(ListActivity.this).upload(filePath, new UploadListener() {

			@Override
			public void onSuccess(String fileName,String url) {
				// TODO Auto-generated method stub
				//downloadName = fileName;
				updialog.dismiss();
				//�������õ�һ������ֱ���ڿͻ�����ʾ��ͼƬ��ַ����ô����ʹ��BmobProFile��ľ�̬������ȡ�ɷ��ʵ�URL��ַ,�Ҳ����鿪��URLǩ����֤
				String URL = BmobProFile.getInstance(ListActivity.this).signURL(fileName,url,"������web��̨����Ӧ����Կ�е�AccessKey",0,null);
				showLog("MainActivity -onSuccess :"+fileName+",ǩ�����URL = "+ URL);
				showToast("�ļ����ϴ��ɹ���"+fileName);
				
				String newimg[];
				newimg = new String[1];
				com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
				final String NanDu = newimg[0];
				//showToast("Nandu:"+NanDu);
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

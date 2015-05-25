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
        //绑定Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.game_info);
        
        //生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.game_bar);//图像资源的ID
        map.put("ItemTitle", "说明");
        listItem.add(map);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("ItemImage", R.drawable.game_bar);//图像资源的ID
        map1.put("ItemTitle", "背景图片");
        listItem.add(map1);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("ItemImage", R.drawable.game_bar);//图像资源的ID
        map2.put("ItemTitle", "游戏图片");
        listItem.add(map2);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("ItemImage", R.drawable.game_bar);//图像资源的ID
        map3.put("ItemTitle", "声音");
        listItem.add(map3);
        
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
            R.layout.list_items,//ListItem的XML实现
            //动态数组与ImageItem对应的子项        
            new String[] {"ItemImage","ItemTitle"}, 
            //ImageItem的XML文件里面的一个ImageView,1个TextView ID
            new int[] {R.id.ItemImage,R.id.ItemTitle}
        );
       
        //添加并且显示
        list.setAdapter(listItemAdapter);
        
        //添加点击
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				setTitle("点击第"+arg2+"个项目");
			}
		});
        
      //添加长按点击
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("长按菜单-ContextMenu");   
				menu.add(0, 0, 0, "弹出长按菜单0");
				menu.add(0, 1, 0, "弹出长按菜单1");   
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
	
	//长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目"); 
		return super.onContextItemSelected(item);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) //与上面的结合使用ProcessClickListenerOpenIamge
    {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == RESULT_OK) 
	        {
	                ContentResolver resolver = getContentResolver();
	                //照片的原始资源地址
	                Uri originalUri = data.getData();
	                try 
	                {
	                    //使用ContentProvider通过URI获取原始图片
	                    Bitmap mBitmap  = MediaStore.Images.Media.getBitmap(resolver, originalUri); 
	                    Bitmap photo= Bitmap.createScaledBitmap(mBitmap, (int)(mBitmap.getWidth()*0.08), (int)(mBitmap.getHeight()*0.08), true);  
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
							
	                		//保存相应的参数！
	     
	                		//newimg = new String[1];
	                		//com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
	                		//final String NanDu = newimg[0];
	                		
	                		//Config.imageId = v.getId();
	    					
	    					//gamemanager.saveMyGame(Config.imageId+"",NanDu);//上传自定义游戏
	    					upload();
	        				
	        				
	        				//转换Activity!
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
	 * @Description:单一文件上传
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
		//showToast(bitName);//测试是否读取到了图像的名称
		
		String filePath = "/mnt/sdcard/gameimage/" + bitName +".jpg";//实验发现同一图片只能传一次
		//showToast(filePath);
		
		updialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		updialog.setTitle("上传中...");
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
				//如果你想得到一个可以直接在客户端显示的图片地址，那么可以使用BmobProFile类的静态方法获取可访问的URL地址,且不建议开启URL签名认证
				String URL = BmobProFile.getInstance(ListActivity.this).signURL(fileName,url,"填入你web后台管理应用密钥中的AccessKey",0,null);
				showLog("MainActivity -onSuccess :"+fileName+",签名后的URL = "+ URL);
				showToast("文件已上传成功："+fileName);
				
				String newimg[];
				newimg = new String[1];
				com.game.pintu.predict.readTxtFile("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
				final String NanDu = newimg[0];
				//showToast("Nandu:"+NanDu);
				gamemanager.saveMyGame(fileName,NanDu);//上传自定义游戏
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

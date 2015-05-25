package com.game.h5;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HtmlActivity extends Activity {
	
	private GameManager gamemanager;
	
	private EditText mEditText;
	private Button mBtnUrl;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peasonal_html);
        
        gamemanager=GameManager.getInstance(this);
		
		mEditText=(EditText)findViewById(R.id.etxt_html);
		mBtnUrl=(Button)findViewById(R.id.btn_html);
        //绑定Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.game_html);
        
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
        
       
		
		Config.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(Config.metrics);
    }
	
	//长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目"); 
		return super.onContextItemSelected(item);
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
			//做网址正则表达式判断
			String url=mEditText.getText().toString();
			if (url!="") {
				gamemanager.saveMyGame(getFullUrl(url),"h5");//上传自定义游戏
				
				Intent intent = new Intent(HtmlActivity.this, H5GameMainActivityPersonal.class);
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

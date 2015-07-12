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
        //��Layout�����ListView
        ListView list = (ListView) findViewById(R.id.game_html);
        
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
       
        //���Ӳ�����ʾ
        list.setAdapter(listItemAdapter);
        
        //���ӵ��
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				setTitle("�����"+arg2+"����Ŀ");
			}
		});
        
      //���ӳ������
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("�����˵�-ContextMenu");   
				menu.add(0, 0, 0, "���������˵�0");
				menu.add(0, 1, 0, "���������˵�1");   
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
			//����ַ�������ʽ�ж�
			String url=mEditText.getText().toString();
			if (url!="") {
				gamemanager.saveMyGame(getFullUrl(url),"h5");//�ϴ��Զ�����Ϸ
				
				Intent intent = new Intent(HtmlActivity.this, H5GameMainActivityPersonal.class);
				intent.putExtra("gameURL", getFullUrl(url));
				startActivity(intent);
				finish();
			}else {
				showToast("��������Ϸ��ַ��ѡ���������Ϸ��");
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
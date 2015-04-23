package com.bmob.im.demo.ui.fragment;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.bmob.im.bean.BmobChatUser;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.GamesGridAdapter1;
import com.bmob.im.demo.adapter.GamesGridAdapter2;
import com.bmob.im.demo.ui.FragmentBase;
import com.bmob.im.demo.util.CollectionUtils;
import com.bmob.im.demo.xxh.OfficalActivity;
import com.game.config.Config;
import com.game.pintu.MainActivity;
import com.game.pintu.SelectImage;
import com.game.xxh.AutoImageMainActivityXXH;
import com.game.xxh.MainActivityXXH;
import com.userim.util.SerializableBCU;

public class ShowFragmentXXH1 extends FragmentBase {

	Fragment mContent;
	private ImageButton imgbnt2;
	private ImageButton imgbnt3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.xxh_gamelayout1, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		initView();
		imgbnt2 = (ImageButton)findViewById(R.id.xxh_imagebnt2);
		imgbnt2.setOnClickListener(new ChangeFragment2());
		
		imgbnt3 = (ImageButton)findViewById(R.id.xxh_imagebnt3);
		imgbnt3.setOnClickListener(new ChangeFragment3());
	}
	
	


	private class ChangeFragment2 implements OnClickListener
    {     
        @Override  
        public void onClick(View v) 
        {  
        	// TODO Auto-generated method stub  
        	ShowFragmentXXH2 demoFragment = new ShowFragmentXXH2();
        	
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, demoFragment);
            transaction.commit();
       
        }  
    }
	
	private class ChangeFragment3 implements OnClickListener
    {     
        @Override  
        public void onClick(View v) 
        {  
        	// TODO Auto-generated method stub  
        	ShowFragmentXXH3 demoFragment = new ShowFragmentXXH3();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, demoFragment);
            transaction.commit();
       
        }  
    }
	
	
	private void initView(){
		initTopBarForOnlyTitle("游戏");
		
		GridView mGridView=(GridView)findViewById(R.id.gridgame);
		mGridView.setAdapter(new GamesGridAdapter1(getActivity()));
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
					Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
				    
					Intent it= new Intent(getActivity(), AutoImageMainActivityXXH.class);
					//传递list对象
					//seri
					 //SerializableBCU myMap =new SerializableMap();
					List<BmobChatUser> bcu=CollectionUtils.map2list(users);
					final SerializableBCU myList =new SerializableBCU();
					myList.setUsr(bcu);
					
					
					Bundle bundle =new Bundle();
					bundle.putSerializable("userlist", myList);
					//bundle.putString("fatherName", "gameFrag");
					it.putExtras(bundle);
					//it.putExtra("fatherName", "gameFrag");
					
					if (position==0) {
						
						Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
						t.setToNow(); // 取得系统时间。
						int year = t.year;
						int month = t.month;
						int date = t.monthDay;
						int hour = t.hour; // 0-23
						int minute = t.minute;
						int second = t.second;
						String str = ""+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
						String newimg[];
						newimg = new String[1];
						newimg[0] = str;
						File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
						  if (!destDir.exists()) {
						   destDir.mkdirs();
						  }

						com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
						
						
						Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.icon1);
						
						saveMyBitmapxxh(str,img);
						
						startAnimActivity(it);
					}	
			}
		});
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

package com.bmob.im.demo.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.util.CollectionUtils;
import com.game.Forward;
import com.game.Game;
import com.game.operator.GameManager;
import com.game.xxh.AutoImageMainActivityXXH;
import com.userim.util.SerializableBCU;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author HenryTam
 *游戏列表Adapter base
 *游戏制作人为0
 *
 */
public class GamesGridAdapterBase3 extends BaseAdapter {

	private Context mContext;
	private Integer mThumbID = R.drawable.btn_game_grid_selector;

	protected List<Game> gamelist;
	

	protected int length=0;
	public GamesGridAdapterBase3(Context mContext) {
		super();
		this.mContext = mContext;
		//查询所有官方游戏的数据，返回表
		
	}

	public List<Game> getGamelist() {
		return gamelist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//View view = 
		
		final int vCurrentIndex=arg0;
		final ViewHolder holder;
		if (convertView==null) {
			convertView=View.inflate(mContext, R.layout.item_game_grid, null);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView
					.findViewById(R.id.ImgGrid);
			holder.mImageView.setImageResource(mThumbID);
			
			holder.mImageView.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("SdCardPath") @Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//存储当前的游戏game类
					GameManager.getInstance(mContext).setCurrentGame(gamelist.get(vCurrentIndex));
					//获取当前的游戏game类
					Game tempGame=GameManager.getInstance(mContext).getCurrentGame();
					
					String gameImagePath = tempGame.getPreference();//得到定制的图片的名字
					String name2=tempGame.getGameId();
					toast("xxh"+gameImagePath);
					
					Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
				    
					Intent it= new Intent(mContext, AutoImageMainActivityXXH.class);
					//传递list对象
					
					 //SerializableBCU myMap =new SerializableMap();
					List<BmobChatUser> bcu=CollectionUtils.map2list(users);
					final SerializableBCU myList =new SerializableBCU();
					myList.setUsr(bcu);
					
					
					Bundle bundle =new Bundle();
					bundle.putSerializable("userlist", myList);
					//bundle.putString("fatherName", "gameFrag");
					it.putExtras(bundle);
					//it.putExtra("fatherName", "gameFrag");	

					//LoadImageActivity img = new LoadImageActivity(gameImagePath);
					
					String imgName[];
					imgName = new String[6];
					int num = 0;

					imgName[0] = Integer.toString(R.id.iv1);
					imgName[1] = Integer.toString(R.id.iv2);
					imgName[2] = Integer.toString(R.id.iv3);
					imgName[3] = Integer.toString(R.id.iv4);
					imgName[4] = Integer.toString(R.id.iv5);
					imgName[5] = Integer.toString(R.id.iv6);
					
					for(int i=0;i<6;i++)
					{
						if(imgName[i].equals(gameImagePath))//相等为true
						{
							num = i+1;
							break;
						}
					}
					int icon = 0;
					if(num == 1)
					{
					    icon = R.drawable.icon1;
					}
					if(num == 2)
					{
					    icon = R.drawable.icon2;
					}
					if(num == 3)
					{
					    icon = R.drawable.icon3;
					}
					if(num == 4)
					{
					    icon = R.drawable.icon4;
					}
					if(num == 5)
					{
					    icon = R.drawable.icon5;
					}
					if(num == 6)
					{
					    icon = R.drawable.icon6;
					}
					
					String PnameImg = imgName[num-1];
					
					String newimg[];
					newimg = new String[1];
					newimg[0] = PnameImg;//str
					File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
					  if (!destDir.exists()) {
					   destDir.mkdirs();
					  }

					com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
					Bitmap img = BitmapFactory.decodeResource(mContext.getResources(), icon);
					
					//saveMyBitmapxxh(nameImg,img);
					//saveMyBitmapxxh(PnameImg,img);//用于定制进读取
					saveMyBitmapxxh("offical",img);//用于定制进读取

					//Bitmap img = BitmapFactory.decodeFile("/mnt/sdcard/gameimage/2131427498.jpg");
					//saveMyBitmapxxh("offical",img);//用于定制进读取
					
					mContext.startActivity(it);
					
				}
				
				
			});
			
		

		}

		return convertView;

	}
	public final class ViewHolder{
		private ImageView mImageView;
	}

	public void toast(String t){
		Toast.makeText(mContext, t, 200).show();
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

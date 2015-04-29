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
public class GamesGridAdapterBase extends BaseAdapter {

	protected Context mContext;
	protected Integer[] mThumbID ={R.drawable.btn_game1_grid_selector,R.drawable.btn_game2_grid_selector,R.drawable.btn_game3_grid_selector};

	protected List<Game> gamelist;
	

	protected int length=0;
	public GamesGridAdapterBase(Context mContext) {
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
	public final class ViewHolder{
		public ImageView mImageView;
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

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}	
}

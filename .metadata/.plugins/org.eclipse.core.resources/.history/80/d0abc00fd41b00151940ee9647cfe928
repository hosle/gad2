package com.binfen.im.gamead.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;

import com.binfen.im.gamead.CustomApplcation;
import com.binfen.im.gamead.util.CollectionUtils;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.binfen.im.gamead.R;
import com.game.Game;
import com.game.h5.H5GameMainActivity;
import com.game.operator.GameManager;
import com.game.xxh.AutoImageMainActivityXXH;
import com.userim.util.SerializableBCU;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
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
	protected Integer[] mThumbID ={R.drawable.btn_game1_grid_selector,R.drawable.btn_game2_grid_selector,R.drawable.btn_game3_grid_selector,R.drawable.btn_game4_grid_selector};

	protected List<Game> gamelist;
	protected Game currentGame;

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
	 ProgressDialog dialog =null;
     
	//test1
		/**
		 * @Description: 文件下载
		 * @param  
		 * @return void
		 * @throws
		 */
		public void download(String downloadName,final Intent it){
			
			currentGame=GameManager.getInstance(mContext).getCurrentGame();
			if(downloadName.equals("")){
				showLog("请指定下载文件名");
				return;
			}
			dialog = new ProgressDialog(mContext);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
			dialog.setTitle("下载中...");
			dialog.setIndeterminate(false);               
			dialog.setCancelable(true);       
			dialog.setCanceledOnTouchOutside(false);  
			dialog.show();
			BmobProFile.getInstance(mContext).download(downloadName, new DownloadListener() {

				@Override
				public void onSuccess(String fullPath) {
					// TODO Auto-generated method stub
					showLog("MainActivity -download-->onSuccess :"+fullPath);
					dialog.dismiss();
					//showToast("下载成功："+fullPath);
					Bitmap img = BitmapFactory.decodeFile(fullPath);		
					saveMyBitmapxxh("offical",img);
					
					String newimg[];
					newimg = new String[1];
					String gameNandu = currentGame.getSource();//得到游戏的难度
					File destDirNanDu = new File("/mnt/sdcard/gameimage/gamenandu.txt");
					  if (!destDirNanDu.exists()) {
						  destDirNanDu.mkdirs();
					}
					  
					newimg[0] = gameNandu;
					//com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
					com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
					
					mContext.startActivity(it);
				}

				@Override
				public void onProgress(String localPath, int percent) {
					// TODO Auto-generated method stub
					showLog("MainActivity -download-->onProgress :"+percent);
					dialog.setProgress(percent);
				}

				@Override
				public void onError(int statuscode, String errormsg) {
					// TODO Auto-generated method stub
					showLog("MainActivity -download-->onError :"+statuscode +"--"+errormsg);
					dialog.dismiss();
					showToast("下载出错："+errormsg);
				}
			});
		}

		
		Toast mToast;

		public void showToast(String text) {
			if (!TextUtils.isEmpty(text)) {
				if (mToast == null) {
					mToast = Toast.makeText(mContext.getApplicationContext(), text,
							Toast.LENGTH_SHORT);
				} else {
					mToast.setText(text);
				}
				mToast.show();
			}
		}
		
		public void showToast(int resId) {
			if (mToast == null) {
				mToast = Toast.makeText(mContext.getApplicationContext(), resId,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(resId);
			}
			mToast.show();
		}
		
		public static void showLog(String msg) {
			Log.i("BmobPro", msg);
		}
		
		protected class H5OnClickListener implements OnClickListener{

			private int vCurrentIndex;
			public H5OnClickListener(int v){
				vCurrentIndex=v;
			}
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//存储当前的游戏game类
				GameManager.getInstance(mContext).setCurrentGame(gamelist.get(vCurrentIndex));
				//获取当前的游戏game类
				Game tempGame=GameManager.getInstance(mContext).getCurrentGame();
				
				//String gameImagePath = tempGame.getPreference();//得到定制的图片的名字
				String name2=tempGame.getPreference();
				//toast(name2);
				
				Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
			    
				Intent it= new Intent(mContext, H5GameMainActivity.class);
				//传递list对象
				
				
				List<BmobChatUser> bcu=CollectionUtils.map2list(users);
				final SerializableBCU myList =new SerializableBCU();
				myList.setUsr(bcu);
				
				
				Bundle bundle =new Bundle();
				bundle.putSerializable("userlist", myList);
				//bundle.putString("fatherName", "gameFrag");
				it.putExtras(bundle);
					

				mContext.startActivity(it);
			}
			
		}
}

package com.binfen.im.gamead.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.binfen.im.gamead.adapter.GamesListAdapterBase.H5OnClickListener;
import com.binfen.im.gamead.util.CollectionUtils;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.binfen.im.gamead.CustomApplcation;
import com.binfen.im.gamead.R;
import com.game.Forward;
import com.game.Game;
import com.game.operator.GameManager;
import com.game.xxh.PintuMainActivity_0;
import com.userim.User;
import com.userim.util.SerializableBCU;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author HenryTam
 *我收到的游戏列表adapter
 *
 */
public class GamesListAdapter3 extends GamesListAdapterBase {

	//private Context mContext;
	private List<Game> tempGame=new ArrayList<Game>();
	
	public GamesListAdapter3(Context mContext) {
		super(mContext);
		//this.mContext = mContext;
		String myNameString=User.getCurrentUser(mContext).getUsername();
		//查询所有我收到的游戏数据，返回表
		BmobQuery<Forward> query=new BmobQuery<Forward>();
		
		query.addWhereEqualTo("receiverName", myNameString);
		query.include("gameforward");
		query.findObjects(mContext, new FindListener<Forward>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("查询失败:"+arg1);
			}

			@Override
			public void onSuccess(List<Forward> arg0) {
				// TODO Auto-generated method stub
				length=arg0.size();
				toast("查询成功：共"+arg0.size()+"条数据。");
				for(int i=0;i<length;i++){
					tempGame.add(arg0.get(i).getGameforward());
				}
				
				gamelist=tempGame;
				
				//toast("第0个收到的游戏preference："+gamelist.get(0).getPreference());
				
				notifyDataSetChanged();
			}
		});		
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final int vCurrentIndex=arg0;
		OnClickListener myOnClickListener=null;
		final ViewHolder holder;
		if (convertView==null) {
			convertView=View.inflate(mContext, R.layout.item_game_list, null);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView
					.findViewById(R.id.ImgGrid);
			
			//游戏分类
			final String tempSource=gamelist.get(vCurrentIndex).getSource();
			switch (tempSource) {
			case "innerPintu3":
				holder.mImageView.setImageResource(mThumbID[0]);
				myOnClickListener=new myOnClickListener3(vCurrentIndex);
				break;
			case "innerPintu4":
				holder.mImageView.setImageResource(mThumbID[1]);
				myOnClickListener=new myOnClickListener3(vCurrentIndex);
				break;
			case "innerPintu5":
				holder.mImageView.setImageResource(mThumbID[2]);
				myOnClickListener=new myOnClickListener3(vCurrentIndex);
				break;
			case "h5":
				holder.mImageView.setImageResource(mThumbID[3]);
				myOnClickListener=new H5OnClickListener(vCurrentIndex);
				break;
			}
			
				holder.mImageView.setFocusable(false);
				holder.mImageView.setFocusableInTouchMode(false);
				//holder.mImageView.setOnClickListener(myOnClickListener);
				convertView.setOnClickListener(myOnClickListener);
			}

		return convertView;
	}
	
	private class myOnClickListener3 implements OnClickListener{

		private int vCurrentIndex;
		public myOnClickListener3(int vCurrent) {
			// TODO Auto-generated constructor stub
			vCurrentIndex=vCurrent;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//存储当前的游戏game类
			GameManager.getInstance(mContext).setCurrentGame(gamelist.get(vCurrentIndex));
			//获取当前的游戏game类
			currentGame=GameManager.getInstance(mContext).getCurrentGame();
			
			
			String gameImagePath = currentGame.getPreference();//得到定制的图片的名字
			String name2=currentGame.getGameId();
			//toast(gameImagePath);
			
			Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
		    
			Intent it= new Intent(mContext, PintuMainActivity_0.class);
			//传递list对象
			
			 //SerializableBCU myMap =new SerializableMap();
			List<BmobChatUser> bcu=CollectionUtils.map2list(users);
			final SerializableBCU myList =new SerializableBCU();
			myList.setUsr(bcu);
			
			
			Bundle bundle =new Bundle();
			bundle.putSerializable("userlist", myList);
			//bundle.putString("fatherName", "gameFrag");
			it.putExtras(bundle);
			download(gameImagePath,it);
			//it.putExtra("fatherName", "gameFrag");	
			/*Bitmap img = BitmapFactory.decodeFile("/mnt/sdcard/gameimage/"+gameImagePath+".jpg");		
			saveMyBitmapxxh("offical",img);//用于定制进读取*/
			
			/*String newimg[];
			newimg = new String[1];
			String gameNandu = tempGame.getSource();//得到游戏的难度
			File destDirNanDu = new File("/mnt/sdcard/gameimage/gamenandu.txt");
			  if (!destDirNanDu.exists()) {
				  destDirNanDu.mkdirs();
			}
			  
			newimg[0] = gameNandu;
			//com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
			com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
			
			mContext.startActivity(it);*/
		}
		
	}
	

}

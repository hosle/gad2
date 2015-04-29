package com.bmob.im.demo.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.util.CollectionUtils;
import com.game.Game;
import com.game.operator.GameManager;
import com.game.xxh.AutoImageMainActivityXXH;
import com.userim.User;
import com.userim.util.SerializableBCU;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @author HenryTam
 *自定义游戏列表adapter
 *查询所有我定制的数据，返回表
 */
public class GamesGridAdapter2 extends GamesGridAdapterBase {

	//private Context mContext;
	
	public GamesGridAdapter2(Context mContext) {
		super(mContext);
		//this.mContext = mContext;
		//查询所有我定制的数据，返回表
		BmobQuery<Game> query=new BmobQuery<Game>();
		query.addWhereEqualTo("gameOwnerUser", User.getCurrentUser(mContext));
		query.findObjects(mContext, new FindListener<Game>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("查询失败:"+arg1);
			}

			@Override
			public void onSuccess(List<Game> arg0) {
				// TODO Auto-generated method stub
				length=arg0.size();
				toast("查询成功：共"+arg0.size()+"条数据。");
				gamelist=arg0;
				notifyDataSetChanged();
			}
		});		
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final int vCurrentIndex=arg0;
		final ViewHolder holder;
		if (convertView==null) {
			convertView=View.inflate(mContext, R.layout.item_game_grid, null);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView
					.findViewById(R.id.ImgGrid);
			//游戏分类
			final String tempSource=gamelist.get(vCurrentIndex).getSource();
			switch (tempSource) {
			case "innerPintu3":
				holder.mImageView.setImageResource(mThumbID[0]);
				break;
			case "innerPintu4":
				holder.mImageView.setImageResource(mThumbID[1]);
				break;
			case "innerPintu5":
				holder.mImageView.setImageResource(mThumbID[2]);
				break;
			}
			
			
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
					toast(name2);
					
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
					Bitmap img = BitmapFactory.decodeFile("/mnt/sdcard/gameimage/"+gameImagePath+".jpg");		
					saveMyBitmapxxh("offical",img);//用于定制进读取
					
					String newimg[];
					newimg = new String[1];
					String gameNandu = tempGame.getSource();//得到游戏的难度
					File destDirNanDu = new File("/mnt/sdcard/gameimage/gamenandu.txt");
					  if (!destDirNanDu.exists()) {
						  destDirNanDu.mkdirs();
					}
					  
					newimg[0] = gameNandu;
					//com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
					com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
					
					mContext.startActivity(it);
					
				}
				
				
			});
			
		

		}

		return convertView;
	}


}

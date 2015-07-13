package com.binfen.im.gamead.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.binfen.im.gamead.adapter.GamesListAdapterBase.ViewHolder;
import com.binfen.im.gamead.util.CollectionUtils;
import com.binfen.im.gamead.CustomApplcation;
import com.binfen.im.gamead.R;
import com.game.Game;
import com.game.operator.GameManager;
import com.game.xxh.PintuMainActivity_0;
import com.userim.util.SerializableBCU;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @author HenryTam
 *官方游戏列表Adapter
 *游戏制作人为0
 *
 */
public class GamesListAdapter1 extends GamesListAdapterBase {

	

	public GamesListAdapter1(Context mContext) {
		super(mContext);
		
		//查询所有官方游戏的数据，返回表
		BmobQuery<Game> query=new BmobQuery<Game>();
		query.addWhereEqualTo("gameOwnerUser", "0");
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
		OnClickListener myOnClickListener=null;
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
				myOnClickListener=new myOnClickListener1(vCurrentIndex);
				break;
			case "innerPintu4":
				holder.mImageView.setImageResource(mThumbID[1]);
				myOnClickListener=new myOnClickListener1(vCurrentIndex);
				break;
			case "innerPintu5":
				holder.mImageView.setImageResource(mThumbID[2]);
				myOnClickListener=new myOnClickListener1(vCurrentIndex);
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
	
	
	private class myOnClickListener1 implements OnClickListener{

		private int vCurrentIndex;
		
		public myOnClickListener1(int v) {
			// TODO Auto-generated constructor stub
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
			String name2=tempGame.getGameId();
			//toast(name2);
			
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
			//it.putExtra("fatherName", "gameFrag");
			
				/*Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
				t.setToNow(); // 取得系统时间。
				int year = t.year;
				int month = t.month;
				int date = t.monthDay;
				int hour = t.hour; // 0-23
				int minute = t.minute;
				int second = t.second;
				String str = "_"+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;*/
				
				
				int imgName = R.id.iv1;
				//String nameImg = Integer.toString(imgName)+str;
				String PnameImg = Integer.toString(imgName);
				
				String newimg[];
				newimg = new String[1];
				newimg[0] = PnameImg;//str
				
				
				File destDir = new File("/mnt/sdcard/gameimage/newimage.txt");
				  if (!destDir.exists()) {
				   destDir.mkdirs();
				  }

				//com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
				com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
				
				String gameNandu = tempGame.getSource();//得到游戏的难度
				File destDirNanDu = new File("/mnt/sdcard/gameimage/gamenandu.txt");
				  if (!destDirNanDu.exists()) {
					  destDirNanDu.mkdirs();
				}
				  
				newimg[0] = gameNandu;
				//com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
				com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/gamenandu.txt",newimg);

			
			    Bitmap img = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon1);		
				//saveMyBitmapxxh(str,img);
				saveMyBitmapxxh(PnameImg,img);
				saveMyBitmapxxh("offical",img);
				//把游戏的难度进行保存;

				mContext.startActivity(it);
		}
		
	}


}

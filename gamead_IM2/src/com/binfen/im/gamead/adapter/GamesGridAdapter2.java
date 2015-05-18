package com.binfen.im.gamead.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.binfen.im.gamead.CustomApplcation;
import com.binfen.im.gamead.adapter.GamesGridAdapterBase.H5OnClickListener;
import com.binfen.im.gamead.util.CollectionUtils;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.binfen.im.gamead.R;
import com.game.Game;
import com.game.operator.GameManager;
import com.game.xxh.AutoImageMainActivityXXH;
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
 *�Զ�����Ϸ�б�adapter
 *��ѯ�����Ҷ��Ƶ����ݣ����ر�
 */
public class GamesGridAdapter2 extends GamesGridAdapterBase {

	//private Context mContext;
	
	public GamesGridAdapter2(Context mContext) {
		super(mContext);
		//this.mContext = mContext;
		//��ѯ�����Ҷ��Ƶ����ݣ����ر�
		BmobQuery<Game> query=new BmobQuery<Game>();
		query.addWhereEqualTo("gameOwnerUser", User.getCurrentUser(mContext));
		query.findObjects(mContext, new FindListener<Game>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("��ѯʧ��:"+arg1);
			}

			@Override
			public void onSuccess(List<Game> arg0) {
				// TODO Auto-generated method stub
				length=arg0.size();
				toast("��ѯ�ɹ�����"+arg0.size()+"�����ݡ�");
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
			convertView=View.inflate(mContext, R.layout.item_game_grid, null);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView
					.findViewById(R.id.ImgGrid);
			//��Ϸ����
			final String tempSource=gamelist.get(vCurrentIndex).getSource();
			switch (tempSource) {
			case "innerPintu3":
				holder.mImageView.setImageResource(mThumbID[0]);
				myOnClickListener=new myOnClickListener2(vCurrentIndex);
				break;
			case "innerPintu4":
				holder.mImageView.setImageResource(mThumbID[1]);
				myOnClickListener=new myOnClickListener2(vCurrentIndex);
				break;
			case "innerPintu5":
				holder.mImageView.setImageResource(mThumbID[2]);
				myOnClickListener=new myOnClickListener2(vCurrentIndex);
				break;
			case "h5":
				holder.mImageView.setImageResource(mThumbID[3]);
				myOnClickListener=new H5OnClickListener(vCurrentIndex);
				break;
			}
			
			
			holder.mImageView.setOnClickListener(myOnClickListener);
			
		

		}

		return convertView;
	}
	
	private class myOnClickListener2 implements OnClickListener{

		private int vCurrentIndex;
		public myOnClickListener2(int vC) {
			// TODO Auto-generated constructor stub
			vCurrentIndex=vC;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//�洢��ǰ����Ϸgame��
			GameManager.getInstance(mContext).setCurrentGame(gamelist.get(vCurrentIndex));
			//��ȡ��ǰ����Ϸgame��
			Game tempGame=GameManager.getInstance(mContext).getCurrentGame();
			
			String gameImagePath = tempGame.getPreference();//�õ����Ƶ�ͼƬ������
			String name2=tempGame.getGameId();
			//toast(name2);
			
			Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
		    
			Intent it= new Intent(mContext, AutoImageMainActivityXXH.class);
			//����list����
			
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
			saveMyBitmapxxh("offical",img);//���ڶ��ƽ���ȡ*/
			
			/*String newimg[];
			newimg = new String[1];
			String gameNandu = tempGame.getSource();//�õ���Ϸ���Ѷ�
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
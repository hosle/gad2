package com.bmob.im.demo.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.util.CollectionUtils;
import com.game.Forward;
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
 *���յ�����Ϸ�б�adapter
 *
 */
public class GamesGridAdapter3 extends GamesGridAdapterBase {

	//private Context mContext;
	private List<Game> tempGame=new ArrayList<Game>();
	
	public GamesGridAdapter3(Context mContext) {
		super(mContext);
		//this.mContext = mContext;
		String myNameString=User.getCurrentUser(mContext).getUsername();
		//��ѯ�������յ�����Ϸ���ݣ����ر�
		BmobQuery<Forward> query=new BmobQuery<Forward>();
		
		query.addWhereEqualTo("receiverName", myNameString);
		query.include("gameforward");
		query.findObjects(mContext, new FindListener<Forward>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("��ѯʧ��:"+arg1);
			}

			@Override
			public void onSuccess(List<Forward> arg0) {
				// TODO Auto-generated method stub
				length=arg0.size();
				toast("��ѯ�ɹ�����"+arg0.size()+"�����ݡ�");
				for(int i=0;i<length;i++){
					tempGame.add(arg0.get(i).getGameforward());
				}
				
				gamelist=tempGame;
				
				//toast("��0���յ�����Ϸpreference��"+gamelist.get(0).getPreference());
				
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
			//��Ϸ����
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
					//�洢��ǰ����Ϸgame��
					GameManager.getInstance(mContext).setCurrentGame(gamelist.get(vCurrentIndex));
					//��ȡ��ǰ����Ϸgame��
					Game tempGame=GameManager.getInstance(mContext).getCurrentGame();
					
					String gameImagePath = tempGame.getPreference();//�õ����Ƶ�ͼƬ������
					String name2=tempGame.getGameId();
					toast("xxh"+gameImagePath);
					
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
						if(imgName[i].equals(gameImagePath))//���Ϊtrue
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
					//saveMyBitmapxxh(PnameImg,img);//���ڶ��ƽ���ȡ
					saveMyBitmapxxh("offical",img);//���ڶ��ƽ���ȡ

					//Bitmap img = BitmapFactory.decodeFile("/mnt/sdcard/gameimage/2131427498.jpg");
					//saveMyBitmapxxh("offical",img);//���ڶ��ƽ���ȡ
					String gameNandu = tempGame.getSource();//�õ���Ϸ���Ѷ�
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

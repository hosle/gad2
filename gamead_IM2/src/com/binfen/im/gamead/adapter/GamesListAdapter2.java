package com.binfen.im.gamead.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import com.binfen.im.gamead.adapter.GamesListAdapterBase.H5OnClickListener;
import com.binfen.im.gamead.util.CollectionUtils;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.game.Forward;
import com.game.Game;
import com.game.operator.GameManager;
import com.game.xxh.PintuMainActivity_0;
import com.userim.User;
import com.userim.util.SerializableBCU;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author HenryTam
 *�Զ�����Ϸ�б�adapter
 *��ѯ�����Ҷ��Ƶ����ݣ����ر�
 */
public class GamesListAdapter2 extends GamesListAdapterBase {

	//private Context mContext;
	
	public GamesListAdapter2(Context mContext) {
		super(mContext);
		//this.mContext = mContext;
		//��ѯ�����Ҷ��Ƶ����ݣ����ر�
		loadGamelist();
		
	}
	private void loadGamelist() {
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
			convertView=View.inflate(mContext, R.layout.item_game_list, null);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView
					.findViewById(R.id.ImgGrid);
			//��Ϸ����
			final String tempSource=gamelist.get(vCurrentIndex).getSource();
			switch (tempSource) {
			case "innerPintu3":
				toast("��ǰ�Ѷ�"+tempSource+"tempSource");
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
			
			holder.mImageView.setFocusable(false);
			holder.mImageView.setFocusableInTouchMode(false);
			//holder.mImageView.setOnClickListener(myOnClickListener);
			convertView.setOnClickListener(myOnClickListener);
		
			//����ɾ����ǰ����Ϸ
			convertView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					Builder mDialog = new AlertDialog.Builder(mContext);
					// final int score=Config.time;
					mDialog.setTitle("�˳�");
					mDialog.setMessage("ȷ��ɾ����");
					mDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub

							//ɾ����ǰ��Ϸ
							Game delGame=gamelist.get(vCurrentIndex);
							//��ɾ��ת����¼����ɾ��game����
							delGameForward(delGame);
							
							delGame.delete(mContext, new DeleteListener() {
								
								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									toast("ɾ���ɹ�");
									loadGamelist();
									
								}
								
								@Override
								public void onFailure(int code, String msg) {
									// TODO Auto-generated method stub
									toast("ɾ��ʧ��"+code+":"+msg);
								}
							});
							}

					});

					mDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							arg0.cancel();
							
						}
					});

					mDialog.show();
					return true;
				}
			});

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
		    
			Intent it= new Intent(mContext, PintuMainActivity_0.class);
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
	
	/**
	 * ɾ�����е���Ϸ��������
	 */
	private void delGameForward(Game delGame){
		BmobQuery<Forward> query=new BmobQuery<Forward>();
		query.addWhereEqualTo("gameforward", delGame);
		query.findObjects(mContext, new FindListener<Forward>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("��ѯʧ��:"+arg1);
			}

			@Override
			public void onSuccess(List<Forward> item) {
				// TODO Auto-generated method stub
				//length=arg0.size();
				//toast("��ѯ�ɹ�����"+arg0.size()+"�����ݡ�");
				for (int i = 0; i < item.size(); i++) {
					item.get(i).delete(mContext,new DeleteListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							toast("ɾ��ת����¼�ɹ�");
						}
						@Override
						public void onFailure(int code, String msg) {
							// TODO Auto-generated method stub
							toast("ɾ��ת����¼ʧ��"+code+":"+msg);
						}
					});
				}
				//notifyDataSetChanged();
			}
		});		
	}
	
}
package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.game.Forward;
import com.game.Game;
import com.userim.User;

import android.content.Context;

/**
 * @author HenryTam
 *���յ�����Ϸ�б�adapter
 *
 */
public class GamesGridAdapter3 extends GamesGridAdapterBase2 {

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


}

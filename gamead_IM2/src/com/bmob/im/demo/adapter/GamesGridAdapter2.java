package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.game.Game;
import com.userim.User;

import android.content.Context;

/**
 * @author HenryTam
 *�Զ�����Ϸ�б�adapter
 *��ѯ�����Ҷ��Ƶ����ݣ����ر�
 */
public class GamesGridAdapter2 extends GamesGridAdapterBase2 {

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


}

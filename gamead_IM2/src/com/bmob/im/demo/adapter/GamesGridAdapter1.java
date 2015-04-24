package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.game.Game;
import android.content.Context;

/**
 * @author HenryTam
 *�ٷ���Ϸ�б�Adapter
 *��Ϸ������Ϊ0
 *
 */
public class GamesGridAdapter1 extends GamesGridAdapterBase {

	

	public GamesGridAdapter1(Context mContext) {
		super(mContext);
		
		//��ѯ���йٷ���Ϸ�����ݣ����ر�
		BmobQuery<Game> query=new BmobQuery<Game>();
		query.addWhereEqualTo("gameOwnerUser", "0");
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

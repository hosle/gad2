package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.game.Game;
import com.userim.User;

import android.content.Context;

/**
 * @author HenryTam
 *自定义游戏列表adapter
 *查询所有我定制的数据，返回表
 */
public class GamesGridAdapter2 extends GamesGridAdapterBase2 {

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


}

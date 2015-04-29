package com.bmob.im.demo.adapter;

import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.ui.SelectGameToSendActivity;
import com.game.Game;
import com.game.operator.GameManager;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author HenryTam
 *官方游戏列表Adapter
 *游戏制作人为0
 *
 */
public class GamesSendAdapter1 extends GamesSendAdapterBase {

	
	
	public GamesSendAdapter1(SelectGameToSendActivity mContext) {
		super();
		this.mContext=mContext;
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
				gamelist=arg0;
				toast("1查询成功：共"+gamelist.size()+"条数据。");
				
				notifyDataSetChanged();
			}
		});		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	

	

}

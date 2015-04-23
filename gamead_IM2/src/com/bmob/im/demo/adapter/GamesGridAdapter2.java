package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.game.Game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @author HenryTam
 *自定义游戏列表adapter
 */
public class GamesGridAdapter2 extends BaseAdapter {

	private Context mContext;
	private Integer mThumbID = R.drawable.pintugame;

	private List<Game> gamelist;
	private int length=0;
	public GamesGridAdapter2(Context mContext) {
		super();
		this.mContext = mContext;
		//查询所有我定制的数据，返回表
		BmobQuery<Game> query=new BmobQuery<Game>();
		query.addWhereNotEqualTo("gameOwnerUser", "0");
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
	public int getCount() {
		// TODO Auto-generated method stub
		return length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = View.inflate(mContext, R.layout.item_game_grid, null);
		
		
		
		RelativeLayout mRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.gridItemGame);

		ImageView mImageView = (ImageView) mRelativeLayout
				.findViewById(R.id.ImgGrid);

		mImageView.setImageResource(mThumbID);

		return mRelativeLayout;

	}

	private void toast(String t){
		Toast.makeText(mContext, t, Toast.LENGTH_SHORT).show();
	}
}

package com.bmob.im.demo.adapter;

import java.util.List;

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

public class GamesSendAdapterBase extends BaseAdapter {

	protected SelectGameToSendActivity mContext;

	protected Integer[] mThumbID ={R.drawable.btn_game1_grid_selector,R.drawable.btn_game2_grid_selector,R.drawable.btn_game3_grid_selector,R.drawable.btn_game4_grid_selector};

	protected int length=0;
	protected List<Game> gamelist;
	protected String gameId;
	
	public GamesSendAdapterBase() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final int vCurrentIndex=arg0;
		final ViewHolder holder;
		if (convertView==null) {
			convertView=View.inflate(mContext, R.layout.item_game_grid, null);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView
					.findViewById(R.id.ImgGrid);
			
			//游戏分类
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
			case "h5":
				holder.mImageView.setImageResource(mThumbID[3]);
				break;
			}
			
			holder.mImageView.setOnClickListener(new OnClickListener() {
				
				 @Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//存储选中的游戏game类
					
					Game tempgame=gamelist.get(vCurrentIndex);
					GameManager.getInstance(mContext.getBaseContext()).setSelectGame(tempgame);
					
					gameId = tempgame.getGameId();//得到定制的图片的名字
					
					toast(gameId);
					gobacktoChatPage();

				}
				
				
			});
		}
		return convertView;
	}
	
	private void gobacktoChatPage() {
		Game temp=GameManager.getInstance(mContext).getSelectGame();
		if(temp!=null){
			Intent intent = new Intent();
			
			intent.putExtra("gameId", gameId);
			
			mContext.setResult(android.app.Activity.RESULT_OK, intent);
			mContext.finish();
		}else{
			toast("选择游戏失败!");
		}
	}
	public final class ViewHolder{
		private ImageView mImageView;
	}
	
	public void toast(String t){
		Toast.makeText(mContext, t, 200).show();
	}

}

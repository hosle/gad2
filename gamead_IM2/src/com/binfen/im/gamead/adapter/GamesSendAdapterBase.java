package com.binfen.im.gamead.adapter;

import java.util.List;

import com.binfen.im.gamead.ui.SelectGameToSendActivity;
import com.binfen.im.gamead.R;
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
	protected String gameMsg;
	
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
			convertView=View.inflate(mContext, R.layout.item_game_list, null);
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
			myOnClickListener mClickListener=new myOnClickListener(vCurrentIndex);
			convertView.setOnClickListener(mClickListener);
			//holder.mImageView.setOnClickListener(mClickListener);
		}
		return convertView;
	}
	
	private class myOnClickListener implements OnClickListener{

		private int mCurrentIndex;
		public  myOnClickListener(int vCurrentIndex) {
			// TODO Auto-generated constructor stub
			mCurrentIndex=vCurrentIndex;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//存储选中的游戏game类
			
			Game tempgame=gamelist.get(mCurrentIndex);
			GameManager.getInstance(mContext.getBaseContext()).setSelectGame(tempgame);
			
			//设置发送的命令
			gameMsg = "#g"+tempgame.getSource()+"#p"+tempgame.getPreference();//要发送的游戏编码，难度+图片
			
			//toast(gameId);
			gobacktoChatPage();
		}
		
	}
	private void gobacktoChatPage() {
		Game temp=GameManager.getInstance(mContext).getSelectGame();
		if(temp!=null){
			Intent intent = new Intent();
			
			intent.putExtra("gameId", gameMsg);
			
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

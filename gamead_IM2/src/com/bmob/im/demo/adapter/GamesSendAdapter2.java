package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.ui.SelectGameToSendActivity;
import com.game.Game;
import com.game.operator.GameManager;
import com.userim.User;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author HenryTam
 * ���촰���е�
 *�Զ�����Ϸ�б�adapter
 *��Ϸ������Ϊ0
 *
 */
public class GamesSendAdapter2 extends BaseAdapter {

	private SelectGameToSendActivity mContext;

	private Integer mThumbID = R.drawable.btn_game_grid_selector;
	protected int length=0;
	protected List<Game> gamelist;
	private String gameId;
	
	public GamesSendAdapter2(SelectGameToSendActivity mContext) {
		super();
		this.mContext=mContext;
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
				gamelist=arg0;
				toast("2��ѯ�ɹ�����"+gamelist.size()+"�����ݡ�");
				
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
			holder.mImageView.setImageResource(mThumbID);
			
			holder.mImageView.setOnClickListener(new OnClickListener() {
				
				 @Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//�洢ѡ�е���Ϸgame��
					
					Game tempgame=gamelist.get(vCurrentIndex);
					GameManager.getInstance(mContext.getBaseContext()).setSelectGame(tempgame);
					
					gameId = tempgame.getGameId();//�õ����Ƶ�ͼƬ������
					
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
			toast("ѡ����Ϸʧ��!");
		}
	}
	public final class ViewHolder{
		private ImageView mImageView;
	}
	
	public void toast(String t){
		Toast.makeText(mContext, t, 200).show();
	}

}

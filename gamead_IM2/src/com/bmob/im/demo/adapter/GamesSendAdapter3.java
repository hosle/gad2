package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.ui.SelectGameToSendActivity;
import com.game.Forward;
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
 *���յ�����Ϸ�б�adapter
 *
 *
 */
public class GamesSendAdapter3 extends BaseAdapter {

	private SelectGameToSendActivity mContext;

	private Integer mThumbID = R.drawable.btn_game_grid_selector;
	protected int length=0;
	protected List<Game> gamelist;
	private String gameId;
	private List<Game> tempGame=new ArrayList<Game>();
	
	public GamesSendAdapter3(SelectGameToSendActivity mContext) {
		super();
		this.mContext=mContext;
		String myNameString=User.getCurrentUser(mContext).getUsername();
		//��ѯ�������յ�����Ϸ���ݣ����ر�
		BmobQuery<Forward> query=new BmobQuery<Forward>();
		query.addWhereEqualTo("receiverName", myNameString);
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
				toast("3��ѯ�ɹ�����"+arg0.size()+"�����ݡ�");
				for(int i=0;i<length;i++){
					tempGame.add(arg0.get(i).getGameforward());
				}
				gamelist=tempGame;
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
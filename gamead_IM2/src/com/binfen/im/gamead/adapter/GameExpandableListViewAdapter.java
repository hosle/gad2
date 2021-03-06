package com.binfen.im.gamead.adapter;

import java.util.ArrayList;
import java.util.List;

import com.binfen.im.gamead.ui.SelectGameToSendActivity;
import com.binfen.im.gamead.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GameExpandableListViewAdapter extends BaseExpandableListAdapter {

	private SelectGameToSendActivity mContext;
	private List<String> group_list;
	//private List<Integer> itemList;
	private BaseAdapter[] listAdapters;
	
	
	public GameExpandableListViewAdapter(SelectGameToSendActivity mContext) {
		// TODO Auto-generated constructor stub
		this.mContext=mContext;
		group_list=new ArrayList<String>();
		group_list.add("�ٷ�");
		group_list.add("DIY");
		group_list.add("�յ�/�ղ�");
		
		GamesSendAdapter1 gamesSendAdapter1=new GamesSendAdapter1(mContext);
		GamesSendAdapter2 gamesSendAdapter2=new GamesSendAdapter2(mContext);
		GamesSendAdapter3 gamesSendAdapter3=new GamesSendAdapter3(mContext);
		listAdapters=new BaseAdapter[]{gamesSendAdapter1,gamesSendAdapter2,gamesSendAdapter3};
		
		
		  
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return listAdapters[arg0];
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView,
			ViewGroup arg4) {
		// TODO Auto-generated method stub
		ItemHolder itemHolder=null;
		if (convertView == null) {
		    convertView = (View) LayoutInflater.from(mContext).inflate(
		      R.layout.item_select_game_send, null);
		    itemHolder = new ItemHolder();
		    //itemHolder.mgridView = (GridView) convertView.findViewById(R.id.gridgame_expanded);
		    itemHolder.mListView=(ListView)convertView.findViewById(R.id.listgame_expanded);
		    convertView.setTag(itemHolder);
		   } else {
		    itemHolder = (ItemHolder) convertView.getTag();
		   }
			
		   //itemHolder.mgridView.setAdapter(gridAdapters[groupPosition]);
		   itemHolder.mListView.setAdapter(listAdapters[groupPosition]);
		   //itemHolder.mgridView.setv
		   return convertView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return group_list.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group_list.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean isExpanded, View convertView, ViewGroup arg3) {
		// TODO Auto-generated method stub
		GroupHolder groupHolder=null;
		if (convertView==null) {
			convertView = (View) LayoutInflater.from(mContext).inflate(
				      R.layout.group_select_game_send, null);
			groupHolder=new GroupHolder();
			groupHolder.txt=(TextView)convertView.findViewById(R.id.expandlist_group_txt);
			groupHolder.img=(ImageView)convertView.findViewById(R.id.expandlist_group_img);
			convertView.setTag(groupHolder);
		}else {
			groupHolder=(GroupHolder)convertView.getTag();
		}
		groupHolder.txt.setText(group_list.get(arg0));
		//groupHolder.img.setImageResource(R.drawable.btn_expandlist_group_selector);
		if (!isExpanded) {
			groupHolder.img.setImageResource(R.drawable.xxh_game_ar);
		}else {
			groupHolder.img.setImageResource(R.drawable.xxh_game_ad);
		}
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class GroupHolder{
		private TextView txt;
		private ImageView img;
	}
	
	private class ItemHolder{
		//private GridView mgridView;
		private ListView mListView;
	}
	
	

}

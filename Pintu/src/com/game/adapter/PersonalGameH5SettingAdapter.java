package com.game.adapter;



import com.game.pintu.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalGameH5SettingAdapter extends BaseAdapter {

	private Context mContext;
	private ViewHolder holder=null;
	

	private String items[]={"说明","游戏地址"};
	
	
	public PersonalGameH5SettingAdapter(Context vContext){
		mContext=vContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		if (convertView==null) {
			convertView=View.inflate(mContext, R.layout.item_personal_list,null);
			holder=new ViewHolder();
			holder.mEditText=(EditText) convertView.findViewById(R.id.edt_personallist);
			holder.mTextView=(TextView)convertView.findViewById(R.id.txt_personallist);
			switch (position) {
			case 0:
				holder.mTextView.setText(items[position]);
				holder.mEditText.setHint("请填写游戏说明");
				break;
			case 1:
				holder.mTextView.setText(items[position]);
				
				holder.mEditText.setHint("请填写游戏地址");
				
				break;

			default:
				break;
			}
		}
		
		
		return convertView;
	}
	
	private final class ViewHolder{
		private TextView mTextView;
		private EditText mEditText;
	}
	

}

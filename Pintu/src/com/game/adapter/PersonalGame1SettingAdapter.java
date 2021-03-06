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

public class PersonalGame1SettingAdapter extends BaseAdapter {

	private Context mContext;
	private ViewHolder holder=null;
	

	private String items[]={"˵��","����ͼƬ"};
	
	
	public PersonalGame1SettingAdapter(Context vContext){
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
				holder.mEditText.setHint("����д��Ϸ˵��");
				break;
			case 1:
				holder.mTextView.setText(items[position]);
				
				holder.mEditText.setHint("���ѡ��ͼƬ");
				holder.mEditText.setInputType(InputType.TYPE_NULL);				
				holder.mEditText.setOnTouchListener(new picOnTouchListener());
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
	
	private class picOnTouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			if (arg1.getAction()==MotionEvent.ACTION_DOWN) {
				Intent intent;
				if (Build.VERSION.SDK_INT < 19) {
					intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
				} else {
					intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				}
				
				Activity activity=(Activity)mContext;
				activity.startActivityForResult(intent, 0);
			}
			return true;
		}
		
	}
	
	

}

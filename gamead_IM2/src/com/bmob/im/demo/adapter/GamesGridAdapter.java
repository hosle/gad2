package com.bmob.im.demo.adapter;

import com.bmob.im.demo.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GamesGridAdapter extends BaseAdapter {

	private Context mContext;
	private Integer[] mThumbIDs = { R.drawable.pintugame, R.drawable.pintugame,
			R.drawable.pintugame, R.drawable.pintugame };

	public GamesGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIDs.length;
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

		mImageView.setImageResource(mThumbIDs[arg0]);

		return mRelativeLayout;

	}

}

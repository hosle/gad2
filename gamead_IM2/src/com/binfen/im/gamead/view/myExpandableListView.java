package com.binfen.im.gamead.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ExpandableListView;

public class myExpandableListView extends ExpandableListView {

	public myExpandableListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public myExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public myExpandableListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action=ev.getAction();
		if (action==MotionEvent.ACTION_MOVE) {
			return false;
		}else
			return super.onInterceptTouchEvent(ev);
	}

}

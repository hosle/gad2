package com.bmob.im.demo.ui;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.GameExpandableListViewAdapter;
import com.bmob.im.demo.view.myExpandableListView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class SelectGameToSendActivity extends Activity {

	private myExpandableListView mExpandableListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectgametosend);
		
		mExpandableListView=(myExpandableListView)findViewById(R.id.expendlist);
		mExpandableListView.setAdapter(new GameExpandableListViewAdapter(this));
		
		//只能打开其中一组，同时关闭其他组
		mExpandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPositon) {
				int count=mExpandableListView.getExpandableListAdapter().getGroupCount();
				// TODO Auto-generated method stub
				for (int i = 0; i < count; i++) {
					if (groupPositon!=i) {
						mExpandableListView.collapseGroup(i);
					}
					
				}
			}
		});
		mExpandableListView.expandGroup(0);
	}
	
	
}

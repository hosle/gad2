package com.bmob.im.demo.ui.fragment;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import cn.bmob.im.bean.BmobChatUser;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.GamesGridAdapter;
import com.bmob.im.demo.ui.FragmentBase;
import com.bmob.im.demo.util.CollectionUtils;
import com.game.config.Config;
import com.game.pintu.MainActivity;
import com.userim.util.SerializableBCU;

public class GamesFragment extends FragmentBase {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_games, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	private void initView(){
		GridView mGridView=(GridView)findViewById(R.id.gridgame);
		mGridView.setAdapter(new GamesGridAdapter(getActivity()));
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
					Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
				    
					Intent it= new Intent(getActivity(), MainActivity.class);
					//´«µÝlist¶ÔÏó
					//seri
					 //SerializableBCU myMap =new SerializableMap();
					List<BmobChatUser> bcu=CollectionUtils.map2list(users);
					final SerializableBCU myList =new SerializableBCU();
					myList.setUsr(bcu);
					
					
					Bundle bundle =new Bundle();
					bundle.putSerializable("userlist", myList);
					it.putExtras(bundle);
					//it.putExtra("userlist", (Serializable)bcu);
					
					
					/*if (bcu==null) {
						Log.i("test", "bcu=null");
					}
					else {
						Log.i("test", "bcu sth");
					}*/
					//startActivity(it);
					if (position==0) {
						startAnimActivity(it);
					}
					
					
				
			}
		});
	}
	
	
}

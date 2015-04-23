package com.bmob.im.demo.ui.fragment;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import cn.bmob.im.bean.BmobChatUser;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.GamesGridAdapter2;
import com.bmob.im.demo.ui.FragmentBase;
import com.bmob.im.demo.util.CollectionUtils;
import com.bmob.im.demo.xxh.OfficalActivity;
import com.game.config.Config;
import com.game.pintu.MainActivity;
import com.userim.util.SerializableBCU;

public class GamesFragment extends FragmentBase {

	private Fragment mContent;
	private ShowFragmentXXH1 gamesFragment1;
	private ShowFragmentXXH2 gamesFragment2;
	private ShowFragmentXXH3 gamesFragment3;

	
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
		
		gamesFragment1= new ShowFragmentXXH1();
		gamesFragment2= new ShowFragmentXXH2();
		gamesFragment3= new ShowFragmentXXH3();
			
		
		

		getFragmentManager().beginTransaction().add(R.id.gamefragment_container, gamesFragment1).add(R.id.gamefragment_container, gamesFragment2).
		add(R.id.gamefragment_container, gamesFragment3).hide(gamesFragment2).show(gamesFragment1).hide(gamesFragment3).commit();
		
		
		
	}
	
	
	
}

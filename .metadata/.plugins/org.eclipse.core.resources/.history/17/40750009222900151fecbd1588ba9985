package com.binfen.im.gamead.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binfen.im.gamead.ui.FragmentBase;
import com.bmob.im.demo.R;

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

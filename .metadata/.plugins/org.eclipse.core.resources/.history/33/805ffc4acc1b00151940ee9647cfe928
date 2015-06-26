package com.binfen.im.gamead.ui.fragment;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.binfen.im.gamead.adapter.GamesGridAdapter2;
import com.binfen.im.gamead.ui.FragmentBase;
import com.binfen.im.gamead.R;

public class ShowFragmentXXH2 extends FragmentBase {

	ImageButton imgbnt1;
	ImageButton imgbnt3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.xxh_gamelayout2, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		
		//initView();
		imgbnt1 = (ImageButton)findViewById(R.id.xxh_imagebnt1);
		imgbnt1.setOnClickListener(new ChangeFragment1());
		
		imgbnt3 = (ImageButton)findViewById(R.id.xxh_imagebnt3);
		imgbnt3.setOnClickListener(new ChangeFragment3());
	}
	
	
	private class ChangeFragment1 implements OnClickListener
    {     
        @Override  
        public void onClick(View v) 
        {  
        	// TODO Auto-generated method stub  
        	ShowFragmentXXH1 demoFragment = new ShowFragmentXXH1();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, demoFragment);
            transaction.commit();
       
        }  
    }
	
	private class ChangeFragment3 implements OnClickListener
    {     
        @Override  
        public void onClick(View v) 
        {  
        	// TODO Auto-generated method stub  
        	ShowFragmentXXH3 demoFragment = new ShowFragmentXXH3();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, demoFragment);
            transaction.commit();
       
        }  
    }
	
	
	private void initView(){
		initTopBarForOnlyTitle("сно╥");
		GridView mGridView=(GridView)findViewById(R.id.gridgame);
		mGridView.setAdapter(new GamesGridAdapter2(getActivity()));
		
	
	}
	
	
}

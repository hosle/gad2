package com.bmob.im.demo.ui.fragment;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.GamesGridAdapter2;
import com.bmob.im.demo.adapter.GamesGridAdapter3;
import com.bmob.im.demo.ui.FragmentBase;

public class ShowFragmentXXH3 extends FragmentBase {

	ImageButton imgbnt2;
	ImageButton imgbnt1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.xxh_gamelayout3, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		initView();
		imgbnt2 = (ImageButton)findViewById(R.id.xxh_imagebnt2);
		imgbnt2.setOnClickListener(new ChangeFragment2());
		
		imgbnt1 = (ImageButton)findViewById(R.id.xxh_imagebnt1);
		imgbnt1.setOnClickListener(new ChangeFragment1());
	}
	
	private class ChangeFragment2 implements OnClickListener
    {     
        @Override  
        public void onClick(View v) 
        {  
        	// TODO Auto-generated method stub  
        	ShowFragmentXXH2 demoFragment = new ShowFragmentXXH2();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, demoFragment);
            transaction.commit();
       
        }  
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
	
	
	private void initView(){
		initTopBarForOnlyTitle("сно╥");
		GridView mGridView=(GridView)findViewById(R.id.gridgame);
		mGridView.setAdapter(new GamesGridAdapter3(getActivity()));
		
	}
	
}

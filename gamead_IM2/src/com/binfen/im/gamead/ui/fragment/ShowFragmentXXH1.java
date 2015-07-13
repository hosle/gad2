package com.binfen.im.gamead.ui.fragment;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.binfen.im.gamead.adapter.GamesListAdapter1;
import com.binfen.im.gamead.ui.FragmentBase;
import com.binfen.im.gamead.R;

public class ShowFragmentXXH1 extends FragmentBase {

	//private Context mContext=getActivity();
	Fragment mContent;
	private ImageButton imgbnt2;
	private ImageButton imgbnt3;
	private Button mBtnGameCode;
	private EditText mEdtGameCode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.xxh_gamelayout1, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		initView();
		imgbnt2 = (ImageButton)findViewById(R.id.xxh_imagebnt2);
		imgbnt2.setOnClickListener(new ChangeFragment2());
		
		imgbnt3 = (ImageButton)findViewById(R.id.xxh_imagebnt3);
		imgbnt3.setOnClickListener(new ChangeFragment3());
		
		
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
		initTopBarForOnlyTitle("��Ϸ");
		
		
		ListView mListView=(ListView)findViewById(R.id.listgame);
		GamesListAdapter1 adapter1=new GamesListAdapter1(getActivity());
		mListView.setAdapter(adapter1);
		
		
		//ͨ����Ϸ��ֱ�ӽ�����Ϸ
		mBtnGameCode=(Button)findViewById(R.id.btn_gamecode);
		mEdtGameCode=(EditText)findViewById(R.id.edt_gamecode);
		
		mBtnGameCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String targetString=mEdtGameCode.getText().toString().trim();
				String typeString=targetString.substring(targetString.lastIndexOf("#g")+2, targetString.lastIndexOf("#p")-1);//ȡ���Ѷ�ֵ
				switch (typeString) {
				case "innerPintu":
					new GameCodeOnClick((Context)getActivity(),targetString).startGame();
					//tv_message.setOnClickListener(new myOnClickListener1(text));
					break;
				case "h":
					new GameCodeH5OnClick((Context)getActivity(),targetString).startGame();
					//tv_message.setOnClickListener(new H5OnClickListener(text));
					break;
				
				}
			}
		});
		
		
	}
	
	
	
	
	
}

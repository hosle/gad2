package com.binfen.im.gamead.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.binfen.im.gamead.adapter.GamesListAdapter2;
import com.binfen.im.gamead.ui.FragmentBase;
import com.binfen.im.gamead.R;

public class ShowFragmentXXH2 extends FragmentBase {

	ImageButton imgbnt1;
	ImageButton imgbnt3;
	private Button mBtnGameCode;
	private EditText mEdtGameCode;

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

		// initView();
		imgbnt1 = (ImageButton) findViewById(R.id.xxh_imagebnt1);
		imgbnt1.setOnClickListener(new ChangeFragment1());

		imgbnt3 = (ImageButton) findViewById(R.id.xxh_imagebnt3);
		imgbnt3.setOnClickListener(new ChangeFragment3());
	}

	private class ChangeFragment1 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowFragmentXXH1 demoFragment = new ShowFragmentXXH1();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, demoFragment);
			transaction.commit();

		}
	}

	private class ChangeFragment3 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowFragmentXXH3 demoFragment = new ShowFragmentXXH3();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, demoFragment);
			transaction.commit();

		}
	}

	private void initView() {
		initTopBarForOnlyTitle("游戏");
		
		ListView mListView = (ListView) findViewById(R.id.listgame);
		mListView.setAdapter(new GamesListAdapter2(getActivity()));
		

		
		// 通过游戏码直接进入游戏
		mBtnGameCode = (Button) findViewById(R.id.btn_gamecode);
		mEdtGameCode = (EditText) findViewById(R.id.edt_gamecode);

		mBtnGameCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String targetString = mEdtGameCode.getText().toString().trim();
				String typeString = targetString.substring(
						targetString.lastIndexOf("#g") + 2,
						targetString.lastIndexOf("#p") - 1);// 取得难度值
				switch (typeString) {
				case "innerPintu":
					new GameCodeOnClick((Context) getActivity(), targetString)
							.startGame();
					// tv_message.setOnClickListener(new
					// myOnClickListener1(text));
					break;
				case "h":
					new GameCodeH5OnClick((Context) getActivity(), targetString)
							.startGame();
					// tv_message.setOnClickListener(new
					// H5OnClickListener(text));
					break;

				}
			}
		});

	}

}

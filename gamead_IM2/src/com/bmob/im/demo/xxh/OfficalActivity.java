package com.bmob.im.demo.xxh;


import android.os.Bundle;

import android.widget.ImageButton;

import cn.bmob.im.inteface.EventListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.ui.ActivityBase;


public abstract class OfficalActivity extends ActivityBase implements EventListener{
	ImageButton offbnt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xxh_gamelayout1);
		//������ʱ�����񣨵�λΪ�룩-���������̨�Ƿ���δ������Ϣ���еĻ���ȡ����
		//�������ü�����ȽϺ������͵�������Ҳ����ȥ����仰-ͬʱ����onDestory���������stopPollService����
//		BmobChat.getInstance(this).startPollService(30);
		//�����㲥������
	}

	
	
}

package com.binfen.im.gamead.xxh;


import android.os.Bundle;
import android.widget.ImageButton;
import cn.bmob.im.inteface.EventListener;

import com.binfen.im.gamead.ui.ActivityBase;
import com.bmob.im.demo.R;


public abstract class OfficalActivity extends ActivityBase implements EventListener{
	ImageButton offbnt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xxh_gamelayout1);
		//开启定时检测服务（单位为秒）-在这里检测后台是否还有未读的消息，有的话就取出来
		//如果你觉得检测服务比较耗流量和电量，你也可以去掉这句话-同时还有onDestory方法里面的stopPollService方法
//		BmobChat.getInstance(this).startPollService(30);
		//开启广播接收器
	}

	
	
}

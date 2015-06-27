package com.game.adshow;

import com.game.util.HttpUtils;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

public class showBannerAd {

	private Context mContext;
	private Activity mActivity;
	private FrameLayout.LayoutParams layoutParams;
	private int flag=1;
	private  Handler mhandler;
	
	public showBannerAd(Context vContext) {
		// TODO Auto-generated constructor stub
		mContext=vContext;
		mActivity=(Activity)vContext;
		
		init();
	}
	private void init(){
		// 实例化LayoutParams(重要)
		layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
		
		mhandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x1233) {
					String resultString=msg.obj.toString();
					Log.i("test", "handler"+resultString);
					toast(resultString);
				}
				
			}
		};
	}
	public void showBanner(){
		
		chooseFromBackend();
		
		switch (flag) {
		case 0:
			showBanner_youmi(layoutParams);
			break;
		case 1:
			showBanner_baidu(layoutParams);
			break;

		default:
			break;
		}
		
	}
	private void chooseFromBackend(){
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg=new Message();
				String result = HttpUtils.doGet("http://113.10.157.125:3000/search");
				msg.what=0x1233;
				msg.obj=result;
				mhandler.sendMessage(msg);
			}
		}.start();
		

		/*if (result!=null) {
			toast(result);
		}else {
			toast("result 为空");
		}*/
	}
	
	public void showBanner_youmi(FrameLayout.LayoutParams vlayoutParams) {

		// 实例化广告条
		AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数

		
		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
		mActivity.addContentView(adView, vlayoutParams);
	}
	
	private void showBanner_baidu(FrameLayout.LayoutParams vlayoutParams){
		com.baidu.mobads.AdView adViewbai=new com.baidu.mobads.AdView(mContext);
		mActivity.addContentView(adViewbai, vlayoutParams);
	}
	
	public void toast(String t) {
		Toast.makeText(mContext, t, 200).show();
	}
}

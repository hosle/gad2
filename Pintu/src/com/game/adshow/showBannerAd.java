package com.game.adshow;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.im.BmobUserManager;

import com.game.adshow.thread.ApplyOnceThread;
import com.game.adshow.thread.MyApplyAdThread;
import com.game.operator.AdJifenManager;
import com.game.operator.Quit_PostRecord;
import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.userim.User;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class showBannerAd {

	private Context mContext;
	private Activity mActivity;
	private FrameLayout.LayoutParams layoutParams;
	private String flagString = "";
	private MyApplyAdThread mThread_applyAd;
	private ApplyOnceThread mThread_once;
	// private Thread mThread_postrecord;
	private Handler mhandler_applyAd;
	private Handler mHandler_applyclick;
	private Handler mHandler_delrec;
	// private Handler mHandler_postrecord;

	private BmobUserManager userManager;
	private User mUser;

	private FrameLayout fLayout;
	private FrameLayout.LayoutParams coverViewParams;

	private String backendUrl;


	//private boolean isrun = true;// adapply thread

	public showBannerAd(Context vContext) {
		// TODO Auto-generated constructor stub
		mContext = vContext;
		mActivity = (Activity) vContext;

		userManager = BmobUserManager.getInstance(mContext);
		mUser = userManager.getCurrentUser(User.class);

		// 获得后台地址
		backendUrl = AdJifenManager.getInstance(mContext).getBackendURL();

		init();
	}

	private void init() {

		coverViewParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, 200);
		coverViewParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

		// 实例化LayoutParams(重要)
		layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);

		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角

		mhandler_applyAd = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 200) {
					try {
						String resultString = msg.obj.toString();

						JSONObject jObj = new JSONObject(resultString);

						// 获得广告商名称
						flagString = jObj.getString("info");
						AdJifenManager.getInstance(mContext).setAdName(
								flagString);
						mThread_applyAd.setFlagString(flagString);
						Log.i("test", "handler" + resultString);
						toast(flagString);

						// 获得申请时间，并存在adJifenManager里面
						String vApplytime = jObj.getString("applytime");
						AdJifenManager.getInstance(mContext)
								.setApplyTime_adBanner(vApplytime);
						// toast(vApplytime.toString());

						fLayout = new FrameLayout(mContext);

						// 根据结果展示banner
						switch (AD.toAD(flagString.toUpperCase())) {
						case YOUMI:
							showBanner_youmi(layoutParams);

							break;
						case BAIDU:
							showBanner_baidu(layoutParams);

							break;
						case GDT:
							showBanner_gdt(layoutParams);

							break;
						default:

							break;
						}

						// show next adview
						addCoverView(coverViewParams);

						mActivity.addContentView(fLayout, layoutParams);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// this.removeCallbacks(mThread_applyAd);
				} else if (msg.what == 0x1) {
					toast("show time " + msg.obj + "is up");

					// remove the adview
					if (fLayout != null) {
						((ViewGroup) fLayout.getParent()).removeView(fLayout);
					}

					if (mThread_applyAd.isIsrun()) {
						// add show record
						new Quit_PostRecord(mContext).postDataToBackend();
					}
					

				}
			}
		};

		mHandler_applyclick = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 200) {

					String resultString = msg.obj.toString();
					toast("是否达到点击次数：" + resultString);
					if (resultString.equals("true")) {
						toast("再点一次试试");
						View curAdView = (View) fLayout
								.findViewWithTag("currentAdView");
						// 把广告banner暴露出来
						curAdView.bringToFront();
					}

					this.removeCallbacks(mThread_once);

				}
			}
		};

		mHandler_delrec = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 200) {

					String resultString = msg.obj.toString();
					toast(resultString);

					this.removeCallbacks(mThread_once);

				}
			}
		};

	}

	public void showBanner() {

		dogetApplyShow(mhandler_applyAd, backendUrl + "search?uid="
				+ mUser.getObjectId());

	}

	public void setIsrun(boolean isrun) {
		mThread_applyAd.setIsrun(isrun);
	}

	public void rmThread() {

		mThread_applyAd.interrupt();
		mhandler_applyAd.removeCallbacks(mThread_applyAd);
		
	}

	// 枚举每个广告商
	private enum AD {
		BAIDU, GDT, YOUMI, NOVALUE;

		public static AD toAD(String str) {
			try {
				return valueOf(str);
			} catch (Exception e) {
				// TODO: handle exception
				return NOVALUE;
			}
		}
	}
	//do get 申请点击，删除
	private void dogetApplyOnce(final Handler vHandler,final String urlString){
		mThread_once=new ApplyOnceThread(vHandler,urlString);
		mThread_once.start();
	}
	
	
	private void dogetApplyShow(final Handler vHandler,
			final String urlString) {
		mThread_applyAd = new MyApplyAdThread(mContext,vHandler, urlString);
		mThread_applyAd.start();

	}

	

	public void showBanner_youmi(FrameLayout.LayoutParams vlayoutParams) {

		// 实例化广告条
		AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数
		adView.setTag("currentAdView");

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
		fLayout.addView(adView, vlayoutParams);
		// mActivity.addContentView(adView, vlayoutParams);
	}

	private void showBanner_baidu(FrameLayout.LayoutParams vlayoutParams) {
		com.baidu.mobads.AdView adViewbai = new com.baidu.mobads.AdView(
				mContext);
		adViewbai.setTag("currentAdView");
		adViewbai.setListener(new com.baidu.mobads.AdViewListener() {

			@Override
			public void onVideoStart() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onVideoFinish() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onVideoError() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onVideoClickReplay() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onVideoClickClose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onVideoClickAd() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdSwitch() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdShow(JSONObject arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdReady(com.baidu.mobads.AdView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdFailed(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdClick(JSONObject arg0) {
				// TODO Auto-generated method stub
				View coverView = (View) fLayout.findViewWithTag("CoverView");
				coverView.bringToFront();

				// 成功点击后，添加一次积分，并删除该用户该广告商的记录
				commitJifen_delShowRecord();

			}
		});

		fLayout.addView(adViewbai, vlayoutParams);

		// mActivity.addContentView(adViewbai, vlayoutParams);
	}

	private void showBanner_gdt(FrameLayout.LayoutParams vlayoutParams) {
		com.qq.e.ads.AdView adViewgdt = new com.qq.e.ads.AdView(
				(Activity) mContext, com.qq.e.ads.AdSize.BANNER, "1104671462",
				"6040005399072658");
		AdRequest adr = new AdRequest();
		adViewgdt.setTag("currentAdView");
		adViewgdt.fetchAd(adr);
		adViewgdt.setAdListener(new AdListener() {

			@Override
			public void onNoAd(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNoAd() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBannerClosed() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdReceiv() {
				// TODO Auto-generated method stub
				// postDataToBackend("http://219.223.240.65:3000/addRecord");

			}

			@Override
			public void onAdExposure() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAdClicked() {
				// TODO Auto-generated method stub
				View coverView = (View) fLayout.findViewWithTag("CoverView");
				coverView.bringToFront();

				// 成功点击后，添加一次积分，并删除该用户该广告商的记录
				commitJifen_delShowRecord();

			}
		});
		fLayout.addView(adViewgdt, vlayoutParams);
		// mActivity.addContentView(adViewgdt, vlayoutParams);
	}

	/**
	 * @param vlayoutParams
	 *            在广告banner上加上一个view，用以判断是否满足点击要求
	 */
	private void addCoverView(FrameLayout.LayoutParams vlayoutParams) {

		final TextView txtTextView = new TextView(mContext);
		// txtTextView.setText("lalaalalal");
		txtTextView.setTag("CoverView");
		// txtTextView.setBackgroundColor(mContext.getResources().getColor(R.color.holo_orange_light));
		txtTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// 自动点击无效
				// curAdView.performClick();

				dogetApplyOnce(mHandler_applyclick, backendUrl
						+ "applyclick?uid=" + mUser.getObjectId() + "&ader="
						+ flagString);

			}
		});
		fLayout.addView(txtTextView, vlayoutParams);
		// mActivity.addContentView(txtTextView, vlayoutParams);
	}

	/**
	 * 成功点击后，添加一次积分，并删除该用户该广告商的记录
	 */
	private void commitJifen_delShowRecord() {
		// 上传积分
		AdJifenManager.getInstance(mContext).saveGameJifen(flagString, 1);

		// 删除记录
		dogetApplyOnce(mHandler_delrec, backendUrl + "delRecord?uid="
				+ mUser.getObjectId() + "&ader=" + flagString);
	}

	public void toast(String t) {
		Toast.makeText(mContext, t, 200).show();
	}
}

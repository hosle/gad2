package com.game.adshow;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.im.BmobUserManager;

import com.game.operator.AdJifenManager;
import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.userim.User;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class showBannerAd {

	private Context mContext;
	private Activity mActivity;
	private FrameLayout.LayoutParams layoutParams;
	private String flagString="";
	private Thread mThread_applyAd;
	//private Thread mThread_postrecord;
	private  Handler mhandler_applyAd;
	private Handler mHandler_applyclick;
	private Handler mHandler_delrec;
	//private Handler mHandler_postrecord;
	
	private BmobUserManager userManager ;
	private User mUser;
	
	private FrameLayout fLayout;
	private FrameLayout.LayoutParams coverViewParams;
	
	private String backendUrl;
	
	
	public showBannerAd(Context vContext) {
		// TODO Auto-generated constructor stub
		mContext=vContext;
		mActivity=(Activity)vContext;
		
		userManager=BmobUserManager.getInstance(mContext);
		mUser =  userManager.getCurrentUser(User.class);
		
		//��ú�̨��ַ
		backendUrl=AdJifenManager.getInstance(mContext).getBackendURL();
		
		init();
	}
	
	private void init(){
		
		fLayout=new FrameLayout(mContext);
		coverViewParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,200);
		coverViewParams.gravity=Gravity.BOTTOM | Gravity.RIGHT;
		
		// ʵ����LayoutParams(��Ҫ)
		layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		
		// ���ù����������λ��
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // ����ʾ��Ϊ���½�
		
		mhandler_applyAd=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==200) {
					try {
						String resultString=msg.obj.toString();
						
						JSONObject jObj=new JSONObject(resultString);
						
						//��ù��������
						flagString=jObj.getString("info");
						AdJifenManager.getInstance(mContext).setAdName(flagString);
						Log.i("test", "handler"+resultString);
						toast(flagString);
						
						//�������ʱ�䣬������adJifenManager����
						String vApplytime=jObj.getString("applytime");
						AdJifenManager.getInstance(mContext).setApplyTime_adBanner(vApplytime);
						//toast(vApplytime.toString());
						
						
						//���ݽ��չʾbanner
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
						
						addCoverView(coverViewParams);
						
						mActivity.addContentView(fLayout, layoutParams);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.removeCallbacks(mThread_applyAd);
					//mThread_applyAd.destroy();
				}
			}
		};
		
		mHandler_applyclick=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==200) {
					
						String resultString=msg.obj.toString();
						toast("�Ƿ�ﵽ���������"+resultString);
						if (resultString.equals("true")) {
							toast("�ٵ�һ������");
							View curAdView=(View)fLayout.findViewWithTag("currentAdView");
							//�ѹ��banner��¶����
							curAdView.bringToFront();
						}
					
						this.removeCallbacks(mThread_applyAd);
					
				}
			}
		};
		
		mHandler_delrec=new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==200) {
					
					String resultString=msg.obj.toString();
					toast(resultString);
					
				
					this.removeCallbacks(mThread_applyAd);
				
			}
			}
		};
		
		
	}
	public void showBanner(){
		
		getDataFromBackend(mhandler_applyAd,backendUrl+"search?uid="+mUser.getObjectId());
		
	}
	
	
	
	//ö��ÿ�������
	private enum AD{
		BAIDU,GDT,YOUMI,NOVALUE;
		
		public static AD toAD(String str) {
			try {
				return valueOf(str);
			} catch (Exception e) {
				// TODO: handle exception
				return NOVALUE;
			}
		}
	}
	private void getDataFromBackend(final Handler vHandler,final String urlString) {
		mThread_applyAd=new Thread(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg= vHandler.obtainMessage();
				String result="";
				HttpGet httpGet=new HttpGet(urlString);
				HttpResponse response;
				try {
					response = new DefaultHttpClient().execute(httpGet);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode==200) {
						HttpEntity entity=response.getEntity();
						result=EntityUtils.toString(entity, HTTP.UTF_8);
					}
					msg.what=statusCode;
					msg.obj=result;
					vHandler.sendMessage(msg);
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		mThread_applyAd.start();
		
		
	}
	
	public void showBanner_youmi(FrameLayout.LayoutParams vlayoutParams) {

		// ʵ���������
		AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
		// ����Activity��addContentView����
		adView.setTag("currentAdView");

		
		// ����������ӿ�
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������л�");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������ɹ�");
				
				

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������ʧ��");
			}
			
			
		});
		fLayout.addView(adView,vlayoutParams);
		//mActivity.addContentView(adView, vlayoutParams);
	}
	
	private void showBanner_baidu(FrameLayout.LayoutParams vlayoutParams){
		com.baidu.mobads.AdView adViewbai=new com.baidu.mobads.AdView(mContext);
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
				View coverView=(View)fLayout.findViewWithTag("CoverView");
				coverView.bringToFront();
				
				//�ɹ����������һ�λ��֣���ɾ�����û��ù���̵ļ�¼
				commitJifen_delShowRecord();
				
				
			}
		});
		
		fLayout.addView(adViewbai,vlayoutParams);
		
		
		//mActivity.addContentView(adViewbai, vlayoutParams);
	}
	
	private void showBanner_gdt(FrameLayout.LayoutParams vlayoutParams){
		com.qq.e.ads.AdView adViewgdt= new com.qq.e.ads.AdView((Activity) mContext, com.qq.e.ads.AdSize.BANNER, "1104671462", "6040005399072658");
		AdRequest adr=new AdRequest();
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
				//postDataToBackend("http://219.223.240.65:3000/addRecord");
				
			}
			
			@Override
			public void onAdExposure() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdClicked() {
				// TODO Auto-generated method stub
				View coverView=(View)fLayout.findViewWithTag("CoverView");
				coverView.bringToFront();
				
				//�ɹ����������һ�λ��֣���ɾ�����û��ù���̵ļ�¼
				commitJifen_delShowRecord();
				
			}
		});
		fLayout.addView(adViewgdt,vlayoutParams);
		//mActivity.addContentView(adViewgdt, vlayoutParams);
	}
	
	/**
	 * @param vlayoutParams
	 * �ڹ��banner�ϼ���һ��view�������ж��Ƿ�������Ҫ��
	 */
	private void addCoverView(FrameLayout.LayoutParams vlayoutParams) {
		
		final TextView txtTextView=new TextView(mContext);
		//txtTextView.setText("lalaalalal");
		txtTextView.setTag("CoverView");
		//txtTextView.setBackgroundColor(mContext.getResources().getColor(R.color.holo_orange_light));
		txtTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//�Զ������Ч
				//curAdView.performClick();
				
				getDataFromBackend(mHandler_applyclick,backendUrl+"applyclick?uid="+mUser.getObjectId()+"&ader="+flagString);
				
			}
		});
		fLayout.addView(txtTextView,vlayoutParams);
		//mActivity.addContentView(txtTextView, vlayoutParams);
	}
	
	/**
	 * �ɹ����������һ�λ��֣���ɾ�����û��ù���̵ļ�¼
	 */
	private void commitJifen_delShowRecord(){
		//�ϴ�����
		AdJifenManager.getInstance(mContext).saveGameJifen(flagString,1);
		
		//ɾ����¼
		getDataFromBackend(mHandler_delrec,backendUrl+"delRecord?uid="+mUser.getObjectId()+"&ader="+flagString);
	}
	
	public void toast(String t) {
		Toast.makeText(mContext, t, 200).show();
	}
}
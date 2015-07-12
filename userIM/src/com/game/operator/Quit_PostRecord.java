package com.game.operator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.im.BmobUserManager;

import com.userim.User;

import A.This;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class Quit_PostRecord {

	private Context mContext;
	private BmobUserManager userManager;
	private User mUser;
	private Thread mThread_postrecord;
	private String adName;
	private String applyTime_adBanner;

	private String backendURL;

	private Handler mHandler_postrecord;

	public Quit_PostRecord(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		userManager = BmobUserManager.getInstance(context);
		mUser = userManager.getCurrentUser(User.class);
		
		applyTime_adBanner = AdJifenManager.getInstance(mContext).getApplyTime_adBanner();
		adName = AdJifenManager.getInstance(mContext).getAdName();
		
		//��ú�̨��ַ
		backendURL=AdJifenManager.getInstance(mContext).getBackendURL();
		
		mHandler_postrecord = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 200) {
					String resultString = msg.obj.toString();
					toast(resultString);
					
					// ��ձ�������
					AdJifenManager.getInstance(mContext).setApplyTime_adBanner(null);
					AdJifenManager.getInstance(mContext).setAdName(null);

				}

				this.removeCallbacks(mThread_postrecord);
				
			}
		};
	}
	
	/**
	 * @param urlString
	 *            ���ӹ��ɹ�չʾ�ļ�¼
	 */
	private void postDataToBackend(final String urlString) {
		mThread_postrecord = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = mHandler_postrecord.obtainMessage();
				HttpPost httpPost = new HttpPost(urlString);
				HttpResponse response;
				// ���ò���,html�����ύ
				List<NameValuePair> paramlist = new ArrayList<NameValuePair>();

				JSONObject jsonRecord = new JSONObject();

				try {
					jsonRecord.put("adType", 28);
					// jsonRecord.put("duringTime", "800");
					jsonRecord.put("userId", mUser.getObjectId());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BasicNameValuePair param = new BasicNameValuePair(
						"jsonArrpost", jsonRecord.toString());
				paramlist.add(param);

				// ����ʱ��
				BasicNameValuePair param2 = new BasicNameValuePair("applytime",
						applyTime_adBanner);
				paramlist.add(param2);

				// aderName
				BasicNameValuePair param3 = new BasicNameValuePair("adername",
						adName);
				paramlist.add(param3);

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(paramlist,
							HTTP.UTF_8));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					response = new DefaultHttpClient().execute(httpPost);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						msg.what = statusCode;
						msg.obj = result;
						mHandler_postrecord.sendMessage(msg);
						}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		mThread_postrecord.start();
	}

	public void showDialogQuit() {
		Builder mDialog = new AlertDialog.Builder(mContext);
		// final int score=Config.time;
		mDialog.setTitle("�˳�");
		mDialog.setMessage("ȷ���˳���");
		mDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

				//toast("��ǰ��:"+adName);
				if (adName != null&&adName!=""&&adName!="null") {
					postDataToBackend(backendURL+"addRecord");

				}
				((Activity) mContext).finish();

			}

		});

		mDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
				// ((Activity)mContext).finish();
			}
		});

		mDialog.show();
	}

	/**
	 * �Զ���һ��Toast����
	 * 
	 * @param msg
	 *            Ҫ�������ʾ��Ϣ
	 */
	private void toast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}
}
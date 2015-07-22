package com.game.adshow.thread;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ApplyOnceThread extends Thread {

	private Handler vHandler;
	private String urlString;
	
	public ApplyOnceThread(Handler vHandler,String urlString){
		this.vHandler=vHandler;
		this.urlString=urlString;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 1��http����չʾ
		Message msg = vHandler.obtainMessage();
		String result = "";
		HttpGet httpGet = new HttpGet(urlString);
		HttpResponse response;
		try {
			response = new DefaultHttpClient().execute(httpGet);
			int statusCode = response.getStatusLine()
					.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, HTTP.UTF_8);
			}
			msg.what = statusCode;
			msg.obj = result;
			vHandler.sendMessage(msg);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

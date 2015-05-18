package com.game.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class HttpUtils{

	
	public static String doGet(String url)  {
		//String Result = null;
		
		try {
			// 获取httpclient对象
			HttpClient httpClient = new DefaultHttpClient();
			
			// 新建httpget对象
			HttpGet httpGet = new HttpGet(url);

			// 连接超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			// 请求超时
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					3000);
			// 获取httpresponse实例
			HttpResponse httpResponse = httpClient.execute(httpGet);

			// 判断是否请求成功
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 获取返回的数据
				HttpEntity entity=httpResponse.getEntity();
				String resultData=EntityUtils.toString(entity);
				Log.i("test", resultData);
				return resultData;
				//Result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				
			} else {
				
				Log.i("test", "httpgeterror");
				//toast( "HttpPost请求失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return null;
	}
	
	

	

}

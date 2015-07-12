package com.game.operator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.game.Game;
import com.game.jifen.GameJifen;
import com.userim.User;

/**
 * @author HenryTam
 *
 */
public class AdJifenManager {

	protected static final String Activity = null;
	private static BmobUserManager userManager;
	private static User mUser;
	private GameJifen mJifen;

	private int curJifen=0;// ��ǰ����

	// private Handler mHandler_postrecord;

	private Context mContext;
	private static AdJifenManager instance;

	// banner����ɹ���ʱ��
	private String applyTime_adBanner;
	private String adName;

	private static Game mGame;

	//private final String backendURL = "http://219.223.240.65:3000/";
	private final String backendURL = "http://113.10.157.125:3000/";

	public AdJifenManager(Context context) {
		super();
		mContext = context;
		userManager = BmobUserManager.getInstance(context);
		//mUser = userManager.getCurrentUser(User.class);
		// TODO Auto-generated constructor stub

		
		
		

	}

	public static synchronized AdJifenManager getInstance(Context vContext) {
		if (instance == null) {
			instance = new AdJifenManager(vContext);
		}
		mUser = userManager.getCurrentUser(User.class);
		mGame = GameManager.getInstance(vContext).getCurrentGame();

		return instance;

	}

	// �ϴ�һ����Ϸ����

	/**
	 * @param vJifen
	 *            ������Ϸ�Ļ���
	 */
	public void saveGameJifen(String ader, int vJifen) {

		mJifen = new GameJifen();
		mJifen.setPlayer(mUser);
		mJifen.setJifen(vJifen);
		mJifen.setGameId(mGame.getObjectId());
		mJifen.setExRate((float) 0.38);
		mJifen.setAder(ader);

		mJifen.save(mContext, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("��ɹ����һ�λ���");
				addGameJifenToUser();
				updateLocalJifen();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("fail");
			}
		});

	}

	/**
	 * ���ӻ��ֵ��û��Ļ�����Ϣ��
	 */
	private void addGameJifenToUser() {
		if (TextUtils.isEmpty(mUser.getObjectId())
				|| TextUtils.isEmpty(mJifen.getObjectId())) {
			toast("��ǰ�û����ߵ�ǰCard�����objectΪ��");
			return;
		}

		BmobRelation jifens = new BmobRelation();
		jifens.add(mJifen);
		mUser.setGameJifen(jifens);
		mUser.update(mContext, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�ѳɹ����ӵ��û��Ļ�����Ϣ��");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("���ź����û��Ļ�����Ϣ����ʧ��");
			}
		});
	}

	// ���±��ػ���
	public void updateLocalJifen() {
		
		//toast("��ǰ�û�Ϊ:"+mUser.getUsername());

		BmobQuery<GameJifen> query = new BmobQuery<GameJifen>();
		query.addWhereEqualTo("player", mUser);
		query.findObjects(mContext, new FindListener<GameJifen>() {

			@Override
			public void onSuccess(List<GameJifen> results) {
				// TODO Auto-generated method stub
				toast("���£���������Ϊ:"+results.size());
				// txt_set_jifen.setText(results.size());
				curJifen = results.size();

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	public String getApplyTime_adBanner() {
		return applyTime_adBanner;
	}

	public String getAdName() {
		return adName;
	}

	public void setApplyTime_adBanner(String applyTime_adBanner) {
		this.applyTime_adBanner = applyTime_adBanner;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getBackendURL() {
		return backendURL;
	}

	public int getCurJifen() {
		return curJifen;
	}

	public void setCurJifen(int curJifen) {
		this.curJifen = curJifen;
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
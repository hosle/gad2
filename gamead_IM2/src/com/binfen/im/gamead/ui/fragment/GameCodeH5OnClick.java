package com.binfen.im.gamead.ui.fragment;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;

import com.binfen.im.gamead.CustomApplcation;
import com.binfen.im.gamead.util.CollectionUtils;
import com.game.h5.H5GameMainActivity_0;
import com.userim.util.SerializableBCU;

public class GameCodeH5OnClick  {

	private Context mContext;
	private String text;
	ProgressDialog dialog = null;

	public GameCodeH5OnClick(Context vContext, String txt) {
		// TODO Auto-generated constructor stub
		mContext = vContext;
		text = txt;
	}

	public void startGame() {
		String vGameImagePath = text.substring(text.lastIndexOf("#p") + 2);// 得到定制的图片的名字

		Map<String, BmobChatUser> users = CustomApplcation.getInstance()
				.getContactList();

		Intent it = new Intent(mContext, H5GameMainActivity_0.class);
		// 传递list对象

		List<BmobChatUser> bcu = CollectionUtils.map2list(users);
		final SerializableBCU myList = new SerializableBCU();
		myList.setUsr(bcu);

		Bundle bundle = new Bundle();
		bundle.putSerializable("userlist", myList);
		bundle.putString("gameUrl", vGameImagePath);

		it.putExtras(bundle);

		mContext.startActivity(it);
	}

	

	public void ShowLog(String msg) {
		BmobLog.i(msg);
	}

	Toast mToast;

	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			((Activity) mContext).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(mContext, text,
								Toast.LENGTH_SHORT);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});

		}
	}
}
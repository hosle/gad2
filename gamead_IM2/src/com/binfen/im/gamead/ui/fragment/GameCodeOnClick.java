package com.binfen.im.gamead.ui.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;

import com.binfen.im.gamead.util.CollectionUtils;
import com.bmob.BmobProFile;
import com.bmob.im.demo.CustomApplcation;
import com.game.xxh.PintuMainActivity_0;
import com.userim.util.SerializableBCU;

public class GameCodeOnClick {

	private static Context mContext;
	private static String text;
	static ProgressDialog dialog = null;

	public GameCodeOnClick(Context vContext, String txt) {
		// TODO Auto-generated constructor stub
		mContext = vContext;
		text = txt;
	}

	public void startGame() {
		if (text.matches("^#g.*")) {
			Log.i("test", "text  :  " + text);

			String vNandu = text.substring(text.lastIndexOf("#g") + 2,
					text.lastIndexOf("#p"));// ȡ���Ѷ�ֵ
			String vGameImagePath = text.substring(text.lastIndexOf("#p") + 2);// �õ����Ƶ�ͼƬ������

			Map<String, BmobChatUser> users = CustomApplcation.getInstance()
					.getContactList();

			Intent it = new Intent(mContext, PintuMainActivity_0.class);
			// ����list����

			// SerializableBCU myMap =new SerializableMap();
			List<BmobChatUser> bcu = CollectionUtils.map2list(users);
			final SerializableBCU myList = new SerializableBCU();
			myList.setUsr(bcu);

			Bundle bundle = new Bundle();
			bundle.putSerializable("userlist", myList);
			// bundle.putString("fatherName", "gameFrag");
			it.putExtras(bundle);
			// ShowToast(vNandu+"+"+vGameImagePath);
			download(vNandu, vGameImagePath, it);
		}
	}

	/**
	 * ����ͼƬ��������Ϸ
	 * 
	 * @param downloadName
	 * @param it
	 */
	private static void download(final String nandu, String downloadName,
			final Intent it) {

		// currentGame=GameManager.getInstance(mContext).getCurrentGame();
		if (downloadName.equals("")) {
			ShowToast("��ָ�������ļ���");
			return;
		}
		dialog = new ProgressDialog(mContext);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("������...");
		dialog.setIndeterminate(false);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		BmobProFile.getInstance(mContext).download(downloadName,
				new com.bmob.btp.callback.DownloadListener() {

					@Override
					public void onError(int statuscode, String errormsg) {
						// TODO Auto-generated method stub
						ShowLog("MainActivity -download-->onError :"
								+ statuscode + "--" + errormsg);
						dialog.dismiss();
						ShowToast("���س�����" + errormsg);
					}

					@Override
					public void onSuccess(String fullPath) {
						// TODO Auto-generated method stub
						ShowLog("MainActivity -download-->onSuccess :"
								+ fullPath);
						dialog.dismiss();
						// showToast("���سɹ���"+fullPath);
						Bitmap img = BitmapFactory.decodeFile(fullPath);
						saveMyBitmapxxh("offical", img);

						String newimg[];
						newimg = new String[1];
						String gameNandu = nandu;// �õ���Ϸ���Ѷ�
						File destDirNanDu = new File(
								"/mnt/sdcard/gameimage/gamenandu.txt");
						if (!destDirNanDu.exists()) {
							destDirNanDu.mkdirs();
						}

						newimg[0] = gameNandu;
						// com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
						com.game.pintu.predict.WriteDate(
								"/mnt/sdcard/gameimage/gamenandu.txt", newimg);

						mContext.startActivity(it);
					}

					@Override
					public void onProgress(String arg0, int percent) {
						// TODO Auto-generated method stub
						ShowLog("MainActivity -download-->onProgress :"
								+ percent);
						dialog.setProgress(percent);
					}
				});
	}

	private static void saveMyBitmapxxh(String bitName, Bitmap mBitmap) {
		File f = new File("/mnt/sdcard/gameimage/" + bitName + ".jpg");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ShowLog(String msg) {
		BmobLog.i(msg);
	}

	static Toast mToast;

	public static void ShowToast(final String text) {
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
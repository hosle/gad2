package com.binfen.im.gamead.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.bmob.im.BmobDownloadManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.inteface.DownloadListener;

import com.binfen.im.gamead.CustomApplcation;
import com.binfen.im.gamead.adapter.base.BaseListAdapter;
import com.binfen.im.gamead.adapter.base.ViewHolder;
import com.binfen.im.gamead.ui.ImageBrowserActivity;
import com.binfen.im.gamead.ui.LocationActivity;
import com.binfen.im.gamead.ui.SetMyInfoActivity;
import com.binfen.im.gamead.util.CollectionUtils;
import com.binfen.im.gamead.util.FaceTextUtils;
import com.binfen.im.gamead.util.ImageLoadOptions;
import com.binfen.im.gamead.util.TimeUtil;
import com.bmob.BmobProFile;
import com.binfen.im.gamead.R;
import com.game.Game;
import com.game.h5.H5GameMainActivity;
import com.game.operator.GameManager;
import com.game.pintu.NewGame_received;
import com.game.xxh.AutoImageMainActivityXXH;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.userim.util.SerializableBCU;


/** ����������
  * @ClassName: MessageChatAdapter
  * @Description: TODO
  * @author smile
  * @date 2014-5-28 ����5:34:07
  */
public class MessageChatAdapter extends BaseListAdapter<BmobMsg> {

	//8��Item������
	//�ı�
	private final int TYPE_RECEIVER_TXT = 0;
	private final int TYPE_SEND_TXT = 1;
	//ͼƬ
	private final int TYPE_SEND_IMAGE = 2;
	private final int TYPE_RECEIVER_IMAGE = 3;
	//λ��
	private final int TYPE_SEND_LOCATION = 4;
	private final int TYPE_RECEIVER_LOCATION = 5;
	//����
	private final int TYPE_SEND_VOICE =6;
	private final int TYPE_RECEIVER_VOICE = 7;
	
	String currentObjectId = "";
	
	ProgressDialog dialog =null;

	DisplayImageOptions options;
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	public MessageChatAdapter(Context context,List<BmobMsg> msgList) {
		// TODO Auto-generated constructor stub
		super(context, msgList);
		currentObjectId = BmobUserManager.getInstance(context).getCurrentUserObjectId();
		
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.resetViewBeforeLoading(true)
		.cacheOnDisc(true)
		.cacheInMemory(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
	}

	@Override
	public int getItemViewType(int position) {
		BmobMsg msg = list.get(position);
		if(msg.getMsgType()==BmobConfig.TYPE_IMAGE){
			return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_IMAGE: TYPE_RECEIVER_IMAGE;
		}else if(msg.getMsgType()==BmobConfig.TYPE_LOCATION){
			return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_LOCATION: TYPE_RECEIVER_LOCATION;
		}else if(msg.getMsgType()==BmobConfig.TYPE_VOICE){
			return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_VOICE: TYPE_RECEIVER_VOICE;
		}else{
		    return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_TXT: TYPE_RECEIVER_TXT;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 8;
	}
	
	private View createViewByType(BmobMsg message, int position) {
		int type = message.getMsgType();
	   if(type==BmobConfig.TYPE_IMAGE){//ͼƬ����
			return getItemViewType(position) == TYPE_RECEIVER_IMAGE ? 
					mInflater.inflate(R.layout.item_chat_received_image, null) 
					:
					mInflater.inflate(R.layout.item_chat_sent_image, null);
		}else if(type==BmobConfig.TYPE_LOCATION){//λ������
			return getItemViewType(position) == TYPE_RECEIVER_LOCATION ? 
					mInflater.inflate(R.layout.item_chat_received_location, null) 
					:
					mInflater.inflate(R.layout.item_chat_sent_location, null);
		}else if(type==BmobConfig.TYPE_VOICE){//��������
			return getItemViewType(position) == TYPE_RECEIVER_VOICE ? 
					mInflater.inflate(R.layout.item_chat_received_voice, null) 
					:
					mInflater.inflate(R.layout.item_chat_sent_voice, null);
		}else{//ʣ��Ĭ�ϵĶ����ı�
			return getItemViewType(position) == TYPE_RECEIVER_TXT ? 
					mInflater.inflate(R.layout.item_chat_received_message, null) 
					:
					mInflater.inflate(R.layout.item_chat_sent_message, null);
		}
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final BmobMsg item = list.get(position);
		if (convertView == null) {
			convertView = createViewByType(item, position);
		}
		//�ı�����
		ImageView iv_avatar = ViewHolder.get(convertView, R.id.iv_avatar);
		final ImageView iv_fail_resend = ViewHolder.get(convertView, R.id.iv_fail_resend);//ʧ���ط�
		final TextView tv_send_status = ViewHolder.get(convertView, R.id.tv_send_status);//����״̬
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_message = ViewHolder.get(convertView, R.id.tv_message);
		//ͼƬ
		ImageView iv_picture = ViewHolder.get(convertView, R.id.iv_picture);
		final ProgressBar progress_load = ViewHolder.get(convertView, R.id.progress_load);//������
		//λ��
		TextView tv_location = ViewHolder.get(convertView, R.id.tv_location);
		//����
		final ImageView iv_voice = ViewHolder.get(convertView, R.id.iv_voice);
		//��������
		final TextView tv_voice_length = ViewHolder.get(convertView, R.id.tv_voice_length);
		
		//���ͷ������������
		String avatar = item.getBelongAvatar();
		if(avatar!=null && !avatar.equals("")){//����ͷ��-Ϊ�˲�ÿ�ζ�����ͷ��
			ImageLoader.getInstance().displayImage(avatar, iv_avatar, ImageLoadOptions.getOptions(),animateFirstListener);
		}else{
			iv_avatar.setImageResource(R.drawable.head);
		}
		
		iv_avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(mContext,SetMyInfoActivity.class);
				if(getItemViewType(position) == TYPE_RECEIVER_TXT 
						||getItemViewType(position) == TYPE_RECEIVER_IMAGE
				        ||getItemViewType(position)==TYPE_RECEIVER_LOCATION
				        ||getItemViewType(position)==TYPE_RECEIVER_VOICE){
					intent.putExtra("from", "other");
					intent.putExtra("username", item.getBelongUsername());
				}else{
					intent.putExtra("from", "me");
				}
				mContext.startActivity(intent);
			}
		});
		
		tv_time.setText(TimeUtil.getChatTime(Long.parseLong(item.getMsgTime())));
		
		if(getItemViewType(position)==TYPE_SEND_TXT
//				||getItemViewType(position)==TYPE_SEND_IMAGE//ͼƬ��������
				||getItemViewType(position)==TYPE_SEND_LOCATION
				||getItemViewType(position)==TYPE_SEND_VOICE){//ֻ���Լ����͵���Ϣ�����ط�����
			//״̬����
			if(item.getStatus()==BmobConfig.STATUS_SEND_SUCCESS){//���ͳɹ�
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				if(item.getMsgType()==BmobConfig.TYPE_VOICE){
					tv_send_status.setVisibility(View.GONE);
					tv_voice_length.setVisibility(View.VISIBLE);
				}else{
					tv_send_status.setVisibility(View.VISIBLE);
					tv_send_status.setText("�ѷ���");
				}
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_FAIL){//����������Ӧ���߲�ѯʧ�ܵ�ԭ����ɵķ���ʧ�ܣ�����Ҫ�ط�
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.VISIBLE);
				tv_send_status.setVisibility(View.INVISIBLE);
				if(item.getMsgType()==BmobConfig.TYPE_VOICE){
					tv_voice_length.setVisibility(View.GONE);
				}
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_RECEIVERED){//�Է��ѽ��յ�
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				if(item.getMsgType()==BmobConfig.TYPE_VOICE){
					tv_send_status.setVisibility(View.GONE);
					tv_voice_length.setVisibility(View.VISIBLE);
				}else{
					tv_send_status.setVisibility(View.VISIBLE);
					tv_send_status.setText("���Ķ�");
				}
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_START){//��ʼ�ϴ�
				progress_load.setVisibility(View.VISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.INVISIBLE);
				if(item.getMsgType()==BmobConfig.TYPE_VOICE){
					tv_voice_length.setVisibility(View.GONE);
				}
			}
		}
		//����������ʾ����
		final String text = item.getContent();
		switch (item.getMsgType()) {
		case BmobConfig.TYPE_TEXT:
			try {
				SpannableString spannableString = FaceTextUtils
						.toSpannableString(mContext, text);
				tv_message.setText(spannableString);
				
				String typeString=text.substring(text.lastIndexOf("#g")+2, text.lastIndexOf("#p")-1);//ȡ���Ѷ�ֵ
				switch (typeString) {
				case "innerPintu":
					tv_message.setOnClickListener(new myOnClickListener1(text));
					break;
				case "h":
					tv_message.setOnClickListener(new H5OnClickListener(text));
					break;
				
				}
				

			} catch (Exception e) {
			}
			break;

		case BmobConfig.TYPE_IMAGE://ͼƬ��
			try {
				if (text != null && !text.equals("")) {//���ͳɹ�֮��洢��ͼƬ���͵�content�ͽ��յ����ǲ�һ����
					dealWithImage(position, progress_load, iv_fail_resend, tv_send_status, iv_picture, item);
				}
				iv_picture.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent =new Intent(mContext,ImageBrowserActivity.class);
						ArrayList<String> photos = new ArrayList<String>();
						photos.add(getImageUrl(item));
						intent.putStringArrayListExtra("photos", photos);
						intent.putExtra("position", 0);
						mContext.startActivity(intent);
					}
				});
				
			} catch (Exception e) {
			}
			break;
			
		case BmobConfig.TYPE_LOCATION://λ����Ϣ
			try {
				if (text != null && !text.equals("")) {
					String address  = text.split("&")[0];
					final String latitude = text.split("&")[1];//ά��
					final String longtitude = text.split("&")[2];//����
					tv_location.setText(address);
					tv_location.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext, LocationActivity.class);
							intent.putExtra("type", "scan");
							intent.putExtra("latitude", Double.parseDouble(latitude));//ά��
							intent.putExtra("longtitude", Double.parseDouble(longtitude));//����
							mContext.startActivity(intent);
						}
					});
				}
			} catch (Exception e) {
				
			}
			break;
		case BmobConfig.TYPE_VOICE://������Ϣ
			try {
				if (text != null && !text.equals("")) {
					tv_voice_length.setVisibility(View.VISIBLE);
					String content = item.getContent();
					if (item.getBelongId().equals(currentObjectId)) {//���͵���Ϣ
						if(item.getStatus()==BmobConfig.STATUS_SEND_RECEIVERED
								||item.getStatus()==BmobConfig.STATUS_SEND_SUCCESS){//�����ͳɹ����߷������Ķ���ʱ������ʾ��������
							tv_voice_length.setVisibility(View.VISIBLE);
							String length = content.split("&")[2];
							tv_voice_length.setText(length+"\''");
						}else{
							tv_voice_length.setVisibility(View.INVISIBLE);
						}
					} else {//�յ�����Ϣ
						boolean isExists = BmobDownloadManager.checkTargetPathExist(currentObjectId,item);
						if(!isExists){//��ָ����ʽ��¼���ļ������ڣ�����Ҫ���أ���Ϊ���ļ��Ƚ�С���ʷ��ڴ�����
							String netUrl = content.split("&")[0];
							final String length = content.split("&")[1];
							BmobDownloadManager downloadTask = new BmobDownloadManager(mContext,item,new DownloadListener() {
								
								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.VISIBLE);
									tv_voice_length.setVisibility(View.GONE);
									iv_voice.setVisibility(View.INVISIBLE);//ֻ��������ɲ���ʾ���ŵİ�ť
								}
								
								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.GONE);
									tv_voice_length.setVisibility(View.VISIBLE);
									tv_voice_length.setText(length+"\''");
									iv_voice.setVisibility(View.VISIBLE);
								}
								@Override
								public void onError(String error) {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.GONE);
									tv_voice_length.setVisibility(View.GONE);
									iv_voice.setVisibility(View.INVISIBLE);
								}
							});
							downloadTask.execute(netUrl);
						}else{
							String length = content.split("&")[2];
							tv_voice_length.setText(length+"\''");
						}
					}
				}
				//���������ļ�
				iv_voice.setOnClickListener(new NewRecordPlayClickListener(mContext,item,iv_voice));
			} catch (Exception e) {
				
			}
			
			break;
		default:
			break;
		}
		return convertView;
	}

	private class myOnClickListener1 implements OnClickListener{

		private String text;
		public myOnClickListener1(String txt) {
			// TODO Auto-generated constructor stub
			text=txt;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (text.matches("^#g.*")) {
				Log.i("test", "text  :  "+text);
				
				String vNandu=text.substring(text.lastIndexOf("#g")+2, text.lastIndexOf("#p"));//ȡ���Ѷ�ֵ
				String vGameImagePath = text.substring(text.lastIndexOf("#p")+2);//�õ����Ƶ�ͼƬ������
				
				Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
			    
				Intent it= new Intent(mContext, AutoImageMainActivityXXH.class);
				//����list����
				
				 //SerializableBCU myMap =new SerializableMap();
				List<BmobChatUser> bcu=CollectionUtils.map2list(users);
				final SerializableBCU myList =new SerializableBCU();
				myList.setUsr(bcu);
				
				
				Bundle bundle =new Bundle();
				bundle.putSerializable("userlist", myList);
				//bundle.putString("fatherName", "gameFrag");
				it.putExtras(bundle);
				//ShowToast(vNandu+"+"+vGameImagePath);
				download(vNandu,vGameImagePath,it);
				
		}
	}
	}
	
	
	private class H5OnClickListener implements OnClickListener{

		private String text;
		public H5OnClickListener(String text){
			this.text=text;
		}
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//�洢��ǰ����Ϸgame��
			//GameManager.getInstance(mContext).setCurrentGame(gamelist.get(vCurrentIndex));
			//��ȡ��ǰ����Ϸgame��
			//Game tempGame=GameManager.getInstance(mContext).getCurrentGame();
			String vGameImagePath = text.substring(text.lastIndexOf("#p")+2);//�õ����Ƶ�ͼƬ������
			
			Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
		    
			Intent it= new Intent(mContext, H5GameMainActivity.class);
			//����list����
			
			
			List<BmobChatUser> bcu=CollectionUtils.map2list(users);
			final SerializableBCU myList =new SerializableBCU();
			myList.setUsr(bcu);
			
			
			Bundle bundle =new Bundle();
			bundle.putSerializable("userlist", myList);
			bundle.putString("gameUrl", vGameImagePath);
			
			it.putExtras(bundle);
				

			mContext.startActivity(it);
		}
		
	}
	/**
	 * ����ͼƬ��������Ϸ
	 * @param downloadName
	 * @param it
	 */
	private void download(final String nandu, String downloadName,final Intent it){
		
		//currentGame=GameManager.getInstance(mContext).getCurrentGame();
		if(downloadName.equals("")){
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
		BmobProFile.getInstance(mContext).download(downloadName, new com.bmob.btp.callback.DownloadListener() {
			
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				ShowLog("MainActivity -download-->onError :"+statuscode +"--"+errormsg);
				dialog.dismiss();
				ShowToast("���س���"+errormsg);
			}
			
			@Override
			public void onSuccess(String fullPath) {
				// TODO Auto-generated method stub
				ShowLog("MainActivity -download-->onSuccess :"+fullPath);
				dialog.dismiss();
				//showToast("���سɹ���"+fullPath);
				Bitmap img = BitmapFactory.decodeFile(fullPath);		
				saveMyBitmapxxh("offical",img);
				
				String newimg[];
				newimg = new String[1];
				String gameNandu = nandu;//�õ���Ϸ���Ѷ�
				File destDirNanDu = new File("/mnt/sdcard/gameimage/gamenandu.txt");
				  if (!destDirNanDu.exists()) {
					  destDirNanDu.mkdirs();
				}
				  
				newimg[0] = gameNandu;
				//com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/newimage.txt",newimg);
				com.game.pintu.predict.WriteDate("/mnt/sdcard/gameimage/gamenandu.txt",newimg);
				
				mContext.startActivity(it);
			}
			
			@Override
			public void onProgress(String arg0, int percent) {
				// TODO Auto-generated method stub
				ShowLog("MainActivity -download-->onProgress :"+percent);
				dialog.setProgress(percent);
			}
		});
	}
	private void saveMyBitmapxxh(String bitName,Bitmap mBitmap)
	{
		File f = new File("/mnt/sdcard/gameimage/" + bitName + ".jpg");
	    try {
		   f.createNewFile();
		}catch (IOException e) {
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
	
	/** ��ȡͼƬ�ĵ�ַ--
	  * @Description: TODO
	  * @param @param item
	  * @param @return 
	  * @return String
	  * @throws
	  */
	private String getImageUrl(BmobMsg item){
		String showUrl = "";
		String text = item.getContent();
		if(item.getBelongId().equals(currentObjectId)){//
			if(text.contains("&")){
				showUrl = text.split("&")[0];
			}else{
				showUrl = text;
			}
		}else{//������յ�����Ϣ������Ҫ����������
			showUrl = text;
		}
		return showUrl;
	}
	
	
	/** ����ͼƬ
	  * @Description: TODO
	  * @param @param position
	  * @param @param progress_load
	  * @param @param iv_fail_resend
	  * @param @param tv_send_status
	  * @param @param iv_picture
	  * @param @param item 
	  * @return void
	  * @throws
	  */
	private void dealWithImage(int position,final ProgressBar progress_load,ImageView iv_fail_resend,TextView tv_send_status,ImageView iv_picture,BmobMsg item){
		String text = item.getContent();
		if(getItemViewType(position)==TYPE_SEND_IMAGE){//���͵���Ϣ
			if(item.getStatus()==BmobConfig.STATUS_SEND_START){
				progress_load.setVisibility(View.VISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.INVISIBLE);
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_SUCCESS){
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.VISIBLE);
				tv_send_status.setText("�ѷ���");
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_FAIL){
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.VISIBLE);
				tv_send_status.setVisibility(View.INVISIBLE);
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_RECEIVERED){
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.VISIBLE);
				tv_send_status.setText("���Ķ�");
			}
//			����Ƿ��͵�ͼƬ�Ļ�����Ϊ��ʼ���ʹ洢�ĵ�ַ�Ǳ��ص�ַ�����ͳɹ�֮��洢���Ǳ��ص�ַ+"&"+�����ַ�������Ҫ�ж���
			String showUrl = "";
			if(text.contains("&")){
				showUrl = text.split("&")[0];
			}else{
				showUrl = text;
			}
			//Ϊ�˷���ÿ�ζ���ȡ����ͼƬ��ʾ
			ImageLoader.getInstance().displayImage(showUrl, iv_picture);
		}else{
			ImageLoader.getInstance().displayImage(text, iv_picture,options,new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
					progress_load.setVisibility(View.VISIBLE);
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					// TODO Auto-generated method stub
					progress_load.setVisibility(View.INVISIBLE);
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					progress_load.setVisibility(View.INVISIBLE);
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					progress_load.setVisibility(View.INVISIBLE);
				}
			});
		}
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	
}

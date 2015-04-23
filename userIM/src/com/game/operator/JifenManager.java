package com.game.operator;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.game.jifen.GameJifen;
import com.userim.User;

public class JifenManager {

	private BmobUserManager userManager ;
	private User mUser;
	private GameJifen mJifen;
	
	private Context mContext;
	private static JifenManager instance;
	public JifenManager(Context context) {
		super();
		mContext=context;
		userManager=BmobUserManager.getInstance(context);
		mUser =  userManager.getCurrentUser(User.class);
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized JifenManager getInstance(Context vContext) {
		if (instance==null) {
			instance=new JifenManager(vContext);
		}
		
		return instance;
		
	}

//�ϴ�һ����Ϸ����
    
    /**
     * @param vJifen ������Ϸ�Ļ���
     */
    public void saveGameJifen(int vJifen) {
    	
    	
    	 mJifen=new GameJifen();
    	 mJifen.setPlayer(mUser);
    	 mJifen.setJifen(vJifen);
    	 mJifen.setGameId("0303003");
    	 mJifen.setExRate((float)0.38);
    	 
    	 mJifen.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("��ɹ����һ�λ���");
				addGameJifenToUser();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("fail");
			}
		});
    	  
    	  
	}
    
    /**
	 * ��ӻ��ֵ��û��Ļ�����Ϣ��
	 */
	private void addGameJifenToUser(){
		if(TextUtils.isEmpty(mUser.getObjectId()) || 
				TextUtils.isEmpty(mJifen.getObjectId())){
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
				toast("�ѳɹ���ӵ��û��Ļ�����Ϣ��");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("���ź����û��Ļ�����Ϣ���ʧ��");
			}
		});
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

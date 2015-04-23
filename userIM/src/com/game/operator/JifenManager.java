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

//上传一次游戏积分
    
    /**
     * @param vJifen 本次游戏的积分
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
				toast("你成功获得一次积分");
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
	 * 添加积分到用户的积分信息中
	 */
	private void addGameJifenToUser(){
		if(TextUtils.isEmpty(mUser.getObjectId()) || 
				TextUtils.isEmpty(mJifen.getObjectId())){
			toast("当前用户或者当前Card对象的object为空");
			return;
		}
		
		BmobRelation jifens = new BmobRelation();
		jifens.add(mJifen);
		mUser.setGameJifen(jifens);
		mUser.update(mContext, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("已成功添加到用户的积分信息中");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("很遗憾，用户的积分信息添加失败");
			}
		});
	}

	/**
	 * 自定义一个Toast方法
	 * 
	 * @param msg
	 *            要输出的提示信息
	 */
	private void toast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	
}

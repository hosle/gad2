package com.game.operator;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Time;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.game.Forward;
import com.game.Game;
import com.userim.User;

public class GameManager {

	private Context mContext;
	private static GameManager instance;
	private BmobUserManager userManager ;
	private User mUser;
	
	private Game mGame;//新生成的游戏
	//private Game sendGame;//要发送的游戏
	//private Forward forwardGame;
	
	public GameManager(Context context) {
		// TODO Auto-generated constructor stub
		mContext=context;
		userManager=BmobUserManager.getInstance(context);
		mUser =  userManager.getCurrentUser(User.class);
	}
	
	public static synchronized GameManager getInstance(Context vContext) {
		if (instance==null) {
			instance=new GameManager(vContext);
		}
		return instance;
		
	}
	
//上传我定制的游戏
    
    /**
     * @param vJifen 本次游戏的内容
     */
    public void saveMyGame(String changeString) {
    	
    	
    	 mGame=new Game();
    	 mGame.setGameId(createGameId());//设置游戏id
    	 mGame.setGameOwnerUser(mUser);
    	 
    	 mGame.setSource("innerGame");
    	 mGame.setPreference(changeString);//修改的内容
    	 
    	 mGame.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				//toast("你成功生成一个游戏");
				addNewGameToUser();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("fail");
			}
		});
    	  
    	  
	}
    
    /**
	 * 添加新游戏到用户的游戏信息中
	 */
	private void addNewGameToUser(){
		if(TextUtils.isEmpty(mUser.getObjectId()) || 
				TextUtils.isEmpty(mGame.getObjectId())){
			toast("当前用户或者当前Card对象的object为空");
			return;
		}
		
		BmobRelation games = new BmobRelation();
		games.add(mGame);
		mUser.setmyGame(games);
		mUser.update(mContext, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("已成功添加新的游戏");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("很遗憾，新游戏添加失败");
			}
		});
	}

	
	/**
	 * 游戏ID gameId的产生规则：基本游戏名+生成者名称+游戏生成时间
	 * @return
	 */
	private String createGameId()
	{
		String result="";
		String baseGame="pintu";
		String usrname=mUser.getUsername();
		
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;
		String str = ""+year+"_"+month+"_"+date+"_"+hour+"_"+minute+"_"+second;
		
		result=baseGame+usrname+str;
		
		return result;
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
	
	
	/**
	 * 发送游戏
	 */
	public void sendGame(String gameId,String receiver)
	{
		//sendGame=game;
		final String vReceiver=receiver;
	    Forward forwardGame=new Forward();
		forwardGame.setGameId(gameId);
		forwardGame.setSenderName(mUser.getUsername());
		forwardGame.setReceiverName(receiver);

		forwardGame.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("你成功发送了一个游戏给"+vReceiver);
				//addForwardToGame();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("fail");
			}
		});
	}
   
}

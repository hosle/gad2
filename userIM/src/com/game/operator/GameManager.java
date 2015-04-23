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
	
	private Game mGame;//�����ɵ���Ϸ
	//private Game sendGame;//Ҫ���͵���Ϸ
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
	
//�ϴ��Ҷ��Ƶ���Ϸ
    
    /**
     * @param vJifen ������Ϸ������
     */
    public void saveMyGame(String changeString) {
    	
    	
    	 mGame=new Game();
    	 mGame.setGameId(createGameId());//������Ϸid
    	 mGame.setGameOwnerUser(mUser);
    	 
    	 mGame.setSource("innerGame");
    	 mGame.setPreference(changeString);//�޸ĵ�����
    	 
    	 mGame.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				//toast("��ɹ�����һ����Ϸ");
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
	 * �������Ϸ���û�����Ϸ��Ϣ��
	 */
	private void addNewGameToUser(){
		if(TextUtils.isEmpty(mUser.getObjectId()) || 
				TextUtils.isEmpty(mGame.getObjectId())){
			toast("��ǰ�û����ߵ�ǰCard�����objectΪ��");
			return;
		}
		
		BmobRelation games = new BmobRelation();
		games.add(mGame);
		mUser.setmyGame(games);
		mUser.update(mContext, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�ѳɹ�����µ���Ϸ");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("���ź�������Ϸ���ʧ��");
			}
		});
	}

	
	/**
	 * ��ϷID gameId�Ĳ������򣺻�����Ϸ��+����������+��Ϸ����ʱ��
	 * @return
	 */
	private String createGameId()
	{
		String result="";
		String baseGame="pintu";
		String usrname=mUser.getUsername();
		
		Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
		t.setToNow(); // ȡ��ϵͳʱ�䡣
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
	 * �Զ���һ��Toast����
	 * 
	 * @param msg
	 *            Ҫ�������ʾ��Ϣ
	 */
	private void toast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 * ������Ϸ
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
				toast("��ɹ�������һ����Ϸ��"+vReceiver);
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

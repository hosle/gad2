package com.game.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.Time;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.game.Forward;
import com.game.Game;
import com.game.ader.AdUnion;
import com.userim.User;

/**
 * @author HenryTam
 *
 */
public class AdUnionManager {

	private Context mContext;
	private static AdUnionManager instance;
	
	private Map<String, Integer> map_showTimeUpLimit=null;//�����ʾ������ʱ��
	
	public AdUnionManager(Context context) {
		// TODO Auto-generated constructor stub
		mContext=context;
	}
	
	public static synchronized AdUnionManager getInstance(Context vContext) {
		if (instance==null) {
			instance=new AdUnionManager(vContext);
		}
		return instance;
		
	}
	//��ѯ���ʱ����ʾ���޵��б�
	public void queryShowTimeUpLimit(){
		
		map_showTimeUpLimit=null;
		BmobQuery<AdUnion> query = new BmobQuery<AdUnion>();
		//ִ�в�ѯ����
		query.findObjects(mContext, new FindListener<AdUnion>() {
		        @Override
		        public void onSuccess(List<AdUnion> object) {
		            // TODO Auto-generated method stub
		            //toast("AdUnionManager ��ѯ�ɹ�����"+object.size()+"�����ݡ�");
		            map_showTimeUpLimit=new HashMap<String, Integer>();
		            for (AdUnion ader : object) {
		            	//���showTimeUpLimit����Ϣ,����arraylist��
		            	map_showTimeUpLimit.put(ader.getAderName(), ader.getShowTimeUpLimit());
		            }
		            
		        }
		        @Override
		        public void onError(int code, String msg) {
		            // TODO Auto-generated method stub
		            toast("��ѯʧ�ܣ�"+msg);
		        }
		});
		
		
	}
	
	
	public Map<String, Integer> getMap_showTimeUpLimit() {
		return map_showTimeUpLimit;
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
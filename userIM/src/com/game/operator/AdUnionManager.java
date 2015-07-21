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
	
	private Map<String, Integer> map_showTimeUpLimit=null;//广告显示的上限时间
	
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
	//查询获得时间显示上限的列表
	public void queryShowTimeUpLimit(){
		
		map_showTimeUpLimit=null;
		BmobQuery<AdUnion> query = new BmobQuery<AdUnion>();
		//执行查询方法
		query.findObjects(mContext, new FindListener<AdUnion>() {
		        @Override
		        public void onSuccess(List<AdUnion> object) {
		            // TODO Auto-generated method stub
		            //toast("AdUnionManager 查询成功：共"+object.size()+"条数据。");
		            map_showTimeUpLimit=new HashMap<String, Integer>();
		            for (AdUnion ader : object) {
		            	//获得showTimeUpLimit的信息,放入arraylist中
		            	map_showTimeUpLimit.put(ader.getAderName(), ader.getShowTimeUpLimit());
		            }
		            
		        }
		        @Override
		        public void onError(int code, String msg) {
		            // TODO Auto-generated method stub
		            toast("查询失败："+msg);
		        }
		});
		
		
	}
	
	
	public Map<String, Integer> getMap_showTimeUpLimit() {
		return map_showTimeUpLimit;
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

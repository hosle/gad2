/**
 * 
 */
package com.userim.util;

import java.io.Serializable;
import java.util.List;
import cn.bmob.im.bean.BmobChatUser;

/**
 * @author HenryTam
 *
 */
public class SerializableBCU implements Serializable {

	private List<BmobChatUser> usr;

	public List<BmobChatUser> getUsr() {
		return usr;
	}

	public void setUsr(List<BmobChatUser> usr) {
		this.usr = usr;
	}
}

/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  FriendType.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.domain 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:52:45 
 * @version  V1.0   
 */
package com.woyi.mhub.im.domain;

import java.util.List;


/** 
 * @ClassName  FriendType 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:52:45 
 * 
 */
public class FriendType {
	
	private int id;

	private String typeName;
	
	private List<Friend> friends;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	public List<Friend> getFriends() {
		return friends;
	}

}

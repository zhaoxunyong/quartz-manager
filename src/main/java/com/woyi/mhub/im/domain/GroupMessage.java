/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  GroupMessage.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:58:08 
 * @version  V1.0   
 */
package com.woyi.mhub.im.domain;

/**
 * @ClassName GroupMessage
 * @Description TODO
 * @author jie_huang@woyitech.com
 * @date 2016年10月13日 下午1:58:08
 */
public class GroupMessage {

	private int groupId;
	private int userId;
	private String type;
	private String content;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

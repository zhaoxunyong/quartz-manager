/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  UserInfo.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.domain 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:50:34 
 * @version  V1.0   
 */
package com.woyi.mhub.im.domain;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @author jie_huang@woyitech.com
 * @date 2016年10月13日 下午1:50:34
 */
public class UserInfo {

	public int id;

	public String logo;

	public String sign;

	public String ucName;

	public int isOnline;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUcName() {
		return ucName;
	}

	public void setUcName(String ucName) {
		this.ucName = ucName;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

}

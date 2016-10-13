/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  UserInfoService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.service 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午5:00:20 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import com.woyi.mhub.im.domain.UserInfo;

/** 
 * @ClassName  UserInfoService 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午5:00:20 
 * 
 */
public interface UserInfoService {

	UserInfo getById(int userId);

}

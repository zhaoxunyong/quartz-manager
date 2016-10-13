/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  UserOnlineService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.service 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午5:00:32 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import com.woyi.mhub.im.domain.UserInfo;

/** 
 * @ClassName  UserOnlineService 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午5:00:32 
 * 
 */
public interface UserOnlineService {

	UserInfo getById(int userId);

}

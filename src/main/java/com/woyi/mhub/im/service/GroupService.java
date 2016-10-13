/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  GroupService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:57:55 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import com.woyi.mhub.im.domain.UserInfo;

/**
 * @ClassName GroupService
 * @Description TODO
 * @author jie_huang@woyitech.com
 * @date 2016年10月13日 下午1:57:55
 * 
 */
public interface GroupService {

	UserInfo getGroupMaster(int id);

}

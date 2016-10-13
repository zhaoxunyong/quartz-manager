/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  FriendTypeService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.service 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:22:45 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import java.util.List;

import com.woyi.mhub.im.domain.FriendType;


/** 
 * @ClassName  FriendTypeService 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:22:45 
 * 
 */
public interface FriendTypeService {

	List<FriendType> getFriendTypeByUserId(int userId);

}

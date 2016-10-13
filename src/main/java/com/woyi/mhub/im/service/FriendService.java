/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  FriendService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.service 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午4:59:46 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import java.util.List;

import com.woyi.mhub.im.domain.SNSFriend;

/** 
 * @ClassName  FriendService 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午4:59:46 
 * 
 */
public interface FriendService {

	List<SNSFriend> searchFriendByFriendId(int friendId);

	List<SNSFriend> searchFriendByRemarkName(String remarkName);

	boolean isFriend(int userId, int friendId);

	int delFriend(int userId, int friendId);

}

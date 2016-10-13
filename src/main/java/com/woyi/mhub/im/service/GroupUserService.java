/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  GroupUserService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.service 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月13日 下午1:23:42 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import java.util.List;

import com.woyi.mhub.im.Group;
import com.woyi.mhub.im.domain.GroupUser;

/**
 * @ClassName GroupUserService
 * @Description TODO
 * @author jie_huang@woyitech.com
 * @date 2016年10月13日 下午1:23:42
 * 
 */
public interface GroupUserService {

	List<Group> getByUserId(int userId);

	Object getSimpleMemberByGroupId(int groupId);

	List<GroupUser> getByGroupId(int id);

}

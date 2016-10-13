/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  UserService.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.im.service 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午5:00:05 
 * @version  V1.0   
 */
package com.woyi.mhub.im.service;

import com.github.pagehelper.Page;
import com.woyi.mhub.im.domain.User;

/** 
 * @ClassName  UserService 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月12日 下午5:00:05 
 * 
 */
public interface UserService {

	Page<User> search(String searchStr, String userRole, String schoolYear, String provinceCode, String cityCode,
			String countyCode, Integer schoolId, String gradeCode, Integer classId, Integer pageNum, Integer pageSize);

}

/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  ImportExcelController.java 
 * @Prject  quartz-manager
 * @Package  com.woyi.mhub.task.controller 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月9日 下午1:48:48 
 * @version  V1.0   
 */
package com.woyi.mhub.task.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @ClassName  ImportExcelController 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年10月9日 下午1:48:48 
 * 
 */
@Controller
@RequestMapping("/import")
public class ImportExcelController {
	
	
	@RequestMapping("index")
	public String taskList(HttpServletRequest request){
		return "import/index";
	}

}

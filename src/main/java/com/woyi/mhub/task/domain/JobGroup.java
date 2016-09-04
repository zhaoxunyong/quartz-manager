package com.woyi.mhub.task.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* 此类描述的是：分组任务
* @author: jie_hoang@woyitech.com
* @version: 2016年9月2日 下午3:38:59
* @ClassName: JobGroup 
* @项目： quartz-manager
* @包：com.woyi.mhub.task.domain
 */
public class JobGroup {

	private String groupName;  //分组名称

	private List<JobTaskDetail> list; //任务

	public void add(JobTaskDetail taskDetail) {
		if (list == null) {
			list = new ArrayList<JobTaskDetail>();
		}
		list.add(taskDetail);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<JobTaskDetail> getList() {
		return list;
	}

	public void setList(List<JobTaskDetail> list) {
		this.list = list;
	}


}

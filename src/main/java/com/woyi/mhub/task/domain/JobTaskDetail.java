package com.woyi.mhub.task.domain;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Trigger;

public class JobTaskDetail {

	private String jobName;  //任务名称
	private String description;  //任务描述

	private String status;  //任务状态
	
	private List<Trigger> triggerList; //触发器组
	
	private Trigger trigger;
	

	public void add(Trigger trigger) {
		if (triggerList == null) {
			triggerList = new ArrayList<Trigger>();
		}
		triggerList.add(trigger);
	}


	public List<Trigger> getTriggerList() {
		return triggerList;
	}

	public void setTriggerList(List<Trigger> list) {
		this.triggerList = list;
	}

	public String getJobName() {
		return jobName;
	}


	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public Trigger getTrigger() {
		return trigger;
	}


	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public static enum TriggerState {
		NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED;
	}
	
}

/**   
 * Copyright © 2016 Hefei Wo Yi Information Technology Co., Ltd. All rights reserved.
 * 
 * @Title  JobTaskController.java 
 * @Prject  quartz-manager
 * @Package  com.snailxr.base.task.controller 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年8月29日 下午4:15:02 
 * @version  V1.0   
 */
package com.woyi.mhub.task.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyi.mhub.support.ResultMsg;
import com.woyi.mhub.task.domain.JobGroup;
import com.woyi.mhub.task.domain.JobTaskDetail;
import com.woyi.mhub.task.domain.ScheduleJob;
import com.woyi.mhub.task.service.JobTaskService;

/** 
 * @ClassName  JobTaskController 
 * @Description  TODO
 * @author  jie_huang@woyitech.com
 * @date  2016年8月29日 下午4:15:02 
 * 
 */
@Controller
@RequestMapping("/jobTask")
public class JobTaskController {
	
	@Autowired
	private JobTaskService taskService;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	
	/**
	 * 计划任务
	 */
	@RequestMapping("taskList")
	public String taskList(HttpServletRequest request){
		List<ScheduleJob> taskList = taskService.getAllTask();
		request.setAttribute("taskList", taskList);
		return "taskList";
	}
	
	/**
	 * quartz 集群任务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("quartzList")
	public String quartzList(HttpServletRequest request){
		try {
			//获取所有任务分组
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			List<JobGroup> grouplist = new ArrayList<JobGroup>();
			JobGroup jobGroup = null;
			List<JobTaskDetail> jobList = null;
			for (String groupName : scheduler.getJobGroupNames()) {
				jobGroup = new JobGroup();
				jobList = new ArrayList<JobTaskDetail>();
				jobGroup.setGroupName(groupName);
				JobTaskDetail taskDetail = null;
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
					taskDetail = new JobTaskDetail();
					taskDetail.setJobName(jobKey.getName()); //任务名
					
					List<Trigger>  tlist = (List<Trigger>)scheduler.getTriggersOfJob(jobKey);
					taskDetail.setTriggerList(tlist); //触发器组
					taskDetail.setTrigger(tlist.get(0));
					
					taskDetail.setStatus(scheduler.getTriggerState(tlist.get(0).getKey()).name().toString()); //任务状态
					
					taskDetail.setDescription(scheduler.getJobDetail(jobKey).getDescription());
					jobList.add(taskDetail);
				}
				jobGroup.setList(jobList);
				grouplist.add(jobGroup);
			}
			
			request.setAttribute("grouplist", grouplist);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "quartzList";
	}
	
	
	@RequestMapping("showAdd")
	public String showAdd(HttpServletRequest request){
		return "add";
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	public ResultMsg doAdd(HttpServletRequest request, ScheduleJob scheduleJob) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setFlag(true);
		
		//验证cron表达式
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setMsg("Cron表达式有误");
			resultMsg.setFlag(false);
		}
		
		if(resultMsg.isFlag()){
			
			//验证job类
			Object obj = null;
			try {
				Class clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				resultMsg.setMsg("未找到目标类");
				resultMsg.setFlag(false);
			}
			if(obj != null){
				//判断是否实现 org.quartz.Job 
				if(!(obj instanceof Job)){
					resultMsg.setMsg("此类不符合");
					resultMsg.setFlag(false);
				}
			}
		}
		
		if(resultMsg.isFlag()){
			try {
				//默认初始不启动
				scheduleJob.setJobStatus("0");
				taskService.addTask(scheduleJob);
			} catch (Exception e) {
				e.printStackTrace();
				resultMsg.setMsg("保存失败，检查 name group 组合是否有重复");
				//resultMsg.setMsg(e.getMessage());
				resultMsg.setFlag(false);
			}
		}
		return resultMsg;
	}
	
	
	@RequestMapping("changeStatus")
	@ResponseBody
	public ResultMsg changeStatus(HttpServletRequest request, Long jobId, String status){
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setFlag(false);
		try {
			taskService.changeStatus(jobId, status);
			resultMsg.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMsg;
	}
	
	

}

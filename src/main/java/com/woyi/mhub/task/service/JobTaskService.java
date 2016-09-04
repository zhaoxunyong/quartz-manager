package com.woyi.mhub.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.woyi.mhub.task.dao.ScheduleJobMapper;
import com.woyi.mhub.task.domain.ScheduleJob;

/**
 *  计划任务管理
 */
@Service
public class JobTaskService {
	public final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.getAll();
	}

	public void addTask(ScheduleJob job) {
		job.setCreateTime(new Date());
		scheduleJobMapper.insertSelective(job);
	}

	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	/**
	 * 更改任务状态
	 */
	public void changeStatus(Long jobId, String status) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if (ScheduleJob.STATUS_NOT_RUNNING.equals(status)) {
			//暂停job
			deleteJob(job);
			job.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if (ScheduleJob.STATUS_RUNNING.equals(status)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			//开启job
			addJob(job);
		}
		job.setUpdateTime(new Date());
		scheduleJobMapper.updateByPrimaryKeySelective(job);
	}
	
	
	/*********** 任务处理 *************/

	/**
	 * 
	* 此方法描述的是：添加任务
	* @Title: addJob 
	* @author: jie_hoang@woyitech.com
	* @param job
	* @return void    返回类型
	* @version: 2016年9月2日 下午4:16:58
	 */
	public void addJob(ScheduleJob job){
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
			
		//检测job执行记录
		JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroup());
		JobDetail jobDetail = null;
		try {
			jobDetail = scheduler.getJobDetail(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		if(null == jobDetail){ //无记录，添加job
			logger.info("开始添加任务... "+ job.getJobName());
			//构建job实例
			Class clazz = null;
			Object obj = null;
			boolean flag = true;
			try {
				clazz = Class.forName(job.getBeanClass());
				obj = clazz.newInstance();
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
				logger.error("请检查任务类... "+ job.getBeanClass());
			}
			if(obj != null){
				//判断是否实现 org.quartz.Job 
				if(!(obj instanceof Job)){
					flag = false;
					logger.error("任务类... "+ job.getBeanClass() + " 不符合规范");
				}
			}
			if(flag){ //配置正常
				jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).withDescription(job.getDescription()).build();
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

				
				CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
				try {
					scheduler.scheduleJob(jobDetail, trigger);
				} catch (SchedulerException e) {
					e.printStackTrace();
					logger.error("任务... "+ job.getJobName() + " 启动失败");
				}
			}
			
		}else{ //任务已存在
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			try {
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				// 不存在，创建一个
				if (null == trigger) {
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
					scheduler.scheduleJob(jobDetail, trigger);
				} else { //Trigger已存在，更新
					trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
					scheduler.rescheduleJob(triggerKey, trigger);
				}
				logger.error("任务... "+ job.getJobName() + " 触发器更新成功");
			} catch (SchedulerException e) {
				e.printStackTrace();
				logger.error("任务... "+ job.getJobName() + " 触发器更新失败");
			}
			
		}
	}
	

	/**
	 * 
	* 此方法描述的是：暂停job
	* @Title: pauseJob 
	* @author: jie_hoang@woyitech.com
	* @param scheduleJob
	* @throws SchedulerException
	* @return void    返回类型
	* @version: 2016年9月2日 下午5:34:04
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}
	
	
	/**
	 * 
	* 此方法描述的是：恢复job
	* @Title: resumeJob 
	* @author: jie_hoang@woyitech.com
	* @param scheduleJob
	* @throws SchedulerException
	* @return void    返回类型
	* @version: 2016年9月2日 下午5:34:25
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}
	
	
	/**
	 * 
	* 此方法描述的是：删除job
	* @Title: deleteJob 
	* @author: jie_hoang@woyitech.com
	* @param scheduleJob
	* @throws SchedulerException
	* @return void    返回类型
	* @version: 2016年9月2日 下午5:34:50
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}
	
	
	
	
	
}

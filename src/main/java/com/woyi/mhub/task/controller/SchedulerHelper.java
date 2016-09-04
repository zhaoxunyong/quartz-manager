package com.woyi.mhub.task.controller;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 
 * 此类描述的是：任务的创建和管理
 * 
 * @author: jie_hoang@woyitech.com
 * @version: 2016年8月25日 下午8:08:51
 * @ClassName: SchedulerHelper
 * @项目： MyQuartz
 * @包：com.woyi.mhub.quartz
 */
public class SchedulerHelper {

	private static Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);

	private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";// 任务组
	private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";// 触发器组

	private static Scheduler scheduler;  
	private SchedulerFactory schedulerFactory;  
	private StdSchedulerFactory stdSchedulerFactory;  
	
	private static SchedulerFactoryBean schedulerFactoryBean;
	
	public SchedulerHelper(){
		if(null == this.schedulerFactory){
			this.schedulerFactory = new StdSchedulerFactory(); 
		}
	}
	
	public static void main(String[] ars) {
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			
			scheduler = sf.getScheduler();
			List<String> rr = scheduler.getJobGroupNames();
			
			JobKey jobKey = new JobKey("job1",JOB_GROUP_NAME);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			
			TriggerKey triggerKey = TriggerKey.triggerKey("trigger1",TRIGGER_GROUP_NAME);
			Trigger trigger = scheduler.getTrigger(triggerKey);
			
			
			System.out.println("_______");
			
			//getJobDetail(null);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		


	}
	 

	/**
	 * 加入job 
	 */
	public static void addJob(String jobName, String triggerName,
			Class<? extends Job> jobClass, int seconds){
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			
/*			//显式配置
			StdSchedulerFactory sf = new StdSchedulerFactory();
			Properties props = new Properties();
			FileInputStream in = new FileInputStream("F:/Workspaces/MyQuartz/src/quartz.properties");
			props.load(in);
			in.close();
			
			props.put("org.quartz.scheduler.instanceName", "TestScheduler1");
			props.put("org.quartz.scheduler.instanceId", "WOYI"+System.currentTimeMillis());
			sf.initialize(props);
*/			
			
			scheduler = sf.getScheduler();
			
			//检测job执行记录
			JobKey jobKey = new JobKey(jobName,JOB_GROUP_NAME);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if(null == jobDetail){ //无记录
				logger.info("add {"+ jobName +"} Scheduler");
				
				//构建job实例
				jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
				
				//构建触发器
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME)
								.startNow()
								.withSchedule(SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(seconds)
								.repeatForever())
								.build();// 产生触发器
				
				scheduler.scheduleJob(jobDetail, trigger);
				
			}else{ //有记录
				TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,TRIGGER_GROUP_NAME);
				SimpleTriggerImpl simpleTrigger = (SimpleTriggerImpl) scheduler.getTrigger(triggerKey);
		        simpleTrigger.setRepeatInterval(seconds*1000);
		        
				
				//重新设置job执行
				scheduler.rescheduleJob(triggerKey, simpleTrigger);
				logger.info(scheduler.getSchedulerInstanceId() + " .... "+ jobName +  " 重置触发器 ");    
				
			}
			
			// 启动
			scheduler.start();
			logger.info(""+ jobName +" start");     
		} catch (Exception e) {
			logger.error(" add "+ jobName +" start error", e);    
			
		}
		
	}
	
	
	/**
	 * 清除
	 */
	public static void clearAllScheduler() {
		try {
			scheduler.clear();
		} catch (SchedulerException e) {
			logger.error("clearAllScheduler", e);
		}
	}
	
   
    public static JobKey getJobKey(String jobName, String jobGroup){
		return new JobKey(jobName,jobGroup);
    }  
	
    
	

}

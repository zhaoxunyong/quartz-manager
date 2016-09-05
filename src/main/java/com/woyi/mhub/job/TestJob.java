package com.woyi.mhub.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.woyi.mhub.task.util.DateUtil;


public class TestJob extends QuartzJobBean{
	
	@Override
	protected void executeInternal(JobExecutionContext paramJobExecutionContext)
			throws JobExecutionException {
		try {
			
			/*//获取JobExecutionContext中的service对象    
            SchedulerContext schedulerContext = paramJobExecutionContext.getScheduler().getContext();     
            //获取SchedulerContext中的service    
            //这里的service就是通过配置文件 配置的    
            schedulerContext.get("**Service");   */
            
            String schedulerinstanceid =  paramJobExecutionContext.getScheduler().getSchedulerInstanceId();
        	
        	System.out.println(schedulerinstanceid +" .... 执行任务 1 .... " + DateUtil.getNow());
                 
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
}

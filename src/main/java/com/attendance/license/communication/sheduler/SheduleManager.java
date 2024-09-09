package com.nusyn.license.communication.sheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.nusyn.license.exception.StartupException;

import com.nusyn.license.main.controller.WebSocketController;
import com.nusyn.license.manager.SpringContextManager;


public class SheduleManager {

	private static SheduleManager instance = null;
	private List<String> runningJobs = new ArrayList<String>();
	private boolean running;
	
	
	public static SheduleManager getInstance() {
		if (instance == null) {
			instance = new SheduleManager();
			
			try {
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();

				if (!scheduler.isStarted()) {
					scheduler.start();
				}
			} catch (Exception exp) {

			}
		}
		return instance;
	}
	
	public static enum SchedulingUnit {
		Minutes("Minutes"), 
		Seconds("Seconds"),
		MilliSeconds("MilliSeconds"), 
		Hours("Hours"),
		Days("Days"),
		Weeks("Weeks"),
		Months("Months");
	    private String name = "";
	    SchedulingUnit(String name)
	    {
	    	this.name = name;
	    }
	    
	    public static SchedulingUnit getScheduleUnitFromString(String strStatus)
		{
			if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.Minutes.name) == 0))
			{
				return SchedulingUnit.Minutes;
			}else if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.Seconds.name) == 0))
			{
				return SchedulingUnit.Seconds;
			}else if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.MilliSeconds.name) == 0))
			{
				return SchedulingUnit.MilliSeconds;
			}else if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.Hours.name) == 0))
			{
				return SchedulingUnit.Hours;
			}else if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.Days.name) == 0))
			{
				return SchedulingUnit.Days;
			}else if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.Weeks.name) == 0))
			{
				return SchedulingUnit.Weeks;
			}else if((strStatus != null) && (strStatus.compareToIgnoreCase(SchedulingUnit.Months.name) == 0))
			{
				return SchedulingUnit.Months;
			}else
			{
				return SchedulingUnit.Minutes;
			}
		}
	    
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	
	public void scheduleJob(String jobName, String groupName, int interval, SheduleManager.SchedulingUnit unit,
			Class jobTOCreate, Map<String, Object> dataMap)  {

		try {

			String triggerName = jobName + "_trigger";

			Trigger trigger = createTrigger(triggerName, groupName, interval, unit);
			JobDetail job = createJob(jobName, groupName, jobTOCreate, dataMap);

			Scheduler metroScheduler = new StdSchedulerFactory().getScheduler();
			metroScheduler.scheduleJob(job, trigger);

		} catch (SchedulerException exp) {
			//throw new QuartzSchedulerException(
			//		"Exception while scheduling quartz job " + jobName + ">>" + exp.getMessage(), exp);
		} catch (Exception exp) {
			//throw new QuartzSchedulerException("Exception while scheduling quartz job" + exp.getMessage(), exp);
		}
	}
	
	
	public Trigger createTrigger(String triggerName, String groupName, int interval,
			SheduleManager.SchedulingUnit unit) {
		// check whether any jobs are running with same name
		

		SimpleScheduleBuilder scheduler = null;

		switch (unit) {
		case Minutes:
			scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(interval).repeatForever();
			break;
		case Seconds:
			scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever();
			break;
		case MilliSeconds:
			scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(interval).repeatForever();
			break;
		case Hours:
			scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(interval).repeatForever();
			break;
		default:
			scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever();
			break;
		}

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName).withSchedule(scheduler)
				.build();
		return trigger;
	}
	
	
	public JobDetail createJob(String jobName, String groupName, Class jobToCreate, Map<String, Object> dataMap) {
		// JobDetail heartbeatJob
		// jobName = jobName + "_job";


		JobDetail job = JobBuilder.newJob(jobToCreate).withIdentity(jobName, groupName).storeDurably(false).build();

		if (dataMap != null) {
			Iterator<String> keyItr = dataMap.keySet().iterator();
			while (keyItr.hasNext()) {
				String key = keyItr.next();
				Object obj = dataMap.get(key);
				job.getJobDataMap().put(key, obj);
			}
		}

		return job;
	}
	
	
	public void unScheduleJob(String jobName, String groupName) {
		try {
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.deleteJob(new JobKey(jobName, groupName));
		} catch (SchedulerException exp) {
			//throw new QuartzSchedulerException("Exception while unscheduling quartz job " + jobName, exp);
		} catch (Exception exp) {
			//throw new QuartzSchedulerException("Exception while unscheduling quartz job", exp);
		}
	}
}

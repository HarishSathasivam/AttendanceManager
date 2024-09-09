package com.nusyn.license.communication.sheduler;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;


public class checkUserAvailable implements InterruptableJob{
	
	
	public void execute(JobExecutionContext context)throws JobExecutionException 
	{
		SheduleJob sheduleJob = new SheduleJob();
		sheduleJob.checkUserAvailable();
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}
	
}

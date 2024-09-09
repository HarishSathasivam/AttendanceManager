package com.nusyn.license.communication.sheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.util.ApplicationStatus;
import com.nusyn.license.util.NusynConstants.CurrentApplicationStatus;


public class SheduleJob {
	
	

	public static void start(UserTO userTO,Date loggedInTime) {
				
		if(userTO != null) {
			LogedInUser logedInUser =new LogedInUser();
			logedInUser.setId(userTO.getUserId());
			logedInUser.setUserName(userTO.getUserName());
			logedInUser.setPassword(userTO.getPassword());
			logedInUser.setLogedInDate(loggedInTime);
			logedInUser.setFrequencyIntervalToLogOut(userTO.getFrequencyIntervalToLogOut());
			logedInUser.setStatus("loggedIn");
			
			try {
				ApplicationStatus.getInstance().setLogedInUser(logedInUser);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Map<String, Object> statusDataMap = new HashMap<String, Object>();
			String statusJobName = "StatusJob";
			String statusGroupName = "StatusJobGtoup";
			
			int statusInterval = 60;
			SheduleManager.getInstance().unScheduleJob(statusJobName, statusGroupName);
	
			SheduleManager.getInstance().scheduleJob(statusJobName, statusGroupName, statusInterval,
					SheduleManager.SchedulingUnit.Seconds, checkUserAvailable.class, statusDataMap);
		}
	}
	
	


	public void checkUserAvailable() {
	    List<LogedInUser> logedInUsers = ApplicationStatus.getInstance().getLogedInUser();

	    if(ApplicationStatus.getInstance().getLogedInUser().size() > 0) {
		    synchronized (logedInUsers) {
		        Date now = new Date();
		        for (LogedInUser logedInUser : logedInUsers) {
		            if (logedInUser.getFrequencyIntervalToLogOut() != null) {
		                long loggedInTimeMillis = now.getTime() - logedInUser.getLogedInDate().getTime();
		                long loggedInMinutes = loggedInTimeMillis / (60 * 1000);
		
		                if (loggedInMinutes >= logedInUser.getFrequencyIntervalToLogOut()) {
		                    logedInUser.setStatus("loggedOut");
		                }
		            }
		        }
		    }
		}else {

			String statusJobName = "StatusJob";
		    String statusGroupName = "StatusJobGroup";          
			SheduleManager.getInstance().unScheduleJob(statusJobName, statusGroupName);
		}


	}
	


	
}

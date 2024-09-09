package com.nusyn.license.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.nusyn.license.communication.sheduler.LogedInUser;
import com.nusyn.license.exception.StartupException;
import com.nusyn.license.util.NusynConstants.CurrentApplicationStatus;

public class ApplicationStatus {

	protected static Logger logger = Logger.getLogger(ApplicationStatus.class.getName());
	// private instance
	private static ApplicationStatus instance = null;
	private CurrentApplicationStatus currentStatus = CurrentApplicationStatus.STARTING_UP;
	private List<String> startupErrorMessages = new ArrayList<String>();
    private List<LogedInUser> logedInUser = Collections.synchronizedList(new ArrayList<>());

	public List<LogedInUser> getLogedInUser() {
		return logedInUser;
	}

	public void setLogedInUser(LogedInUser logedInUser) {
		this.logedInUser.add(logedInUser);
	}

	private ApplicationStatus() {

	}
	
	public static ApplicationStatus getInstance() {
		if (instance == null) {
			instance = new ApplicationStatus();
		}

		return instance;
	}
	
	public void start() throws StartupException
	{
		
	}
	

	



	
	
	public String getStartupErrorMessagesForDisplay() {
		StringBuffer strBuffer = new StringBuffer("");
		if (startupErrorMessages.size() > 0) {
			boolean firstItem = true;
			Iterator<String> messagesItr = startupErrorMessages.iterator();
			while (messagesItr.hasNext()) {
				String message = messagesItr.next();
				if (firstItem) {
					strBuffer.append(message);
					firstItem = false;
				} else {
					strBuffer.append("<br>" + message);
				}
			}
		}
		return strBuffer.toString();
	}
	
	public void clearErrors() {
		startupErrorMessages = new ArrayList<String>();
	}
	
	public CurrentApplicationStatus userId() {
		return currentStatus;
	}

	public void setCurrentStatus(CurrentApplicationStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public CurrentApplicationStatus getCurrentStatus() {
		return currentStatus;
	}
	
	public void addErrorMessage(String message) {
		startupErrorMessages.add(message);
	}

	

	

}

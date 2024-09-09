package com.nusyn.license.communication.sheduler;

import java.util.Date;
import java.util.Set;

import com.nusyn.license.main.access.model.RoleTO;
import com.nusyn.license.main.access.model.UserPropertyTO;

public class LogedInUser {
	
	private static LogedInUser instance = null;

	private String id;
	private String userName;
	private String password;
	private Date LogedInDate;
	private Integer frequencyIntervalToLogOut;
	private String  status;
	
//	public static LogedInUser getInstance()
//	{
//		if(instance == null)
//		{
//			instance = new LogedInUser();
//		}
//		
//		return instance;
//	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLogedInDate() {
		return LogedInDate;
	}
	public void setLogedInDate(Date logedInDate) {
		LogedInDate = logedInDate;
	}
	public Integer getFrequencyIntervalToLogOut() {
		return frequencyIntervalToLogOut;
	}
	public void setFrequencyIntervalToLogOut(Integer frequencyIntervalToLogOut) {
		this.frequencyIntervalToLogOut = frequencyIntervalToLogOut;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}

package com.nusyn.license.main.access.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


public class UserActionTO {

	private Integer id;
	
	
	private Date loggdInTime;
	
	private Date loggedOutTime;
	

    private UserTO user;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}





	public Date getLoggedOutTime() {
		return loggedOutTime;
	}


	public Date getLoggdInTime() {
		return loggdInTime;
	}


	public void setLoggdInTime(Date loggdInTime) {
		this.loggdInTime = loggdInTime;
	}


	public void setLoggedOutTime(Date loggedOutTime) {
		this.loggedOutTime = loggedOutTime;
	}


	public UserTO getUser() {
		return user;
	}


	public void setUser(UserTO user) {
		this.user = user;
	}
}

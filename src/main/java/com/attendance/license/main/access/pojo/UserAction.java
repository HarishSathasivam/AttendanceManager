package com.nusyn.license.main.access.pojo;

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

@Entity
@Table(name = "user_Action")
public class UserAction {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "loggedInTime")
	private Date loggedInTime;
	
	@Column(name = "loggedOutTime")
	private Date loggedOutTime;
	
	
	@ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
	
    private User user;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getLoggedInTime() {
		return loggedInTime;
	}


	public void setLoggedInTime(Date loggedInTime) {
		this.loggedInTime = loggedInTime;
	}


	public Date getLoggedOutTime() {
		return loggedOutTime;
	}


	public void setLoggedOutTime(Date loggedOutTime) {
		this.loggedOutTime = loggedOutTime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}

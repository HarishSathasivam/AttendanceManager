package com.nusyn.license.main.access.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nusyn.license.main.access.model.UserActionTO;

@Entity
@Table(name = "pf_user")
public class User{
	

	@Id
	@Column(name = "pf_id")
	private String userId;
	
	@Column(name = "pf_cluster_id")
	private Integer clusterId;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "userRole")
	private String userRole;

	@Column(name = "loggedInTime")
	private Date loggedInTime;
	
	@Column(name = "loggedOutTime")
	private Date loggedOutTime;
	
	@Column(name = "frequencyIntervalToLogOut")
	private Integer frequencyIntervalToLogOut;

	
	public Integer getFrequencyIntervalToLogOut() {
		return frequencyIntervalToLogOut;
	}

	public void setFrequencyIntervalToLogOut(Integer frequencyIntervalToLogOut) {
		this.frequencyIntervalToLogOut = frequencyIntervalToLogOut;
	}



	@Column(name = "password")
	private String password;
	
	@Column(name = "logedIn_time")
	private Date modifiedDate;
	
	
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
	@JoinColumn(name = "pf_role_id")  
	private Role role;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER ,orphanRemoval = true)
    private Set<UserAction> userActions = new HashSet<>();



	public Integer getClusterId() {
		return clusterId;
	}

	public Set<UserAction> getUserActions() {
		return userActions;
	}

	public void setUserActions(Set<UserAction> userActions) {
		this.userActions = userActions;
	}

	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String  getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
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
}

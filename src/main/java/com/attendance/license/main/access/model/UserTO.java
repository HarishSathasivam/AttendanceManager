package com.nusyn.license.main.access.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.nusyn.license.main.access.pojo.UserAction;
import com.nusyn.license.util.NusynConstants;

public class UserTO {

	private Integer id;
	private Integer clusterId;
	private String userName;
	private String UserRole;
	private Set<UserActionTO> userAction;
	
	public Set<UserActionTO> getUserAction() {
		return userAction;
	}
	
	public void setUserAction(Set<UserActionTO> set) {
		this.userAction = set;
	}
	private Date logedInTime;
	
	public Date getLogedInTime() {
		return logedInTime;
	}
	public void setLogedInTime(Date logedInTime) {
		this.logedInTime = logedInTime;
	}
	public Date getLogedOutTime() {
		return logedOutTime;
	}
	public void setLogedOutTime(Date logedOutTime) {
		this.logedOutTime = logedOutTime;
	}
	private Date logedOutTime;

	private String userId;
	
	private Integer frequencyIntervalToLogOut;

	public Integer getFrequencyIntervalToLogOut() {
		return frequencyIntervalToLogOut;
	}

	public void setFrequencyIntervalToLogOut(Integer frequencyIntervalToLogOut) {
		this.frequencyIntervalToLogOut = frequencyIntervalToLogOut;
	}
	private String password;
	private Set<UserPropertyTO> userProperties;
	public String getUserRole() {
		return UserRole;
	}
	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	private RoleTO role;
	private Date modifiedDate;
	
	public UserTO()
	{
	
	}	
	public UserTO(String userId, String password)
	{
		this.userId = userId;
		this.password = password;
	}
	
	public void addProperty(UserPropertyTO newProperty)
	{
		if(userProperties == null)
		{
			userProperties = new HashSet<UserPropertyTO>();
		}
		//If property exists then update it
		Iterator<UserPropertyTO> propertiesItr = userProperties.iterator();
		boolean foundMatch = false;
		while(propertiesItr.hasNext())
		{
			UserPropertyTO property = propertiesItr.next();
			if((property.getType().compareTo(newProperty.getType()) == 0) && (property.getName().compareTo(newProperty.getName()) == 0))
			{
				userProperties.remove(property);
				userProperties.add(newProperty);
				foundMatch = true;
				break;
			}
		}
		if(!foundMatch)
		{
			userProperties.add(newProperty);
		}
	}
	
	public boolean showFeature(String featureName) {
		boolean allow = false;

		if (role.getAccesses() != null) {
			Iterator<AccessTO> accessItr = role.getAccesses().iterator();
			while (accessItr.hasNext()) {
				AccessTO access = accessItr.next();
				if (access.getFeature().compareToIgnoreCase(featureName) == 0) {
					if ((access.getLevel() != null)
							&& (access.getLevel().compareToIgnoreCase(
									NusynConstants.AccessLevel.NOACCESS.name()) != 0)) {
						allow = true;
					}
					break;
				}
			}
		}
		return allow;
	}
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Set<UserPropertyTO> getUserProperties() {
		return userProperties;
	}
	public void setUserProperties(Set<UserPropertyTO> userProperties) {
		this.userProperties = userProperties;
	}
	public Integer getClusterId() {
		return clusterId;
	}
	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}
	public RoleTO getRole() {
		return role;
	}
	public void setRole(RoleTO role) {
		this.role = role;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getLogeedOutTime() {
		return logedInTime;
	}
	public void setLogeedOutTime(Date logeedOutTime) {
		this.logedInTime = logeedOutTime;
	}
}

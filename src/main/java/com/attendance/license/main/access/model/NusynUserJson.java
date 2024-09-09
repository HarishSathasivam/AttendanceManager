package com.nusyn.license.main.access.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.nusyn.license.util.NusynConstants;

public class NusynUserJson {

	private Integer id;
	private String name = "";
	private String userName = "";
	private String password = "";
	private String email = "";
	private String errorMessage = "";
	private Integer roleId = 0;
	private String roleName = "";
	private Date modifiedDate;
	private String accessGroupName = "";
	
	public NusynUserJson()
	{
		
	}

	public void setUser(UserTO user, HashMap<Integer,String> nodeMap) {
		id = user.getId();
		userName = user.getUserId();
		password = user.getPassword();
		modifiedDate = user.getModifiedDate();
		roleName = NusynConstants.DefaultRole.getRoleFromName(user.getRole().getName()).getName();
		roleId = user.getRole().getId();
		
		//Rest of the fields are in properties
		Set<UserPropertyTO> userProperties = user.getUserProperties();
		if(userProperties != null)
		{
			Iterator<UserPropertyTO> propertiesItr = userProperties.iterator();
			while(propertiesItr.hasNext())
			{
				UserPropertyTO property = propertiesItr.next();
				if((property.getType().compareTo(NusynConstants.USER_DETAILS) == 0) && (property.getName().compareTo(NusynConstants.USER_NAME) == 0))
				{
					//Found name
					name = property.getValue();
				}else if((property.getType().compareTo(NusynConstants.USER_DETAILS) == 0) && (property.getName().compareTo(NusynConstants.USER_EMAIL) == 0))
				{
					//Found email
					email = property.getValue();
				}else
				{
					//Not interested
				}
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {			
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAccessGroupName() {
		return accessGroupName;
	}

	public void setAccessGroupName(String accessGroupName) {
		this.accessGroupName = accessGroupName;
	}
	
}

package com.nusyn.license.main.access.model;

import com.nusyn.license.util.NusynConstants;

public class NusynRoleJson {

	private Integer id;
	private String name;
	private String nameForDisplay = "";
	
	public NusynRoleJson(RoleTO role)
	{
		id = role.getId();
		name = role.getName();
		nameForDisplay = NusynConstants.DefaultRole.getRoleFromName(name).getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameForDisplay() {
		return nameForDisplay;
	}

	public void setNameForDisplay(String nameForDisplay) {
		this.nameForDisplay = nameForDisplay;
	}
}

package com.nusyn.license.main.access.model;

import java.util.Set;

public class RoleTO {

	private Integer id;
	private Integer clusterId;
	private String name;
	private String domain;
	private Set<RolePropertyTO> roleProperties;
	private Set<AccessTO> accesses;
	
	//Getters and Setters
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
	public Set<RolePropertyTO> getRoleProperties() {
		return roleProperties;
	}
	public void setRoleProperties(Set<RolePropertyTO> roleProperties) {
		this.roleProperties = roleProperties;
	}
	public Set<AccessTO> getAccesses() {
		return accesses;
	}
	public void setAccesses(Set<AccessTO> accesses) {
		this.accesses = accesses;
	}
	public Integer getClusterId() {
		return clusterId;
	}
	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}

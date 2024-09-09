package com.nusyn.license.main.access.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pf_role")
public class Role{

	private Integer id;
	private Integer clusterId;
	private String name;
	private String domain;
	private Set<RoleProperty> roleProperties;
	private Set<Access> accesses;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pf_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "pf_cluster_id")
	public Integer getClusterId() {
		return clusterId;
	}
	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}
	@Column(name = "pf_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@OneToMany(mappedBy = "role",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	public Set<RoleProperty> getRoleProperties() {
		return roleProperties;
	}
	public void setRoleProperties(Set<RoleProperty> roleProperties) {
		this.roleProperties = roleProperties;
	}
	
	@OneToMany(mappedBy = "role",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	public Set<Access> getAccesses() {
		return accesses;
	}
	public void setAccesses(Set<Access> accesses) {
		this.accesses = accesses;
	}
}

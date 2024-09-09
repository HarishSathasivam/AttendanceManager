package com.nusyn.license.main.access.model;

public class UserPropertyTO {
	
	private Integer id;
	private String type;
	private String name;
	private String value;
	
	public UserPropertyTO()
	{
		
	}
	
	public UserPropertyTO(String type, String name, String value)
	{
		this.type = type;
		this.name= name;
		this.value = value;
	}
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}

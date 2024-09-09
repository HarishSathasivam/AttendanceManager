package com.nusyn.license.main.access.model;

public class AccessTO {

	private Integer id;
	private String feature;
	private String level;
	
	public AccessTO()
	{
		
	}
	
	public AccessTO(String feature, String level)
	{
		this.feature = feature;
		this.level = level;
	}
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
}

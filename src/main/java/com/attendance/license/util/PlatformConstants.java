package com.nusyn.license.util;

public class PlatformConstants {

	//Date formats
	public static String licenseFileDateFormat = "dd-MM-yyyy";
	public static String generalDateTimeFormat = "dd-MM-yyyy hh:mm:ss";
	public static String planDateFormat = "yyyy-MM-dd";
	public static String planTimeFormat = "HH:mm:ss";
	public static String planDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		
	//License types
	public static enum LicenseType {
	    PERPETUAL("Perpetual"), TRIAL("Trial"), BETA("Beta"), UNKNOWN("Unknown");
	    private String type = "";
	    LicenseType(String type)
	    {
	    	this.type = type;
	    }
	    
	    public String getType()
	    {
	    	return type;
	    }
	}
	
}

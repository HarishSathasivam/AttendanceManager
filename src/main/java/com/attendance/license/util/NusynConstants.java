package com.nusyn.license.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NusynConstants {
	
	//Application name can change
	public static void setApplicationParameters(String appName, String pJavascriptVersion, String pImageVersion, String pCssVersion, String pServerContextName)
	{
		applicationName = appName;
		productFolderPath = "C:\\ProgramData\\Nusyn\\" + applicationName;
		dataFolderPath = "C:\\ProgramData\\Nusyn\\" + applicationName +"\\Data";
		javaScriptVersion = pJavascriptVersion;
		imageVersion = pImageVersion;
		cssVersion = pCssVersion;
		serverContextName = pServerContextName;
	}
	
	public static void setProductDetails(String pDefaultPageTitle,String pFooterProductName)
	{
		defaultPageTitle = pDefaultPageTitle;
		footerProductName = pFooterProductName;
	}
	
	public static String applicationName = "";
	public static String productFolderPath = "";
	public static String dataFolderPath = "";
	public static String productLicenseFileName = "license.xml";
	public static String adminCredentialsFileName = "AdminCredentials.xml";
	
	public static String productFailoverFileName = "failover.xml";
	public static String bootupFileName = "config.xml";
	
	//Url pattern
	public static String serverContextName = "licensecontent";
	
	//Folder names
	public static final String contentFolder = "content";
	public static final String tempFolder = "temp";
	public static final String thumbnailFolder = "thumbnail";
	public static final String reportFolder = "report";
	public static final String uploadFolder = "upload";
	
	//File names
	public static String javaScriptVersion = "sdffsda";
	public static String cssVersion = "dsfasdf";
	public static String imageVersion = "dsfddsfa";
	
	//Product details
	public static String defaultPageTitle = "NusynLicenseManager";
	public static String footerProductName = "NusynLicenseManager";
	public static String releaseNumber = "1.0";
	public static String footerCopyrightDate = "2016-2030";
	
	//Date formats
	public static String licenseFileDateFormat = "dd-MM-yyyy";
	public static String generalDateTimeFormat = "dd-MM-yyyy hh:mm:ss";
	public static String databaseDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	
	//Default users
	public static String administrator_username = "Administrator";
	
	public static final String USER_PERSONALIZE = "Personalize";
	public static final String USER_DETAILS = "UserDetails";
	//Property Names
	public static final String USER_NAME = "Name";
	public static final String USER_EMAIL = "Email";
	
	//Application status
	public static enum CurrentApplicationStatus {
	    STARTING_UP("Starting Up"), SHOW_ADMIN_PASSWORD_PAGE("Admin Password Not Configured"), INITIALIZING("Initializing"), STARTED("Started"), CANNOT_START("Cannot Start"),LANDING_PAGE("Landing page");
	    private String status = "";
	    CurrentApplicationStatus(String status)
	    {
	    	this.status = status;
	    }
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}
	
	//Default roles
	public static enum DefaultRole {
		ADMINISTRATOR("Administrator"), 
		GUEST("Guest");
		
		// Reverse-lookup map for getting a role from name
	    private static final Map<String, DefaultRole> lookup = new ConcurrentHashMap<String, DefaultRole>();
	    
	    private String name = "";
	    DefaultRole(String name)
	    {
	    	this.name = name;
	    }
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
		static {
	        for (DefaultRole role : DefaultRole.values()) {
	            lookup.put(role.name(), role);
	        }
	    }
	    
	    public static DefaultRole getRoleFromName(String rolEnumeName) {
	        return lookup.get(rolEnumeName);
	    }
	}
	
	//Features controlled by roles and access levels
	public static enum Feature {
		WEB_ACCESS("Web Access"), 
		USER("User");
	    private String status = "";
	    Feature(String status)
	    {
	    	this.status = status;
	    }
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}
	
	//Role access levels
	public static enum AccessLevel {
		FULLACCESS("Full Access"), CREATE("Create"), READ("Read"), EDIT("Edit"), DELETE("Delete"), NOACCESS("No Access");
	    private String status = "";
	    AccessLevel(String status)
	    {
	    	this.status = status;
	    }
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}
	
	public static enum UserProperty {
	    SYSTEM("Domain","System"),EDITABLE("Domain","Edit");
	    private String type = "";
	    private String name = "";
	    
	    UserProperty(String type, String name)
	    {
	    	this.type = type;
	    	this.name = name;
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
	}
	
	public static enum LicenseFeature {
		WEB_ACCESS("Web Access"), 
		SETUP("Setup");
	    private String featureName = "";
	    LicenseFeature(String featureName)
	    {
	    	this.featureName = featureName;
	    }
		public String getFeatureName() {
			return featureName;
		}
		public void setFeatureName(String featureName) {
			this.featureName = featureName;
		}
	}
	public static final String ROLE_DOMAIN_NAME = "License";
	public static enum LicenseRole {
		LICENSE_MANAGER("License Manager"),
		LICENSE_ADMIN("License Admin");
	    private String name = "";
	    LicenseRole(String name)
	    {
	    	this.name = name;
	    }
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
}

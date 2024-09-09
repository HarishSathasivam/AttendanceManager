package com.nusyn.license.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nusyn.license.exception.ConfigException;


//Singleton Class
public class AdminCredentials {

	protected static Logger logger = Logger.getLogger(AdminCredentials.class.getName());
	
	//private instance
	private static AdminCredentials instance = null;
	
	//Check if configured properly
	private boolean configured = false;
	private String errorMessage;
	
	//Administrator credentials
	private String adminPassword;
	
	//private constructor
	private AdminCredentials()
	{
		
	}
	
	//static access method
	public static AdminCredentials getInstance()
	{
		if(instance == null)
		{
			instance = new AdminCredentials();
			//Load configuration
			try
			{
				instance.errorMessage = "";
				instance.configured = false;
				try
				{
					instance.loadConfig();
					instance.configured = true;
				}catch(Exception exp)
				{
					instance.configured = false;
				}
			}catch(Exception exp)
			{
				instance.configured = false;
				String message = "Exception lopading Admin Credentials. " + exp.getMessage();
				instance.errorMessage = message;
				logger.error(message,exp);
			}
		}
		return instance;
	}
	
	public void reloadConfig() throws ConfigException
	{
		instance.configured = false;
		configured = false;
		errorMessage = "";
		adminPassword = null;
		try
		{
			instance.loadConfig();
			instance.configured = true;
		}catch(Exception exp)
		{
			instance.configured = false;
		}
		instance.configured = true;
	}
	private void loadConfig() throws ConfigException
	{
		try
		{
			String credentialsFilePath= NusynConstants.productFolderPath + File.separator + "Config" + File.separator + NusynConstants.adminCredentialsFileName;
			File credentialsFile = new File(credentialsFilePath);
			if(credentialsFile.exists() && credentialsFile.length()>0)
			{
				Document document;
				try
				{
					SAXReader read = new SAXReader(false);
					document = read.read(credentialsFile);
					//Root element
					Element root = document.getRootElement();
					//Password
					Element passwordElement = (Element)root.selectSingleNode("password");
					if(passwordElement != null)
					{
						String strPassword = passwordElement.getText();
						if((strPassword != null) && (strPassword.length() > 0))
						{
							this.adminPassword = strPassword;
						}else
						{
							String message = "Value of element password in file " + credentialsFilePath + " is blank. Could not load admin credentials.";
							logger.error(message);
							throw new ConfigException(message,null);
						}
					}else
					{
						String message = "Could not find expected element password in file " + credentialsFilePath + " could not load admin credentials.";
						logger.error(message);
						throw new ConfigException(message,null);
					}
				}catch(DocumentException exp)
				{
					String message = "Could not create xml document from file " + credentialsFilePath + ", Error=" + exp.getMessage();
					logger.error(message);
					throw new ConfigException(message,null);
				}
			}else
			{
				String message = "Admin Credentials XML file " + credentialsFilePath + " could not be found. Admin Credentials cannot be loaded.";
				logger.error(message);
				throw new ConfigException(message,null);
			}
		}catch(ConfigException exp)
		{
			throw exp;
		}catch(Exception exp)
		{
			String message = "Exception loading admin credentials file. >> " + exp.getMessage();
			logger.error(message,exp);
			throw new ConfigException(message,null);
		}
	}
	
	public void updateAdminPassword(String adminPassword) throws ConfigException
	{
		try
		{
			this.setAdminPassword(adminPassword);
			String credentialsFilePath= NusynConstants.productFolderPath + File.separator + "Config" + File.separator + NusynConstants.adminCredentialsFileName;
			File credentialsFile = new File(credentialsFilePath);
			if(credentialsFile.exists())
			{
				//Delete the file and create a new one
				credentialsFile.delete();
				//Check if the file is deleted and then proceed
				if(!credentialsFile.exists())
				{
					
				}else
				{
					throw new ConfigException("Could not update existing file",null);
				}
			}
			
			credentialsFile.createNewFile();
			FileWriter fw = new FileWriter(credentialsFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Generate xml text
			StringBuffer strBuffer = new StringBuffer("");
			strBuffer.append("<CMAdminCredentials>");
				if(adminPassword != null)
				{
					String hashedPassword = NusynEncoder.hashString(adminPassword);
					strBuffer.append("<password><![CDATA[" + hashedPassword + "]]></password>");
				}else
				{
					strBuffer.append("<password></password>");
				}
			strBuffer.append("</CMAdminCredentials>");
			
			bw.write(strBuffer.toString());
			bw.flush();
			bw.close();
		}catch(Exception exp)
		{
			throw new ConfigException(exp.getMessage(),null);
		}
	}
	public void authenticateAdministrator(String providedPassword) throws SecurityException,ConfigException
	{
		if(configured)
		{
			String hashedPassword = NusynEncoder.hashString(providedPassword);
			if(adminPassword.compareTo(hashedPassword) == 0)
			{
				//Authenticated as Administrator
			}else
			{
				//Authentication failed.
				String message = "Invalid UserName or Password provided";
				logger.error(message);
				throw new SecurityException(message,null);
			}
		}else
		{
			String message = "Config has not loaded correcttly. Cannot authenticate Administrator.";
			logger.error(message);
			throw new ConfigException(message,null);
		}
	}
	
	
	
	
	public String toXml()
	{
		StringBuffer strBuffer = new StringBuffer("");
		strBuffer.append("<CMAdminCredentials>");
			if(adminPassword != null)
			{
				strBuffer.append("<password><![CDATA[" + adminPassword + "]]></password>");
			}else
			{
				strBuffer.append("<password></password>");
			}
		strBuffer.append("</CMAdminCredentials>");
		return strBuffer.toString();
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	@Override
	public String toString() {
		return this.toXml();
	}
}

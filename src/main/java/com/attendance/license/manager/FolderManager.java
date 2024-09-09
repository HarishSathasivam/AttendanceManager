package com.nusyn.license.manager;

import java.io.File;

import org.apache.log4j.Logger;

import com.nusyn.license.exception.StartupException;

public class FolderManager {

	protected static Logger logger = Logger.getLogger(FolderManager.class.getName());
	
	private static FolderManager instance = null;
	
	private FolderManager()
	{
		
	}
	
	public static FolderManager getInstance()
	{
		if(instance == null)
		{
			instance = new FolderManager();
		}
		return instance;
	}
	
	public void checkFolder(String folder) throws StartupException
	{
		try
		{
			logger.info("Checking folder " + folder);
			checkFolderExists(folder);
		}catch(Exception exp){
			logger.error("Exception checking folder status " + folder + " >> " + exp.getMessage(),exp);
			throw new StartupException("Exception checking folder status " + folder + " >> " + exp.getMessage(),exp);
		}
	}
	
	public void checkFolders(String dataFolderPath) throws StartupException
	{
		try
		{
			//String contentFolderPath = dataFolderPath + File.separator + "content";
			//logger.info("Checking folder " + contentFolderPath);
			//checkFolderExists(contentFolderPath);
		}catch(Exception exp){
			logger.error("Exception checking folder status " + dataFolderPath + " >> " + exp.getMessage(),exp);
			throw new StartupException("Exception checking network folder status " + dataFolderPath + " >> " + exp.getMessage(),exp);
		}
		try
		{
			logger.info("Checking folder " + dataFolderPath);
			checkFolderExists(dataFolderPath);
		}catch(Exception exp){
			logger.error("Exception checking folder status " + dataFolderPath + " >> " + exp.getMessage(),exp);
			throw new StartupException("Exception checking network folder status " + dataFolderPath + " >> " + exp.getMessage(),exp);
		}
	}
	
	
	private static void checkFolderExists(String folderPath) throws Exception{
		File folder = new File(folderPath);
		if (!folder.exists()) {
			// Create new
			try {
				folder.mkdirs();
				//Check if created
				if (!folder.exists()) 
				{
					logger.info("Created new folder " + folderPath);
				}else
				{
					logger.info("Exception creating new folder " + folderPath);
				}
			} catch (Exception e) {
				throw new Exception("Exception creating folder " + e.getMessage());
			}
		} else {
			logger.info("Folder " + folderPath + " exists.");
		}	
	}
}

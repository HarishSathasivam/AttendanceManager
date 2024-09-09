package com.nusyn.license.startup;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nusyn.license.exception.BootupException;

public class ConfigReaderUtil {
	protected static Logger logger = Logger.getLogger(ConfigReaderUtil.class.getName());
	
	public static void loadConfig(String bootupFilePath) throws BootupException
	{
		try
		{
			File configFile = new File(bootupFilePath);
			if (configFile.exists() && configFile.length() > 0) {
				Document document;
				SAXReader reader = new SAXReader();
				document = reader.read(configFile);

				Element root = document.getRootElement();
				
			}
		}catch (Exception exp) {
			throw new BootupException("Exception while reading config file "+ bootupFilePath, exp);
		}
	}
}

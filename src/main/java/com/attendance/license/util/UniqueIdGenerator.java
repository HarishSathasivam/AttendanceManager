package com.nusyn.license.util;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

public class UniqueIdGenerator {

	protected static Logger logger = Logger.getLogger(UniqueIdGenerator.class.getName());
	
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static String getNewPlanVersion()
	{
		String planVersion = "";
		Date now = new Date();
		planVersion = now.getTime() + "";
		return planVersion; 
	}
	
	public static int getSecurerandomInt()
	{
		int randomValue = 0;
		try
		{
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			randomValue = random.nextInt(100000000);
			return randomValue;
		}catch(Exception exp)
		{
			logger.error("Exception generating random number " + exp.getMessage(), exp);
			Random randomGenerator = new Random();
			randomValue = randomGenerator.nextInt(100000);
		}
		return randomValue;
	}
	
	public static String getRandomDigits(int numberOfDigits)
	{
		String randomDigits = "";
		try
		{
			Date now = new Date();
			long time = now.getTime();
			String strTime = time + "";
			if(numberOfDigits < strTime.length())
			{
				randomDigits = strTime.substring(strTime.length() - numberOfDigits);
			}else
			{
				randomDigits = strTime;
			}
		}catch(Exception exp)
		{
			logger.error("Exception generating random number " + exp.getMessage(), exp);
		}
		return randomDigits;
	}
}

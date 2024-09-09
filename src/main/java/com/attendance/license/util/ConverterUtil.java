package com.nusyn.license.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class ConverterUtil {
	protected static Logger logger = Logger.getLogger(ConverterUtil.class.getName());
	
	public static String dateToString(Date date, String format)
	{
		String strDate = "";
		if(date != null)
		{
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
			strDate = dateFormatter.format(date);
		}
		return strDate;
	}
	
	public static String formatFileSize(double size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	public static Date stringToDate(String strDate, String format) throws Exception
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		Date date = dateFormatter.parse(strDate);
		return date;
	}
	
	public static Date moveToEndOfDay(Date dateToMove) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToMove);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		return cal.getTime();
	}

	
	public static Date stringValidDateToDate(String strDate, String format) throws Exception
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		Date date = dateFormatter.parse(strDate);
		return date;
	}

	public static Date convertUTCTimeToDate(long milliseconds) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliseconds);
		Date date = cal.getTime();
		return date;
	}
	
	public static String validDateToString(Date date, String format) throws Exception
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		String strDate = dateFormatter.format(date);
		return strDate;
	}
	
	public static String trimHealthParametersByNumber(String currentList, String newEntry, int maxEntries)
	{
		String result = "";
		if(currentList != null)
		{
			currentList = currentList + "," + newEntry;
			String[] entries = currentList.split(",");
			int entriesToBeRemoved =  entries.length - maxEntries;
			
			if(entriesToBeRemoved > 0)
			{
				String[] newEntries = Arrays.copyOfRange(entries, entriesToBeRemoved, entries.length);
				StringBuilder sb = new StringBuilder();
				boolean firstItem = true;
				for (String n : newEntries) {
					if(firstItem)
					{
						sb.append(n) ;
						firstItem = false;
					}else
					{
						sb.append("," + n) ;
					}
				}
				result = sb.toString();
			}else
			{
				result = currentList;
			}
		}else
		{
			result = newEntry;
		}
		return result;
	}
	
	public static String trimHealthParametersByFieldLength(String currentList, String newEntry, int fieldLength)
	{
		String result = "";
		if(currentList != null)
		{
			currentList = currentList + "," + newEntry;
			if(currentList.length() > fieldLength)
			{
				//Needs to be trimmed
				int indexofPipe = 0;
				boolean continueToTrim = true;
				while(continueToTrim)
				{
					indexofPipe = currentList.indexOf(",");
					currentList = currentList.substring(indexofPipe + 1);
					if(currentList.length() > fieldLength)
					{
						continueToTrim = true;
					}else
					{
						continueToTrim = false;
					}
				}
			}
			result = currentList;
		}else
		{
			result = newEntry;
		}
		return result;
	}
	
	public static int convertVideoDurationToSeconds(String strDuration) throws Exception
	{
		int duration = 0;
		//First split the duration with :
		String[] durationParts = strDuration.split(":");
		String strHours = durationParts[0].trim();
		String strMinutes = durationParts[1].trim();
		String strSeconds = durationParts[2].trim();
		if(strHours.startsWith("0"))
		{
			strHours = strHours.substring(1);
		}
		if(strMinutes.startsWith("0"))
		{
			strMinutes = strMinutes.substring(1);
		}
		if(strSeconds.startsWith("0"))
		{
			strSeconds = strSeconds.substring(1);
		}
		int hours = Integer.parseInt(strHours);
		int minutes = Integer.parseInt(strMinutes);
		float seconds = Float.parseFloat(strSeconds);
		int intSeconds = Math.round(seconds);
		duration = intSeconds + (minutes * 60) + (hours * 60 * 60);
		return duration;
	}
	
	public static int convertTimeSeconds(String strTime) throws Exception
	{
		int duration = 0;
		//First split the duration with :
		String[] durationParts = strTime.split(":");
		String strHours = durationParts[0].trim();
		String strMinutes = durationParts[1].trim();
		String strSeconds = durationParts[2].trim();
		if(strHours.startsWith("0"))
		{
			strHours = strHours.substring(1);
		}
		if(strMinutes.startsWith("0"))
		{
			strMinutes = strMinutes.substring(1);
		}
		if(strSeconds.startsWith("0"))
		{
			strSeconds = strSeconds.substring(1);
		}
		int hours = Integer.parseInt(strHours);
		int minutes = Integer.parseInt(strMinutes);
		float seconds = Float.parseFloat(strSeconds);
		int intSeconds = Math.round(seconds);
		duration = intSeconds + (minutes * 60) + (hours * 60 * 60);
		return duration;
	}
	
	public static String convertSecondsToString(long time)
	{
		String formattedTime = "";
		long totalMinutes = time / 60;
		long seconds = time%60;
		long hours = totalMinutes / 60;
		long minutes = totalMinutes % 60;
		
		String strHours = ((("" + hours).length() == 2) ? ("" + hours) : "0" + hours);
		String strMinutes = ((("" + minutes).length() == 2) ? ("" + minutes) : "0" + minutes);
		String strSeconds = ((("" + seconds).length() == 2) ? ("" + seconds) : "0" + seconds);
		
		
		formattedTime = strHours + ":" + strMinutes + ":" + strSeconds;
		
		return formattedTime;
	}
	
	public static String convertMillisecondsToString(long time)
	{
		String formattedTime = "";
		long totalSeconds = time/1000;
		long milliseconds = time%1000;
		long totalMinutes = totalSeconds / 60;
		long seconds = totalSeconds%60;
		long hours = totalMinutes / 60;
		long minutes = totalMinutes % 60;
		
		String strHours = ((("" + hours).length() == 2) ? ("" + hours) : "0" + hours);
		String strMinutes = ((("" + minutes).length() == 2) ? ("" + minutes) : "0" + minutes);
		String strSeconds = ((("" + seconds).length() == 2) ? ("" + seconds) : "0" + seconds);
		String strMilliseconds = (new DecimalFormat("000")).format(milliseconds);
		
		formattedTime = strHours + ":" + strMinutes + ":" + strSeconds + "." + strMilliseconds;
		
		return formattedTime;
	}
	
	public static int convertPlaybackTimeToSeconds(Date strPlaybackTime) throws Exception
	{
		int startTime = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(strPlaybackTime);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		int seconds = cal.get(Calendar.SECOND);
		int intSeconds = Math.round(seconds);
		startTime = intSeconds + (minutes * 60) + (hours * 60 * 60);
		return startTime;
	}
}

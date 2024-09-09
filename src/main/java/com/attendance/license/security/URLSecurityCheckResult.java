package com.nusyn.license.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class URLSecurityCheckResult {
	
	private boolean secure = true;
	private List<String> messages = new ArrayList<String>();
	
	public void addMessage(String newMessage)
	{
		messages.add(newMessage);
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public String getMessage() {
		StringBuffer strBuffer = new StringBuffer("");
		Iterator<String> messagesItr = messages.iterator();
		boolean firstItem = true;
		while(messagesItr.hasNext())
		{
			String message = messagesItr.next();
			if(firstItem)
			{
				strBuffer.append(message);
			}else
			{
				strBuffer.append("," + message);
			}
			firstItem = false;
		}
		return strBuffer.toString();
	}

}

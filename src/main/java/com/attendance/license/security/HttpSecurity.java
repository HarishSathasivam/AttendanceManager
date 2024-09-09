package com.nusyn.license.security;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpSecurity {
	
	public static URLSecurityCheckResult isUrlSecure(String urlToCheck)
	{
		URLSecurityCheckResult result = new URLSecurityCheckResult();
		String decodedUrl = "";
		try
		{
			decodedUrl = URLDecoder.decode(urlToCheck, StandardCharsets.UTF_8.toString()).toLowerCase();;
		}catch(Exception exp)
		{
			result.setSecure(false);
			result.addMessage("Exception decoding URL " + exp.getMessage());
		}

		if(decodedUrl.contains("<script>"))
		{
			result.setSecure(false);
			result.addMessage("URL contains malicious/insecure content");
		}
		
		return result;
	}

}

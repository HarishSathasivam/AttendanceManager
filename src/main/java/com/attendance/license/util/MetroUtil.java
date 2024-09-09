package com.nusyn.license.util;

import java.util.Date;

public class MetroUtil {

	public static String generateApplicatioId(){
		return (new Date().getTime())+"";
	}
	
	public static Long generateTransactionId(){
		return (new Date().getTime());
	}
}

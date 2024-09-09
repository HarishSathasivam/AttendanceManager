package com.nusyn.license.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.nusyn.license.exception.SystemUtilException;

public class SystemUtil {

	public static List<String> getIPAddress() throws SystemUtilException{
	
		List<String> ipAddresses = new ArrayList<String>();
		Enumeration<NetworkInterface> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()){
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				while(ee.hasMoreElements()){
					InetAddress i = (InetAddress) ee.nextElement();					
					ipAddresses.add(i.getHostAddress());
				}
			}
		} catch (SocketException exp) {
			throw new SystemUtilException("Exception while retrieveing IP Address " + exp.getMessage(), exp);
		}catch(Exception exp){
			throw new SystemUtilException("Exception while retrieveing IP Address " + exp.getMessage(), exp);
		}
		return ipAddresses;
	}
	
	public static String formatIPAddress(String ipAddress){
		if(ipAddress.isEmpty()){
			return "";
		}else{
			return ipAddress.replace(".", "_");
		}
	}
	
	private static void copyFile(File source, File dest) throws IOException {
	    InputStream inputStream = null;
	    OutputStream outputStream = null;
	    try {
	        inputStream = new FileInputStream(source);
	        outputStream = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = inputStream.read(buffer)) > 0) {
	            outputStream.write(buffer, 0, length);
	        }
	    } finally {
	        inputStream.close();
	        outputStream.close();
	    }
	}
}

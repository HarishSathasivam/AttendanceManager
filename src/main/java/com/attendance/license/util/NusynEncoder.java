package com.nusyn.license.util;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.crypto.codec.Base64;

public class NusynEncoder {
	
	private static String INITIALIZATIO_VECTOR = "AODVNUASDNVVAOVF";
	private static String encryptionKey = "0123456789abcdef";
	protected static Logger logger = Logger.getLogger(NusynEncoder.class.getName());
    
	public static String base64Encode(String token) {
	    byte[] encodedBytes = Base64.encode(token.getBytes());
	    return new String(encodedBytes, Charset.forName("UTF-8"));
	}
	
	public static String base64Decode(String token) {
	    byte[] decodedBytes = Base64.decode(token.getBytes());
	    return new String(decodedBytes, Charset.forName("UTF-8"));
	}
	
	public static String encrypt(String plaintext) {
		String encryptedString = "";
		try
		{
			Security.insertProviderAt(new BouncyCastleProvider(), 1);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(INITIALIZATIO_VECTOR.getBytes("UTF-8")));
	        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
	        encryptedString = encryptedBytes.toString();
		}catch(Exception exp)
		{
			logger.error("Exception encrypting text " + plaintext,exp);
			encryptedString = plaintext;
		}
        return encryptedString;
    }
	
	public static String decrypt(String encryptedString) throws Exception{
		String decryptedString = "";
		try
		{
			byte[] cipherText = encryptedString.getBytes();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(INITIALIZATIO_VECTOR.getBytes("UTF-8")));
		    decryptedString = new String(cipher.doFinal(cipherText),"UTF-8");
		}catch(Exception exp)
		{
			logger.error("Exception encrypting text " + encryptedString,exp);
			decryptedString = encryptedString;
		}
	    return decryptedString;
	}
	
	public static final String hashString(String stringToHash)
	{
		//Hashing using MD5, SHA-1 or SHA-256
		String hashedString = "";
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(stringToHash.getBytes(), 0, stringToHash.length());
			hashedString = new BigInteger(1, digest.digest()).toString(16);
		}catch(Exception exp)
		{
			logger.error("exception hashing string " + exp.getMessage(),exp);
		}
		return hashedString;
	}
	
	public static final String hashStringWithKey(String stringToHash, String key)
	{
		//Hashing using MD5, SHA-1 or SHA-256
		String hashedString = "";
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			stringToHash = stringToHash + key;
			digest.update(stringToHash.getBytes(), 0, stringToHash.length());
			hashedString = new BigInteger(1, digest.digest()).toString(16);
		}catch(Exception exp)
		{
			logger.error("exception hashing string " + exp.getMessage(),exp);
		}
		return hashedString;
	}
	
	public static final String hashFile(String fileToHash, String key)
	{
		String hashedString = "";
		FileInputStream fis = null;
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			fis = new FileInputStream(fileToHash);
			byte[] dataBytes = new byte[1024];
			int nread = 0;
		    while ((nread = fis.read(dataBytes)) != -1) {
		    	digest.update(dataBytes, 0, nread);
		    };
		    
			//Append the key
		    digest.update(key.getBytes(), 0, key.length());
		    hashedString = new BigInteger(1, digest.digest()).toString(16);
		}catch(Exception exp)
		{
			logger.error("exception hashing file " + fileToHash + " >> " + exp.getMessage(),exp);
		}finally
		{
			if(fis != null)
			{
				try
				{
					fis.close();
				}catch(Exception exp)
				{
					logger.error("Exception closing inputstream after hashing file " + fileToHash,exp);
				}
				
			}
		}
		return hashedString;
	}
}

package com.nusyn.license.exception;

public class PlatformException extends Exception{
	
	private String message;
	static final long serialVersionUID = 1L;

	public PlatformException(String message,Exception innerException) {
		super(innerException);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

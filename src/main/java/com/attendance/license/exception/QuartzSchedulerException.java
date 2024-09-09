package com.nusyn.license.exception;

public class QuartzSchedulerException extends Exception{
	
	private String message;
	static final long serialVersionUID = 1L;

	public QuartzSchedulerException(String message,Exception innerException) {
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

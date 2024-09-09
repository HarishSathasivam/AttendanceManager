package com.nusyn.license.exception;

public class DaoException extends Exception{
	
	private String message;
	private String innerMessage;
	static final long serialVersionUID = 2L;

	public DaoException(String message,Exception innerException) {
		super(innerException);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInnerMessage() {
		return innerMessage;
	}

	public void setInnerMessage(String innerMessage) {
		this.innerMessage = innerMessage;
	}
	
}

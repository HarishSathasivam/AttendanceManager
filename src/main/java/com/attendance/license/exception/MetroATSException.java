package com.nusyn.license.exception;

public class MetroATSException extends Exception{

	private String message;
	private String innerMessage;
	static final long serialVersionUID = 3L;

	public MetroATSException(String message,Exception innerException) {
		super(innerException);
		this.message = message;
	}

	@Override
	public String toString() {
		return "BootupException [message=" + message + ", innerMessage="
				+ innerMessage + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
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

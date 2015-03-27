package com.nepse.exception;

public class CannotConnectToDataServer extends RuntimeException {
	private static final long serialVersionUID = 1234234324234L;
	
	
	public CannotConnectToDataServer(String message){
		super(message);
	}

	public CannotConnectToDataServer(String message, Exception cause){
		super(message, cause);
		
	}
}

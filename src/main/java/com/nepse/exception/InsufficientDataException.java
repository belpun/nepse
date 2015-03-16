package com.nepse.exception;

public class InsufficientDataException extends RuntimeException{

	private static final long serialVersionUID = 345451L;
	
	public InsufficientDataException(String msg){
		super(msg);
	}
}

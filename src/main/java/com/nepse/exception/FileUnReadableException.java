package com.nepse.exception;

public class FileUnReadableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 345451L;
	
	public FileUnReadableException(String msg){
		super(msg);
	}
}

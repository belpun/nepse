package com.nepse.exception;


public class CompanyDataUpdateException extends RuntimeException{

	public CompanyDataUpdateException(String errorMsg) {
		super(errorMsg);
	}

	public CompanyDataUpdateException(String errorMsg, Throwable e) {
		super(errorMsg, e);
	}

	private static final long serialVersionUID = -4595451098075019775L;

}

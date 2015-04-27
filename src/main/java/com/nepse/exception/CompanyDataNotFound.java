package com.nepse.exception;

public class CompanyDataNotFound extends RuntimeException{

	private static final long serialVersionUID = -188815700520863383L;
	
	public CompanyDataNotFound(String msg){
		super(msg);
	}
	
	public CompanyDataNotFound(Throwable th) {
		super(th);
	}

}

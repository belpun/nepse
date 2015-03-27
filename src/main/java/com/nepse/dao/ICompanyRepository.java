package com.nepse.dao;

import java.util.Date;

import com.nepse.domain.Company;
import com.nepse.domain.CompanyData;

public interface ICompanyRepository {
	
	public Company getCompanyBySymbol(String symbol);
	
	public CompanyData getCompanyData(String symbol, Date date);

}

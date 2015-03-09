package com.nepse.dao;

import com.nepse.domain.Company;

public interface ICompanyRepository {
	
	public Company getCompanyBySymbol(String symbol);

}

package com.nepse.service.data;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Map;

import com.nepse.domain.CompanyData;

public interface ICompanyDataProvider {

	Map<Date, CompanyData> getCompanyData(String companySymbol)
			throws FileNotFoundException;

}

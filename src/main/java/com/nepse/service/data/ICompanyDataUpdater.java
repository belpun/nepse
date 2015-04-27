package com.nepse.service.data;

import com.nepse.exception.CompanyDataUpdateException;

public interface ICompanyDataUpdater {

	void updateLatestData(String symbol) throws CompanyDataUpdateException;

}

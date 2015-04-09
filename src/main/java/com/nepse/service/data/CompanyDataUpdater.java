package com.nepse.service.data;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.nepse.data.service.IWriteArchivedDataToFileFromWebService;
import com.nepse.exception.CompanyDataUpdateException;

public class CompanyDataUpdater implements ICompanyDataUpdater{
	
	@Qualifier("jobLauncher")
	private JobLauncher jobLauncher;
	
	@Qualifier("writeOpeningPriceFromFileToDbJob")
	private Job job;
	
	@Autowired
	private IWriteArchivedDataToFileFromWebService writeArchivedDataToFileFromWebService;
	
	@Override
	public void updateLatestData(String symbol) throws CompanyDataUpdateException{
		
		writeArchivedDataToFileFromWebService.update(symbol);
		
	}
	
	public boolean updateCompanyDetailsPrice(String symbol){
		// get the latest data from csv
	
		
		return false;
		
	}
	
	
	public boolean updateOpeningPrice(String symbol){
		return false;
	}

}

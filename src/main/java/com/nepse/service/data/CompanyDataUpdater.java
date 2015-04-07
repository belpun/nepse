package com.nepse.service.data;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CompanyDataUpdater implements ICompanyDataUpdater{
	
	@Qualifier("jobLauncher")
	private JobLauncher jobLauncher;
	
	@Qualifier("writeOpeningPriceFromFileToDbJob")
	private Job job;
	
	@Override
	public void updateLatestData(String symbol) {
		
		
	 
//		try {
//	 
//			JobExecution execution = jobLauncher.run(job, new JobParameters());
//			System.out.println("Exit Status : " + execution.getStatus());
//			if(execution.getStatus().equals("FAILED")) {
//				
//			}
		
		updateOpeningPrice(symbol);
		
	}
	
	
	public void updateOpeningPrice(String symbol){
		
		
	}

}

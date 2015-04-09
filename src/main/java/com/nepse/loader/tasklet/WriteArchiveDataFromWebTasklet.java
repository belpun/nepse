package com.nepse.loader.tasklet;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.data.service.IWriteArchivedDataToFileFromWebService;

public class WriteArchiveDataFromWebTasklet implements Tasklet{
	@Autowired
	public JDBCCompanyRepository companyRepository;
	
	@Autowired
	public IWriteArchivedDataToFileFromWebService writeArchivedDataToFileFromWebService;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		Map<String, String> companySymbol = companyRepository
				.getCompanySymbol();
		// Map<String, String> companySymbol = new HashMap<String, String>();
		// companySymbol.put("ADBL", "for test");

		for (String symbol : companySymbol.keySet()) {

			System.out.println("processing company " + symbol);
			
			boolean skippedWriting = writeArchivedDataToFileFromWebService.update(symbol);
			
			if(!skippedWriting) {
				Thread.currentThread().sleep(20000);
			}
		}

		System.out.println("stop");

		return RepeatStatus.FINISHED;
	}

	public JDBCCompanyRepository getCompanyRepository() {
		return companyRepository;
	}

	public void setCompanyRepository(JDBCCompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

}
package com.nepse.loader.tasklet;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.data.service.OpeningPriceService;

public class WriteOpeningPriceToFileFromWebTasklet implements Tasklet{
	
	@Autowired
	private final OpeningPriceService writeOpeningPriceToFileFromWebService = null;
	
	@Autowired
	public JDBCCompanyRepository companyRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		Map<String, String> companySymbol = companyRepository
				.getCompanySymbol();

		for(String symbol : companySymbol.keySet()) {
			
			boolean skippedWriting = writeOpeningPriceToFileFromWebService.updateOpeningPriceFromWebToFile(symbol);

			if(!skippedWriting && companySymbol.size() > 1) {
				Thread.currentThread().sleep(5000);
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
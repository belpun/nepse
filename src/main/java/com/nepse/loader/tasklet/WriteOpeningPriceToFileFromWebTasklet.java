package com.nepse.loader.tasklet;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.data.service.WriteOpeningPriceToFileFromWebService;

public class WriteOpeningPriceToFileFromWebTasklet implements Tasklet{
	
	@Autowired
	private final WriteOpeningPriceToFileFromWebService writeOpeningPriceToFileFromWebService = null;
	
	@Autowired
	public JDBCCompanyRepository companyRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		Map<String, String> companySymbol = companyRepository
				.getCompanySymbol();

		writeOpeningPriceToFileFromWebService.update(companySymbol.keySet());

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
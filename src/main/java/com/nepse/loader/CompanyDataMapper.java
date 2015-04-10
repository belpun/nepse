package com.nepse.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

public class CompanyDataMapper implements FieldSetMapper<CompanyDataForm>{
	private static final Logger logger = LoggerFactory.getLogger(CompanyDataMapper.class);
	private String symbol = null;

	private MultiResourceItemReader reader;

	@Override
	public CompanyDataForm mapFieldSet(FieldSet fieldSet) throws BindException {
		
		String companySymbol = null;
		if (reader != null) {
			Resource currentResource = reader.getCurrentResource();
			String filename = currentResource.getFilename();
			String partFileName = filename.split("-")[1];
			companySymbol = partFileName.split("\\.")[0];
			
		} else {
			companySymbol = symbol;
		}
		
		if (reader == null && symbol == null) {
			String errorMsg = "company Symbol is not provided so this file cannot be saved";
			logger.error(errorMsg);
			throw new IllegalStateException(errorMsg);
		}
		System.out.println(companySymbol);
		
		String date = fieldSet.readString("Date");
		String noOfTransaction = fieldSet.readString("No.of Transaction");
		String totalShares = fieldSet.readString("Total Share");
		String amount = fieldSet.readString("Amount");
		String maxPrice = fieldSet.readString("Max.price");
		String minPrice = fieldSet.readString("Min.price");
		String closingPrice = fieldSet.readString("Closing Price");
		
		return new CompanyDataForm(date, noOfTransaction, totalShares, amount, maxPrice, minPrice, closingPrice, companySymbol);
	}
	
	public MultiResourceItemReader getReader() {
		return reader;
	}

	public void setReader(MultiResourceItemReader object) {
		this.reader = object;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}

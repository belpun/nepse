package com.nepse.loader;

import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

public class CompanyDataMapper implements FieldSetMapper<CompanyDataForm>{
	
	private MultiResourceItemReader reader;

	@Override
	public CompanyDataForm mapFieldSet(FieldSet fieldSet) throws BindException {
		Resource currentResource = reader.getCurrentResource();
		String filename = currentResource.getFilename();
		String partFileName = filename.split("-")[1];
		String companySymbol = partFileName.split("\\.")[0];
		System.out.println(companySymbol);
		
		\
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
	
}

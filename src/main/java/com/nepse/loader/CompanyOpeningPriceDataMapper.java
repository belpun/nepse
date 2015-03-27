package com.nepse.loader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

import com.nepse.dao.ICompanyRepository;
import com.nepse.dao.IGenericRepository;
import com.nepse.domain.CompanyData;

public class CompanyOpeningPriceDataMapper implements FieldSetMapper<CompanyData>{
	
	@Autowired
	private ICompanyRepository companyRepository;
	
	@Autowired
	private IGenericRepository genericRepository;
	
	private MultiResourceItemReader reader;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public CompanyData mapFieldSet(FieldSet fieldSet) throws BindException {
		Resource currentResource = reader.getCurrentResource();
		String filename = currentResource.getFilename();
		String partFileName = filename.split("-")[1];
		String companySymbol = partFileName.split("\\.")[0];
		System.out.println(companySymbol);
		
		
		String date = fieldSet.readString("Date");
		
		String closingPrice = fieldSet.readString("Opening Price");

		Date closingPriceDate;
		try {
			closingPriceDate = sdf.parse(closingPrice);
			CompanyData companyData = companyRepository.getCompanyData(companySymbol, closingPriceDate);
			
			if(companyData != null) {
				return companyData;
			} else {
				throw new RuntimeException("Cannot find the Company");
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot parse the company Data Date");
		}
	}
	
	public MultiResourceItemReader getReader() {
		return reader;
	}

	public void setReader(MultiResourceItemReader object) {
		this.reader = object;
	}
	
	public ICompanyRepository getCompanyRepository() {
		return companyRepository;
	}

	public void setCompanyRepository(ICompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
}

package com.nepse.loader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

import com.nepse.dao.ICompanyRepository;
import com.nepse.dao.IGenericRepository;
import com.nepse.domain.CompanyData;
import com.nepse.exception.CompanyDataNotFound;

public class CompanyOpeningPriceDataMapper implements FieldSetMapper<CompanyData>{
	
	Logger logger = LoggerFactory.getLogger(CompanyOpeningPriceDataMapper.class);
	
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
		logger.info("Reading the opening price file for {} ",companySymbol);
		
		String date = fieldSet.readString("Date");
		
		String openingPrice = fieldSet.readString("Open");
		
		Date closingPriceDate;
		try {
			closingPriceDate = sdf.parse(date);
			CompanyData companyData = companyRepository.getCompanyData(companySymbol, closingPriceDate);
			
			if(companyData != null) {
				companyData.setOpenPrice(openingPrice);
				return companyData;
			} else {
				String error = "Company Data not found for " + companySymbol + " for date " + closingPriceDate;
				logger.error(error);
				throw new CompanyDataNotFound(error);
			}
		
		} catch (ParseException e) {
			String error = "Cannot parse the company Data for company:" + companySymbol + ". Provided date : " + date + " and openingPrice : " + openingPrice ;
			logger.error(error);
			throw new CompanyDataNotFound(e);
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

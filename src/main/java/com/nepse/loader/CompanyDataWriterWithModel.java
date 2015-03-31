package com.nepse.loader;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.dao.ICompanyRepository;
import com.nepse.dao.IGenericRepository;
import com.nepse.domain.CompanyData;
import com.nepse.exception.CompanyDataNotFound;


public class CompanyDataWriterWithModel implements ItemWriter<CompanyData> {
	
	@Autowired
	private final IGenericRepository genericRepository = null;
	
	@Autowired
	private final ICompanyRepository companyRepository = null;

	@Override
	public void write(List<? extends CompanyData> items) throws Exception {
		for (CompanyData entity : items) {
			if(entity != null) {
				genericRepository.update(entity);
			} else {
				throw new CompanyDataNotFound("Company data not found for : ");
			}
			
		}
		
	}

}

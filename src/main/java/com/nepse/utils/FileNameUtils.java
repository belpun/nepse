package com.nepse.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nepse.config.properties.IPropertiesRepository;

@Service
public class FileNameUtils {
	
	@Autowired
	@Qualifier("customerPropertiesRepository")
	private final IPropertiesRepository customerPropertiesRepository = null;
	
	private static final String ARCHIEVED_FILE_LOCATION = "src" + File.separator + "main"
			+ File.separator + "resources";
	private static final String ARCHIEVED_PREFIX = "companyData-";
	private static final String ARCHIEVED_SUFIX = ".csv";

	
	private final String OPENING_FILE_LOCATION = "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator + "openingPrice";
	private final String OPENING_PREFIX = "openingPriceCompanyData-";
	private final String OPENING_SUFIX = ".csv";
	
	public String archivedFileName(String companySymbol){
		
		String fileName = ARCHIEVED_PREFIX + companySymbol + ARCHIEVED_SUFIX;

		return customerPropertiesRepository.get("archivedFile.url", ".") + File.separator + ARCHIEVED_FILE_LOCATION + File.separator + fileName;
	}
	
	
	public String openingFileName(String companySymbol){
		
		String fileName = OPENING_PREFIX + companySymbol + OPENING_SUFIX;

		return customerPropertiesRepository.get("openingFile.url", ".") + File.separator + OPENING_FILE_LOCATION + File.separator + fileName;
	}
	
}

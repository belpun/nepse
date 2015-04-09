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
	
	private static final String FILE_LOCATION = "src" + File.separator + "main"
			+ File.separator + "resources";
	private static final String PREFIX = "companyData-";
	private static final String SUFIX = ".csv";

	
	public String archivedFileName(String companySymbol){
		
		String fileName = PREFIX + companySymbol + SUFIX;

		return customerPropertiesRepository.get("archivedFile.url", ".") + File.separator + FILE_LOCATION + File.separator + fileName;
	}
	
	public String openingPriceFileName(String companySymbol) {
		
		return null;
	}
	
	
}

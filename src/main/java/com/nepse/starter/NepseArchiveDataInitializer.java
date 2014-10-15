package com.nepse.starter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nepse.config.NepseDefinition;
import com.nepse.data.CompanyData;
import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.writer.CsvWriter;

public class NepseArchiveDataInitializer {
	
	NepseDataExtractorFromWeb extractor = new NepseDataExtractorFromWeb();
	
	CsvWriter writer = new CsvWriter();
	
	public void initializeFileForAll() {
		Map<String, String> companySymbol = NepseDefinition.getInstance().getCompanySymbol();
		
		Map<String, Map<Date, CompanyData>> companyData = new HashMap<String, Map<Date, CompanyData>>();

		for(String symbol : companySymbol.keySet()) {
			
			companyData.put(symbol, new HashMap<Date, CompanyData>());
			
			File file = new File("src\\main\\resources\\"+symbol+".csv");	
			
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		Map<Date, List<CompanyData>> extractArchivedData = extractor.extractArchivedData("2014-09-13", "2014-10-13");
		
//		writer.writeDataPerCompanyToCsvFile(companiesDatas, location, fileName, name, dateInMilliSecond);
		
//		for(Date date : extractArchivedData.keySet()) {
//			
//			List<CompanyData> listOfDay = extractArchivedData.get(date);
//			
//			for(CompanyData company : listOfDay) {
//				String symbol = findSymbolFromCompanyName(company.getName());
//				
//				if(symbol != null ) {
//					Map<Date, CompanyData> archivedCompanyData = companyData.get(symbol);
//					archivedCompanyData.put(date, company);
//				}
//				
//			}
//			
//		}
		System.out.println("stop");
	}
	
	private String findSymbolFromCompanyName (String companyName){
		Map<String, String> companySymbol = NepseDefinition.getInstance().getCompanySymbol();
		
		for(String key : companySymbol.keySet()) {
			if(companySymbol.get(key).equals(companyName)){
				return key;
			}
		}
		
		return null;
	}

}

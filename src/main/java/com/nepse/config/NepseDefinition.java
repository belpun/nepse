package com.nepse.config;

import java.util.Collections;
import java.util.Map;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.loader.NepseDataLoaderFromFile;

public class NepseDefinition {
	
	private final NepseDataExtractorFromWeb nepseDataExtractor;
	
	private static NepseDefinition nepseDefinition;
	
	private static NepseDataLoaderFromFile dataLoader;
	
	private static final String COMPANY_SYMBOL_FILE = "src\\main\\resources\\companySymbol.csv";
	
	private final Map<String, String> companySymbol;
	
//	private Map<String, Object> cache;
	
	private NepseDefinition(){
		dataLoader = new NepseDataLoaderFromFile();
		
		companySymbol = dataLoader.getCompanySymbol(COMPANY_SYMBOL_FILE);
		
		nepseDataExtractor = new NepseDataExtractorFromWeb();
	}
	
	
	public static NepseDefinition getInstance(){
		
		if (nepseDefinition == null){
			
			nepseDefinition = new NepseDefinition();
					
		}
		
		return nepseDefinition;
		
		
	}
	
	public Map<String, String> getCompanySymbol() {
		return Collections.unmodifiableMap(companySymbol);
	}
	
	public void loadAllData(String date) {
		
	
		
	}
	
	
//	public Map<String, String> compnaySymbol (){
//		NepseDataLoader nepseDataLoader = null;
//		if (cache.get("companySymbol") == null) {
//			nepseDataLoader = new NepseDataLoader();
//			cache.put("companySymbol", nepseDataLoader);
//		}
//		
//		nepseDataLoader = (NepseDataLoader)cache.get("companySymbol");
//		
//		return nepseDataLoader.getCompanySymbol(p);
//	}

}

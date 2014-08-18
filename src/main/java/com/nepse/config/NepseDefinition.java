package com.nepse.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.nepse.loader.NepseDataLoader;

public class NepseDefinition {
	private static NepseDefinition nepseDefinition;
	
	private static NepseDataLoader dataLoader;
	
	private static final String COMPANY_SYMBOL_FILE = "companySymbol.csv";
	
	private Map<String, String> compnaySymbol;
	
	public Map<String, String> getCompnaySymbol() {
		return Collections.unmodifiableMap(compnaySymbol);
	}


//	private Map<String, Object> cache;
	
	private NepseDefinition(){
		dataLoader = new NepseDataLoader();
		
		dataLoader.getCompanySymbol(COMPANY_SYMBOL_FILE);
	}
	
	
	public static NepseDefinition getInstance(){
		
		if (nepseDefinition == null){
			
			nepseDefinition = new NepseDefinition();
					
		}
		
		return nepseDefinition;
		
		
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

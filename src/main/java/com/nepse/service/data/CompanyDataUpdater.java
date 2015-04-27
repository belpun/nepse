package com.nepse.service.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.data.service.IArchivedDataService;
import com.nepse.data.service.IOpeningPriceService;
import com.nepse.exception.CompanyDataUpdateException;

public class CompanyDataUpdater implements ICompanyDataUpdater{
	
	@Autowired
	private final IArchivedDataService archievedDataService = null;
	
	@Autowired
	private final IOpeningPriceService openingPriceService = null;
	
	@Override
	public void updateLatestData(String symbol) throws CompanyDataUpdateException{
		updateCompanyDetailsPrice(symbol);
		updateOpeningPrice(symbol);
	}
	
	private void updateCompanyDetailsPrice(String symbol){
			archievedDataService.updateArchievedDataFromWebToFile(symbol);
			archievedDataService.updateDbFromFile(symbol);
	}
	
	private void updateOpeningPrice(String symbol){
			
		openingPriceService.updateOpeningPriceFromWebToFile(symbol);
		openingPriceService.updateOpeningPriceFromFileToDb(symbol);
	}

}



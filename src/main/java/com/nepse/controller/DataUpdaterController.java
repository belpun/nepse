package com.nepse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nepse.exception.CompanyDataUpdateException;
import com.nepse.service.data.ICompanyDataUpdater;

@Controller
public class DataUpdaterController {
	
	@Autowired
	private ICompanyDataUpdater companyDataUpdater;

	@RequestMapping(value = "/company/{companySymbol}/update", method = RequestMethod.GET)
	public @ResponseBody PriceUpdateResult updateCompanyData(
			@PathVariable String companySymbol) {
		PriceUpdateResult result = new PriceUpdateResult(true, "");
		try {
			companyDataUpdater.updateLatestData(companySymbol);
		} catch (CompanyDataUpdateException e) {
			result.setSuccessful(false);
			result.setErrorMessage("Internal error in updating the company data");
		}
		return result;
	}
}

class PriceUpdateResult {
	private boolean successful;
	private String errorMessage;
	public boolean isSuccessful() {
		return successful;
	}
	
	public PriceUpdateResult(boolean successful, String errorMessage) {
		super();
		this.successful = successful;
		this.errorMessage = errorMessage;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
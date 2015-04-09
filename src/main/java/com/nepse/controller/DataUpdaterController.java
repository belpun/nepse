package com.nepse.controller;

import java.util.List;

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
	public @ResponseBody List<Object[]> companyClosingPrice(
			@PathVariable String companySymbol) {

		try {
			companyDataUpdater.updateLatestData(companySymbol);
		} catch (CompanyDataUpdateException e) {
		}
		return null;
	}

}

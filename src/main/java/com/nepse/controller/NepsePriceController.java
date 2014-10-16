package com.nepse.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nepse.data.CompanyData;
import com.nepse.service.data.CsvCompanyDataProvider;
import com.nepse.service.data.ICompanyDataProvider;

@Controller
public class NepsePriceController {

	ICompanyDataProvider DataProvider = new CsvCompanyDataProvider();
	
	public NepsePriceController() {
		System.out.println("created the controller");
	}
	
	@RequestMapping(value = "/service/test/{symbol}", method = RequestMethod.GET)
	public @ResponseBody List<String> getJsonCompanyData(@PathVariable String symbol, HttpServletRequest request) {
		
		Map<Date, CompanyData> companyData =null ; 
		try {
			companyData = DataProvider.getCompanyData(symbol);
		} catch (FileNotFoundException e) {
			return Collections.EMPTY_LIST;
		}
		
		if (companyData!= null) {
			return convertToJsonFormat(companyData);
		}
		
		return Collections.EMPTY_LIST;
	}
	
	public List<String> convertToJsonFormat(Map<Date, CompanyData> companyDatas){
		List<String> datas = new ArrayList<String>();
		
		
		for( Date key : companyDatas.keySet()){
			CompanyData companyData = companyDatas.get(key);
		StringBuilder value = new StringBuilder("" + key.getTime());
		value.append(",").append(companyData.getClosingPrice())
		.append(",").append(companyData.getHigh())
		.append(",").append(companyData.getLow())
		.append(",").append(companyData.getClosingPrice());
		datas.add(value.toString());
		}
		return datas;
	}
		
	}

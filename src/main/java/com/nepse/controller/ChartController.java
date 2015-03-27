package com.nepse.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nepse.analysis.TechnicalAnalysisService;
import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.domain.CompanyData;

@Controller
public class ChartController {
	
	@Autowired
	private JDBCCompanyRepository companyRepository;
	
	@Autowired
	private TechnicalAnalysisService technicalAnalysisService;
	
	 @RequestMapping(value = "/ftl/chart", method = RequestMethod.GET)
	    public String index(@ModelAttribute("model") ModelMap model) {
	 
	        model.addAttribute("company", "MEGA");
	 
	        return "chart";
	    }
	 
	 @RequestMapping(value = "/company/{companySymbol}/companyClosingPrice", method = RequestMethod.GET)
	 public @ResponseBody List<Object[]> companyClosingPrice(@PathVariable String companySymbol) {
		 
		Map<Long, Double> closingPrice = companyRepository.getClosingPrice(companySymbol);
		 return  createJson(closingPrice);
	 }
	 
	 @RequestMapping(value = "/company/{companySymbol}/simpleMovingAverage", method = RequestMethod.GET)
	 public @ResponseBody List<Object[]> simpleMovinAverage(@PathVariable String companySymbol) {
		 
		 Map<Long, Double> closingPrice = technicalAnalysisService.simpleMovingAverage(companySymbol);
		 
		 return createJson(closingPrice);
	 }

	@RequestMapping(value = "/company/{companySymbol}/exponentialMovingAverage", method = RequestMethod.GET)
	public @ResponseBody List<Object[]> exponentialMovinAverage(
			@PathVariable String companySymbol) {

		Map<Long, Double> closingPrice = technicalAnalysisService
				.exponentialMovingAverage(companySymbol);

		return createJson(closingPrice);
	}

	private List<Object[]> createJson(Map<Long, Double> closingPrice) {
		List<Object[]> prices = new ArrayList<Object[]>();
		 for (Entry<Long, Double> temp : closingPrice.entrySet()){
			 Object[] entry = new Object[2];
			 entry[0] = temp.getKey();
			 entry[1] = temp.getValue();
			 prices.add(entry);
			 
		 }
		return prices;
	}
	 
	 @RequestMapping(value = "/company/{companySymbol}/rsiCalculation", method = RequestMethod.GET)
	 public @ResponseBody List<Object[]> rsiCalculation(@PathVariable String companySymbol) {
		 
		Map<Long, Double> rsiMap = technicalAnalysisService.rsiWith14DaysCalculation(companySymbol);
		
		List<Object[]> prices = new ArrayList<Object[]>();
		for (Entry<Long, Double> temp : rsiMap.entrySet()){
			Object[] entry = new Object[2];
			entry[0] = temp.getKey();
			
			 BigDecimal bd = new BigDecimal(temp.getValue());
			 bd = bd.setScale(2, RoundingMode.HALF_UP);
			
			entry[1] =  bd.doubleValue();
			prices.add(entry);
			
		}
		 return prices;
	 }
	 
	 @RequestMapping(value = "/company/{companySymbol}/candleStickChart", method = RequestMethod.GET)
	 public @ResponseBody List<Object[]> candleStickChart(@PathVariable String companySymbol) {
		 
		 Map<Long, CompanyData> companyDatas = companyRepository.getCompanyData(companySymbol);
		 
			List<Object[]> prices = new ArrayList<Object[]>();
			for (Entry<Long, CompanyData> keyTime : companyDatas.entrySet()){
				Object[] entry = new Object[5];
				
				entry[0] = keyTime.getKey();
				
				CompanyData companyData = companyDatas.get(keyTime);
				//open high low close
				
				 BigDecimal open = new BigDecimal(companyData.getOpenPrice());
				 open = open.setScale(2, RoundingMode.HALF_UP);
				 entry[1] =  open.doubleValue();
				 
				 BigDecimal high = new BigDecimal(companyData.getHigh());
				 high = high.setScale(2, RoundingMode.HALF_UP);
				 entry[2] =  high.doubleValue();
				 
				 BigDecimal low = new BigDecimal(companyData.getLow());
				 low = low.setScale(2, RoundingMode.HALF_UP);
				 entry[3] =  low.doubleValue();
				 
				 BigDecimal close = new BigDecimal(companyData.getClosingPrice());
				 close = close.setScale(2, RoundingMode.HALF_UP);
				 entry[4] =  close.doubleValue();
				
				prices.add(entry);
			}
		 
		 return prices;
	 }

}

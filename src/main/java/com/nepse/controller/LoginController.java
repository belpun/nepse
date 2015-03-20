package com.nepse.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nepse.analysis.TechnicalAnalysisService;
import com.nepse.dao.JDBCCompanyRepository;

@Controller
public class LoginController {
	
	@Autowired
	private JDBCCompanyRepository companyRepository;
	
	@Autowired
	private TechnicalAnalysisService technicalAnalysisService;
	
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String indexPage(@ModelAttribute("model") ModelMap model) {
	 
	        return "/index";
	    }
	
	 @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	    public String index(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allRequestParams) {
		 System.out.println(allRequestParams);
		 
		 String company = allRequestParams.get("company");
		 
		 model.addAttribute("companyInfoPresent", false);
		 if(company != null) {
			 model.addAttribute("companyInfoPresent", true);
			 model.addAttribute("companySymbol", company);
			 
		 }
		 Map<String, String> companyMap = companyRepository.getCompanySymbol();
		 model.addAttribute("companyMap", companyMap);
		 
		 model.addAttribute("test", "testMessage");

		 List<String> list = new ArrayList<String>();
		 list.add("a");
		 list.add("b");
		 model.addAttribute("list", list);
		 
		 
	        return "/dashboard";
	    }
	 
	 @RequestMapping(value = "/testJson", method = RequestMethod.GET)
	    public @ResponseBody TestJson testJsonGet() {
	 
	        return getTestJson();
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
	 
	 @RequestMapping(value = "/testJsonMap", method = RequestMethod.GET, produces="application/json")
	    public @ResponseBody Map<String, Object> testJsonGetMap() {
		 	String name = "CCGT";
			List<Long> category = new ArrayList<Long>();
			category.add(1387791900000l);
			category.add(1387792200000l);
			category.add(1387792500000l);
			category.add(1387792800000l);
			List<Integer> data = new ArrayList<Integer>();
			data.add(8389);
			data.add(8478);
			data.add(8761);
			data.add(8980);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("category", category);
			map.put("data", data);
			
	        return map;
	    }
	 
	 
	 @RequestMapping(value = "/stock", method = RequestMethod.GET, produces="application/json")
	    public @ResponseBody List<List<Object>> getStockMap() {
		 List<List<Object>> category = new ArrayList<List<Object>>();
		 List<Object> category1 = new ArrayList<Object>();
		 category1.add(1147651200000l);
		 category1.add(67.79);
		 
		 List<Object> category2 = new ArrayList<Object>();
		 category2.add(1147737600000l);
		 category2.add(64.98);
		 
		 List<Object> category3 = new ArrayList<Object>();
		 category3.add(1147910400000l);
		 category3.add(63.18);
		 
		 category.add(category1);
		 category.add(category2);
		 category.add(category3);
	     return category;
	    }
	 
	 @RequestMapping(value = "/testMessage", method = RequestMethod.GET, produces="application/json")
	    public @ResponseBody String testMsg() {
	        return "test Message";
	    }
	 
	 
	 
	 private TestJson getTestJson() {
		String name = "CCGT";
		List<Long> category = new ArrayList<Long>();
		category.add(1387791900000l);
		category.add(1387792200000l);
		category.add(1387792500000l);
		category.add(1387792800000l);
		List<Integer> data = new ArrayList<Integer>();
		data.add(8389);
		data.add(8478);
		data.add(8761);
		data.add(8980);
		data.add(9050);
		
		return new TestJson(name, category, data);
	 }

}

class TestJson {
	
	private String name;
	private List<Long> category;
	private List<Integer> data;
	
	public TestJson(String name, List<Long> category, List<Integer> data) {
		this.name = name;
		this.category = category;
		this.data = data;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Long> getCategory() {
		return category;
	}
	public void setCategory(List<Long> category) {
		this.category = category;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}

}

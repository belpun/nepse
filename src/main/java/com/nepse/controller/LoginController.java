package com.nepse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
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
			 model.addAttribute("companyName", company);
		 }
		 
		 model.addAttribute("test", "testMessage");
	        return "/dashboard";
	    }
	 
	 @RequestMapping(value = "/testJson", method = RequestMethod.GET)
	    public @ResponseBody TestJson testJsonGet() {
	 
	        return getTestJson();
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
			data.add(9050);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("category", category);
			map.put("data", data);
			
	        return map;
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

package com.nepse.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nepse.dao.JDBCCompanyRepository;

@Controller
public class LoginController {
	
	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private JDBCCompanyRepository companyRepository;
	
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
			 
			 Date getlatestData = companyRepository.getlatestData(company);
			 String latestDate = "";
			 if(getlatestData != null) {
				 
				 synchronized (df) {
					 latestDate = df.format(getlatestData);
				}
			 }
			 model.addAttribute("latestDataDate", latestDate);
			 
		 }
		 Map<String, String> companyMap = companyRepository.getCompanySymbol();
		 model.addAttribute("companyMap", companyMap);
		 
		 
	        return "/dashboard";
	    }
	 
}


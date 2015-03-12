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
	 
}



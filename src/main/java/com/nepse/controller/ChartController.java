package com.nepse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChartController {
	
	 @RequestMapping(value = "/ftl/chart", method = RequestMethod.GET)
	    public String index(@ModelAttribute("model") ModelMap model) {
	 
	        model.addAttribute("company", "MEGA");
	 
	        return "chart";
	    }

}

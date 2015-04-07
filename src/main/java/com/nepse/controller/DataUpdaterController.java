package com.nepse.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataUpdaterController {

	@RequestMapping(value = "/company/{companySymbol}/update", method = RequestMethod.GET)
	public @ResponseBody List<Object[]> companyClosingPrice(
			@PathVariable String companySymbol) {

		return null;
	}

}

package com.nepse.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


public class NepsePriceController {

	@RequestMapping(value = "/service/test/{clientId}", method = RequestMethod.POST)
	public @ResponseBody QstInfo refreshState(@PathVariable long clientId, HttpServletRequest request) {
	}
		
	}

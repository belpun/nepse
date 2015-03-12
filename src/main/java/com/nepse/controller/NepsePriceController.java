package com.nepse.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nepse.domain.CompanyData;
import com.nepse.service.data.CsvCompanyDataProvider;
import com.nepse.service.data.ICompanyDataProvider;

@Controller
public class NepsePriceController {

	ICompanyDataProvider DataProvider = new CsvCompanyDataProvider();

	public NepsePriceController() {
		System.out.println("created the controller");
	}

//	@RequestMapping(value = "/service/historicData/{symbol}", method = RequestMethod.GET)
//	public @ResponseBody String getJsonCompanyData(@PathVariable String symbol, HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
//		System.out.println("got the request");
//		Map<Date, CompanyData> companyData =null ;
//		try {
//		companyData = DataProvider.getCompanyData(symbol);
//		} catch (FileNotFoundException e) {
//		return "";
//		}
//		if (companyData!= null) {
//			List<String> convertToJsonFormat = convertToJsonFormat(companyData);
//			ObjectMapper mapper = new ObjectMapper();
//			String convertValue = mapper.writeValueAsString(convertToJsonFormat);
//			convertValue = convertValue.replace("[\"", "[[");
//			convertValue = convertValue.replace("\",\"", "],[");
//			convertValue = convertValue.replace("\"]", "]]");
//			System.out.println(convertValue);
//			
//			return convertValue;
////			return "[[1411858800000,393.00,398.00,390.00,393.00],[1398639600000,390.00,397.00,382.00,390.00],[1402873200000,435.00,439.00,430.00,435.00]]";
//					//			return convertValue;
//		}
//		return "";
//	}

	// public List<String> convertToJsonFormat(Map<Date, CompanyData>
	// companyDatas){
	// List<String> datas = new ArrayList<String>();
	//
	//
	// for( Date key : companyDatas.keySet()){
	// CompanyData companyData = companyDatas.get(key);
	// StringBuilder value = new StringBuilder("" + key.getTime());
	// value.append(",").append(companyData.getClosingPrice())
	// .append(",").append(companyData.getHigh())
	// .append(",").append(companyData.getLow())
	// .append(",").append(companyData.getClosingPrice());
	// datas.add(value.toString());
	// }
	// return datas;
	// }

	public List<String> convertToJsonFormat(Map<Date, CompanyData> companyDatas) {
		List<String> datas = new LinkedList<String>();
		
		Map<Date, CompanyData> sortedCompanyDatas = new TreeMap<Date, CompanyData>(companyDatas);
		for (Date key : sortedCompanyDatas.keySet()) {
			CompanyData companyData = sortedCompanyDatas.get(key);
			
			DateTime eventTime = new DateTime(key);
			eventTime = eventTime.plusHours(12);
			StringBuilder value = new StringBuilder("" + eventTime.getMillis());
			System.out.println(eventTime.getMillis()+ " " +eventTime);
			
			Float closingPrice = Float.valueOf(companyData.getClosingPrice());
			
			String openPrice = String.valueOf(closingPrice - 10l);
			
			value.append(",").append(openPrice).append(",")
					.append(companyData.getHigh()).append(",")
					.append(companyData.getLow()).append(",")
					.append(companyData.getClosingPrice());
			datas.add(value.toString());
		}
		return datas;
	}

//	public class JsonData {
//		private long date;
//
//		private String open;
//		private String high;
//		private String low;
//		private String close;
//
//		public JsonData(long date, String open, String high, String low,
//				String close) {
//			this.date = date;
//			this.open = open;
//			this.high = high;
//			this.low = low;
//			this.close = close;
//		}
//
//		public long getDate() {
//			return date;
//		}
//
//		public void setDate(long date) {
//			this.date = date;
//		}
//
//		public String getOpen() {
//			return open;
//		}
//
//		public void setOpen(String open) {
//			this.open = open;
//		}
//
//		public String getHigh() {
//			return high;
//		}
//
//		public void setHigh(String high) {
//			this.high = high;
//		}
//
//		public String getLow() {
//			return low;
//		}
//
//		public void setLow(String low) {
//			this.low = low;
//		}
//
//		public String getClose() {
//			return close;
//		}
//
//		public void setClose(String close) {
//			this.close = close;
//		}
//
//		@Override
//		public String toString() {
//			return open + "," + high + "," + low + "," + close;
//		}
//
//	}

}

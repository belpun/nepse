package com.nepse.data;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.nepse.domain.CompanyData;
import com.nepse.exception.DataNotAvailable;

public class NepseDataExtractorFromWeb {
	private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	public List<CompanyData> extractLiveData() {
		
		List<CompanyData> companies = new ArrayList<CompanyData>();

		Document doc = null;
		try {
			doc = Jsoup.connect(
					"http://www.nepalstock.com/live/stockupdate.php").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements dataTable = doc.getElementsByClass("dataTable")
				.select("tbody").select("tr");

		if (dataTable == null) {
			throw new DataNotAvailable("data currently not available");
		}

		for (int i = 1; i < dataTable.size(); i++) {
			Elements stockCompany = dataTable.get(i).select("td");
			int symbolNumber = Integer.valueOf(stockCompany.get(0).text());
			String name = stockCompany.get(1).select("a").attr("title");
			String symbol = stockCompany.get(1).text();
			String lastTradedPrice = stockCompany.get(2).text();
			String lastTradedVolume = stockCompany.get(3).text();
			String percentageChange = stockCompany.get(4).text();
			String open = stockCompany.get(5).text();
			String low = stockCompany.get(6).text();
			String high = stockCompany.get(7).text();
			String volume = stockCompany.get(8).text();

			companies.add(new CompanyData(symbolNumber, name, symbol,
					lastTradedPrice, lastTradedVolume, percentageChange, open,
					low, high, volume));
		}

		return companies;
	}
	
	public List<CompanyData> extractArchivedData(String date) {
	
		List<CompanyData> companies = new ArrayList<CompanyData>();

		Document doc = null;
		try {
			
			//date sample Date=2014-08-22
			doc = Jsoup.connect("http://www.nepalstock.com/datanepse/previous.php")
					.data("Date", date)
					.post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements dataTable = doc.getElementsByClass("dataTable");
				
		if(dataTable.size() < 3) {
			throw new DataNotAvailable("data currently not available");
			
		}
				
		dataTable=dataTable.get(0).select("tbody").select("tr");
		
		if (dataTable == null) {
		}
		
		for (int i = 1; i < dataTable.size(); i++) {
			Elements stockCompany = dataTable.get(i).select("td");
			int symbolNumber = Integer.valueOf(stockCompany.get(0).text());
			String name = stockCompany.get(1).text();
			String noOfTransaction = stockCompany.get(2).text().replaceAll(",","");
			String high = stockCompany.get(3).text().replaceAll(",","");
			String low = stockCompany.get(4).text().replaceAll(",","");
			String closingPrice = stockCompany.get(5).text().replaceAll(",","");
			String volume = stockCompany.get(6).text().replaceAll(",","");
			String amount = stockCompany.get(7).text().replaceAll(",","");
			String previousClosingPrice = stockCompany.get(8).text().replaceAll(",","");
			String difference = stockCompany.get(9).text().replaceAll(",","");
			
			companies.add(new CompanyData(symbolNumber, name, noOfTransaction,
					high, low, closingPrice, volume, amount, previousClosingPrice,
					difference, true));
		}
		
		return companies;
	}
	
	public  Map<Date, CompanyData> extractArchivedDataForCompany(String symbol, String startdate, String  endDate) {
		Map<Date, CompanyData> allData = new LinkedHashMap<Date, CompanyData>();
		Document doc = null;
			try {
			
			//date sample Date=2014-08-22
			doc = Jsoup.connect("http://www.nepalstock.com.np/stockWisePrices")
					.data("startDate", startdate)
					.data("endDate", endDate)
					.data("stock-symbol", symbol)
					.data("_limit", "500")
					.post();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		Elements dataTable = doc.getElementsByTag("table").get(0).select("tbody").select("tr");
		System.out.println(dataTable);
		for (int i = 2; i < dataTable.size() -1; i++) {
			Elements stockCompany = dataTable.get(i).select("td");
//			int symbolNumber = Integer.valueOf(stockCompany.get(0).text());
			Date date = null;
			try {
				date = dateFormatter.parse(stockCompany.get(1).text());
			} catch (ParseException e) {
				System.out.println("Cannot parse the data from the web so skipping it");
				continue;
			}
			String totalTransaction = stockCompany.get(2).text();
			String totalSharesTraded = stockCompany.get(3).text();
			String volume = stockCompany.get(4).text();
			String maxPrice = stockCompany.get(5).text();
			String minPrice = stockCompany.get(6).text();
			String closingPrice = stockCompany.get(7).text();
			
			CompanyData companyData = new CompanyData(totalTransaction, totalSharesTraded, volume, maxPrice, minPrice, closingPrice);
			
			allData.put(date, companyData);
		}
		return allData;
	}
	
	
	public Map<Date, List<CompanyData>> extractArchivedData(String fromDate, String toDate) {
		Map<Date, List<CompanyData>> dateRangeData = new LinkedHashMap<Date, List<CompanyData>>();
		
		Date from = null;
		Date to = null;
		try {
			from = dateFormatter.parse(fromDate);
			to = dateFormatter.parse(toDate);
		} catch (ParseException e) {
			throw new DataNotAvailable("Date should be in yyy-MM-dd format");
		}
		
		if (to.before(from)){
			throw new DataNotAvailable("data currently not available");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		while (from.before(to) || from.equals(to)){
			String requiredDate = dateFormatter.format(from);
			
			List<CompanyData> extractArchivedData = null;
			try{
				extractArchivedData = extractArchivedData(requiredDate);
			
			} catch (DataNotAvailable ex) {
				System.out.println("Data not found for " + from);
			}
			
			dateRangeData.put(calendar.getTime(), extractArchivedData);
			calendar.add(Calendar.DATE, 1);
			from = calendar.getTime();
			
		}
		
		return dateRangeData;
		
	}
		
}

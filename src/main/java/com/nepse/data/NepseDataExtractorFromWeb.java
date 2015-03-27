package com.nepse.data;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nepse.domain.CompanyData;
import com.nepse.exception.CannotConnectToDataServer;
import com.nepse.exception.DataNotAvailable;

public class NepseDataExtractorFromWeb {
	private static final DateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final Logger logger = LoggerFactory
			.getLogger(NepseDataExtractorFromWeb.class);

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

			// date sample Date=2014-08-22
			doc = Jsoup
					.connect("http://www.nepalstock.com/datanepse/previous.php")
					.data("Date", date).post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements dataTable = doc.getElementsByClass("dataTable");

		if (dataTable.size() < 3) {
			throw new DataNotAvailable("data currently not available");

		}

		dataTable = dataTable.get(0).select("tbody").select("tr");

		if (dataTable == null) {
		}

		for (int i = 1; i < dataTable.size(); i++) {
			Elements stockCompany = dataTable.get(i).select("td");
			int symbolNumber = Integer.valueOf(stockCompany.get(0).text());
			String name = stockCompany.get(1).text();
			String noOfTransaction = stockCompany.get(2).text()
					.replaceAll(",", "");
			String high = stockCompany.get(3).text().replaceAll(",", "");
			String low = stockCompany.get(4).text().replaceAll(",", "");
			String closingPrice = stockCompany.get(5).text()
					.replaceAll(",", "");
			String volume = stockCompany.get(6).text().replaceAll(",", "");
			String amount = stockCompany.get(7).text().replaceAll(",", "");
			String previousClosingPrice = stockCompany.get(8).text()
					.replaceAll(",", "");
			String difference = stockCompany.get(9).text().replaceAll(",", "");

			companies.add(new CompanyData(symbolNumber, name, noOfTransaction,
					high, low, closingPrice, volume, amount,
					previousClosingPrice, difference, true));
		}

		return companies;
	}

	public Map<Date, CompanyData> extractArchivedDataForCompanyAll(String symbol) {
		DateTime currentDate = new DateTime();
		String toadyDate = dateFormatter.format(currentDate.toDate());

		currentDate = currentDate.minusYears(10);

		String startDate = dateFormatter.format(currentDate.toDate());

		Map<Date, CompanyData> extractArchivedDataForCompanyAll = extractArchivedDataForCompanyAll(
				symbol, startDate, toadyDate);

		return extractArchivedDataForCompanyAll;
	}

	public Map<Date, CompanyData> extractArchivedDataForCompanyAll(
			String symbol, String startdate, String endDate) {

		Map<Date, CompanyData> mainData = new TreeMap<Date, CompanyData>();

		ExtractionResult result = extractArchivedDataForCompany(symbol,
				startdate, endDate);
		if (result != null) {
			mainData.putAll(result.getDatas());
		}
		while (result != null && result.getDatas().size() == 500) {
			boolean desc = false;

			Date firstDate = result.getFirstDate();
			Date lastDate = result.getLastDate();

			if (firstDate.after(lastDate)) {
				desc = true;
			}

			DateTime jodaLastDate = new DateTime(lastDate);
			if (desc) {
				jodaLastDate = jodaLastDate.plusDays(1);

				endDate = dateFormatter.format(jodaLastDate.toDate());

				result = extractArchivedDataForCompany(symbol, startdate,
						endDate);
			} else {
				jodaLastDate = jodaLastDate.minusDays(1);
				startdate = dateFormatter.format(jodaLastDate.toDate());

				result = extractArchivedDataForCompany(symbol, startdate,
						endDate);
			}

			if (result != null && result.getDatas() != null) {
				mainData.putAll(result.getDatas());
			}

		}

		return mainData;
	}

	public ExtractionResult extractArchivedDataForCompany(String symbol,
			String startdate, String endDate) {
		Map<Date, CompanyData> allData = new TreeMap<Date, CompanyData>();
		Document doc = null;
		try {

			// date sample Date=2014-08-22
			doc = Jsoup.connect("http://www.nepalstock.com.np/stockWisePrices")
					.timeout(10000).data("startDate", startdate)
					.data("endDate", endDate).data("stock-symbol", symbol)
					.data("_limit", "500").post();
		} catch (IOException e) {
			throw new CannotConnectToDataServer(
					"Cannot connect to nepalsotck.com at the moment");
		}
		// wait for the server to reponse back
		sleep();

		Elements dataTable = doc.getElementsByTag("table").get(0)
				.select("tbody").select("tr");
		System.out.println(dataTable);

		ExtractionResult result = new ExtractionResult();

		if (dataTable.size() < 4) {
			return null;
		}

		for (int i = 2; i < dataTable.size() - 1; i++) {
			Elements stockCompany = dataTable.get(i).select("td");
			// int symbolNumber = Integer.valueOf(stockCompany.get(0).text());
			Date date = null;
			try {
				date = dateFormatter.parse(stockCompany.get(1).text());
				if (i == 2) {
					result.setFirstDate(date);
				} else if (i == dataTable.size() - 2) {
					result.setLastDate(date);
				}

			} catch (ParseException e) {
				System.out
						.println("Cannot parse the data from the web so skipping it");
				continue;
			}
			String totalTransaction = stockCompany.get(2).text();
			String totalSharesTraded = stockCompany.get(3).text();
			String volume = stockCompany.get(4).text();
			String maxPrice = stockCompany.get(5).text();
			String minPrice = stockCompany.get(6).text();
			String closingPrice = stockCompany.get(7).text();

			CompanyData companyData = new CompanyData(totalTransaction,
					totalSharesTraded, volume, maxPrice, minPrice, closingPrice);
			allData.put(date, companyData);
		}

		result.setDatas(allData);

		return result;
	}

	public Map<String, String> getAllCompanyMap() {
		String COMPANY_URL = "http://merolagani.com/handlers/AutoSuggestHandler.ashx?type=Company";

		Map<String, String> allData = new HashMap<String, String>();
		Element doc = null;
		try {

			doc = Jsoup.connect(COMPANY_URL).timeout(10000).get().body();
			String json = doc.text();

			ObjectMapper mapper = new ObjectMapper();

			List<CompanyJson> readValue = mapper.readValue(json,
					new TypeReference<List<CompanyJson>>() {
					});

			for (CompanyJson company : readValue) {
				allData.put(company.getCompanySymbol(),
						company.getCompanyName());
			}

			System.out.println("stop");

		} catch (IOException e) {
			throw new CannotConnectToDataServer(
					"Cannot connect to nepalsotck.com at the moment", e);
		}
		return allData;
	}

	// private void printMap(Map<Date, CompanyData> allData){
	//
	// for (Date key : allData.keySet()) {
	// System.out.println(dateFormatter.format(key));
	// }
	//
	// }

	private void sleep() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			logger.error("Interuppted while sleeping to get response from nepalstock.com");
		}

	}

	// private Map<Date, CompanyData> getDataFromGetRequest(String link) {
	// Document doc = null;
	// try {
	//
	// //date sample Date=2014-08-22
	// doc = Jsoup.connect(link).timeout(10000)
	// .get();
	// sleep();
	// } catch (IOException e) {
	// throw new
	// CannotConnectToDataServer("Cannot connect to nepalsotck.com at the moment");
	// }
	//
	// ExtractionResult extractResultFromDoc = extractResultFromDoc(doc, false);
	//
	// return extractResultFromDoc.getDataMap();
	// }

	// private ExtractionResult extractResultFromDoc(Document doc, boolean
	// getOtherPageLinks){
	//
	//
	// extractionResult.setDataMap(allData);
	// return extractionResult;
	// }

	public Map<Date, List<CompanyData>> extractArchivedData(String fromDate,
			String toDate) {
		Map<Date, List<CompanyData>> dateRangeData = new LinkedHashMap<Date, List<CompanyData>>();

		Date from = null;
		Date to = null;
		try {
			from = dateFormatter.parse(fromDate);
			to = dateFormatter.parse(toDate);
		} catch (ParseException e) {
			throw new DataNotAvailable("Date should be in yyy-MM-dd format");
		}

		if (to.before(from)) {
			throw new DataNotAvailable("data currently not available");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		while (from.before(to) || from.equals(to)) {
			String requiredDate = dateFormatter.format(from);

			List<CompanyData> extractArchivedData = null;
			try {
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

	public Map<Date, Double> extractOpenData(String symbol) {

		String COMPANY_URL_OPEN_PRICE = "http://www.merolagani.com/handlers/webrequesthandler.ashx?type=get_company_graph&symbol="
				+ symbol + "&dateRange=96";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Map<Date, Double> allData = new TreeMap<Date, Double>();
		Element doc = null;
		try {

			doc = Jsoup.connect(COMPANY_URL_OPEN_PRICE).timeout(10000).get()
					.body();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(doc.text());
			
			if(rootNode != null && !"null".equals(rootNode.toString())) {

				JsonNode nameNode = rootNode.path("quotes");
	
				List<CompanyDataJson> companyValues = mapper.readValue(
						nameNode.toString(),
						new TypeReference<List<CompanyDataJson>>() {
						});
	
				for (CompanyDataJson company : companyValues) {
					Date date;
					try {
						date = sdf.parse(company.getDate());
						double open = company.getOpen();
	
						allData.put(date, open);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			throw new CannotConnectToDataServer(
					"Cannot connect to nepalsotck.com at the moment", e);
		}
		return allData;
	}

	// get all the rsi data// here you also get opening/closing /volume/date
	// http://www.merolagani.com/handlers/webrequesthandler.ashx?type=get_company_graph&symbol=ADBL&dateRange=96

	// get all the data from index
	// http://www.merolagani.com/handlers/webrequesthandler.ashx?type=get_index_graph&indexID=58&dateRange=12

	// market summary
	// http://www.merolagani.com/handlers/webrequesthandler.ashx?type=market_summary

	// getlive trading info
	// http://www.merolagani.com/signalr/send?transport=serverSentEvents&connectionToken=UrDdG5a3CALympb1kFE8tHUceN5iv7_tp2qoWoRTQ4kv4jm03CykDGo2kHBmTSQr_8tCR2yrYfYUJcJXmolVWvoD2M7BYOsPyH6BmtnmWngeMXtP7dfhkr78bAwWz77fHo4lh6xGnX43FgGHetTFD6wmTDU39nk358PiCUa-BQ2Zq2cdFc2EoL_SuUgczkcs0

}

class ExtractionResult {
	private Date firstDate;
	private Date lastDate;

	private Map<Date, CompanyData> datas;

	public Map<Date, CompanyData> getDatas() {
		return datas;
	}

	public void setDatas(Map<Date, CompanyData> datas) {
		this.datas = datas;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
}

class CompanyJson {
	private String l;
	private String d;
	private String v;

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		if (l != null && l.contains("(") && l.contains(")")) {
			this.l = l.substring(l.indexOf('(') + 1, l.indexOf(')'));
		}
	}

	public String getCompanySymbol() {
		return d;
	}

	public String getCompanyName() {
		return l;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class CompanyDataJson {

	private double open;

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}
}

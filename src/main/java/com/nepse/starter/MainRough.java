package com.nepse.starter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nepse.domain.CompanyData;

public class MainRough {

	public static void main(String[] args) throws IOException {
		
//		Map<Date, Integer> map = new TreeMap<Date, Integer>();
//		
//		DateTime dateTime = new DateTime(new Date());
//		
//		Date date2 = dateTime.toDate();
//		System.out.println("medium date:" + date2);
//		dateTime = dateTime.minusYears(1);
//		Date date1 = dateTime.toDate();
//		System.out.println("small date:" + date1);
//		
//		dateTime = dateTime.plusYears(10);
//		Date date3 = dateTime.toDate();
//		System.out.println("large date:" + date3);
//		
//		map.put(date1, 1);
//		map.put(date2, 2);
//		map.put(date3, 3);
//		
//		
//		System.out.println("------------------------------------------");
//		
//		for( Entry<Date, Integer> entry : map.entrySet()) {
//			System.out.println(entry.getKey() + "= " + entry.getValue());
//		}
		
//		File file = new File("src/main/resources/aaaaaaaaaaaaaaaaaaaaa.csv");
//		
//		if(!file.exists()) {
//			file.createNewFile();
//		}
//		
//		FileWriter fw = new FileWriter(file);
//		
//		fw.append("this is the first Character");
//		
//		fw.flush();
//		fw.close();
//		
//		FileWriter fw2 = new FileWriter(file, true);
//		
//		fw2.append("\n");
//		fw2.append("Appended Text");
//		
//		fw2.flush();
//		fw2.close();
//		
		
		
		
		//jsoup load from file
		
//		File input = new File("src/main/resources/test.html");
//		
//		System.out.println("files exists : " + input.exists());
//		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
//		
//		
//		Elements dataTable = doc.getElementsByTag("table").get(0).select("tbody").select("tr");
//		Elements stockCompany = dataTable.get(dataTable.size() - 1 ).select("td");
//		
//		
//		Elements select = stockCompany.select("a :contains('www.nepalstock.com.np')");
//		
//		for (int i = 0; i < select.size(); i++) {
//			System.out.println(select.get(i).attr("href"));
//		}
		
//		String test = "http://www.nepalstock.com.np/main/stockwiseprices/index/2/Date/Desc/";
//		
//		System.out.println(test.contains("www.nepalstock.com.np"));
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode rootNode = mapper.readTree(new File("C:\\workspaces\\bpun\\nepseBackup\\nepse\\src\\main\\resources\\openingPrice\\test.json"));
		
		JsonNode nameNode = rootNode.path("quotes");
		System.out.println(nameNode.toString());
//		mapper.readValue(nameNode, List<CompanyData>.class);
		
		List<CompanyDataJson> readValue = mapper.readValue(nameNode.toString(), new TypeReference<List<CompanyDataJson>>() { });
		
		System.out.println(nameNode.asText());
		
	}
	
	

}

@JsonIgnoreProperties(ignoreUnknown = true)
class CompanyDataJson{
	
	private double open;
	private double high;
	private double low;
	private double close;
	
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	
}

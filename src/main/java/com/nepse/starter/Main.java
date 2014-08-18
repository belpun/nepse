package com.nepse.starter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nepse.data.CompanyData;
import com.nepse.data.NepseDataExtractor;
import com.nepse.writer.CsvWriter;

public class Main {

	public static void main(String[] args) {
		NepseDataExtractor dataExtractor = new NepseDataExtractor();
		CsvWriter writer = new CsvWriter();
//		
//		List<CompanyData> companies = dataExtractor.extractArchivedData("2014-08-12");
		
//		writer.writeArchivedDataToCsvFile(companies, "nepseLiveTest");
		
		
		Map<Date, List<CompanyData>> extractArchivedData = dataExtractor.extractArchivedData("2014-08-03", "2014-08-14");
		
		writer.writeDataPerCompanyToCsvFile(extractArchivedData, "c:\\temp\\nepse", "company.csv", "Ace Development Bank Limited");
		
		
//		Calendar cal = Calendar.getInstance();
//		
//		Date time1 = cal.getTime();
//		System.out.println(time1);
//		
//		cal.add(Calendar.DATE, 1);
//		Date time2 = cal.getTime();
//		System.out.println(time2);

	}
	


}

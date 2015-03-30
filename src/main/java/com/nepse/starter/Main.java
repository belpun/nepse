package com.nepse.starter;

import java.io.File;
import java.util.Date;
import java.util.Map;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.domain.CompanyData;
import com.nepse.writer.CsvWriter;

public class Main {

	public static void main(String[] args) {
		NepseDataExtractorFromWeb dataExtractor = new NepseDataExtractorFromWeb();
		CsvWriter writer = new CsvWriter();
//		
//		List<CompanyData> companies = dataExtractor.extractArchivedData("2014-08-12");
		
//		writer.writeArchivedDataToCsvFile(companies, "nepseLiveTest");
		
		
		NepseDataExtractorFromWeb extractor =new NepseDataExtractorFromWeb();
//		Map<Date, CompanyData> extractArchivedDataForCompany = extractor.extractArchivedDataForCompany("MEGA", "2012-09-14", "2013-10-14");
		
//		File file = new File("src\\main\\resources\\companyData-MEGAtest.csv");
//		writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany, file, false, false);
		
//		Map<Date, CompanyData> extractArchivedDataForCompany2 = extractor.extractArchivedDataForCompany("MEGA", "2013-09-14", "2014-10-14");
//		writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany2, file, false, true);
		
		System.out.println("stop");
//		NepseArchiveDataInitializer init = new NepseArchiveDataInitializer();
//		init.initializeFileForAll();
		
//		Map<String, String> companySymbol = NepseDefinition.getInstance().getCompanySymbol();
//		
//		for (String key : companySymbol.keySet()){
//			System.out.println(key + "= " + companySymbol.get(key));
//		}
		
//		Map<Date, List<CompanyData>> extractArchivedData = dataExtractor.extractArchivedData("2014-10-10", "2014-10-13");
//		
//		writer.writeDataPerCompanyToCsvFile(extractArchivedData, "c:\\temp\\nepse", "company.csv", "Ace Development Bank Limited");
		
		
//		Calendar cal = Calendar.getInstance();
//		
//		Date time1 = cal.getTime();
//		System.out.println(time1);
//		
//		cal.add(Calendar.DATE, 1);
//		Date time2 = cal.getTime();
//		System.out.println(time2);

	}
	
	public static  Long getLongValue() {
		return null;
	}
	


}

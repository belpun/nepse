package com.nepse.loader.initilizer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.domain.CompanyData;
import com.nepse.writer.CsvWriter;

public class WriteArchiveDataFromWebTasklet implements Tasklet {
	private final String FILE_LOCATION = "src" + File.separator + "main" + File.separator + "resources";
	private final String PREFIX = "companyData-";
	private final String SUFIX = ".csv";
	
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private final NepseDataExtractorFromWeb extractor = new NepseDataExtractorFromWeb();
	
	private final CsvWriter writer = new CsvWriter();
	
	@Autowired
	public JDBCCompanyRepository companyRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		Map<String, String> companySymbol = companyRepository.getCompanySymbol();
		
		for (String symbol : companySymbol.keySet()) {
			String fileName = PREFIX + symbol + SUFIX;
			
			ClassPathResource fileResource = new ClassPathResource(fileName);
			
			if(fileResource.exists()) {
				
				//check the last date and get latest and append in sorted order
				appendInNewFile(fileResource.getFile(), symbol);
			} else {
				//create a new file with the name
				String fullPath = FILE_LOCATION + File.separator + fileName;
				
				File file = new File(fullPath);
				
				file.createNewFile();
				writeInNewFile(file, symbol);
				
				//get the data
			}
			
			Thread.currentThread().sleep(2000);
			
		}
		
		
		
		
		NepseDataExtractorFromWeb dataExtractor = new NepseDataExtractorFromWeb();
		
		NepseDataExtractorFromWeb extractor =new NepseDataExtractorFromWeb();
		
		Map<Date, CompanyData> extractArchivedDataForCompany = extractor.extractArchivedDataForCompany("MEGA", "2012-09-14", "2014-10-14");
		
//		writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany, "src\\main\\resources", "MEGA", false);
		
		System.out.println("stop");
		
		return null;
	}
	
	
	public void writeInNewFile(File file, String symbol) {
		DateTime currentDate = new DateTime();
		String currentDateText = dateFormat.format(currentDate.toDate());
		
		currentDate = currentDate.minusYears(10);
		
		String pastDate = dateFormat.format(currentDate.toDate());
		
		Map<Date, CompanyData> mainData = new TreeMap<Date, CompanyData>();
		
		Map<Date, CompanyData> extractArchivedDataForCompany = extractor.extractArchivedDataForCompany(symbol, pastDate, currentDateText);
		
		while(extractArchivedDataForCompany.size() == 500) {
				Date lastKey = ((TreeMap<Date, CompanyData>)extractArchivedDataForCompany).lastKey();
				pastDate = dateFormat.format(lastKey);
				mainData.putAll(extractArchivedDataForCompany);
				extractArchivedDataForCompany.clear();
				extractArchivedDataForCompany = extractor.extractArchivedDataForCompany(symbol, pastDate, currentDateText);
		}
		
		mainData.putAll(extractArchivedDataForCompany);
		
		writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany, file, false, false);
		
	}
	
	public void appendInNewFile(File file, String symbol) {
		
	}

}

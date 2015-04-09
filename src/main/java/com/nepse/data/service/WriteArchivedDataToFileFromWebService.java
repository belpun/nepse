package com.nepse.data.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.domain.CompanyData;
import com.nepse.exception.CompanyDataUpdateException;
import com.nepse.utils.FileNameUtils;
import com.nepse.writer.CsvWriter;

public class WriteArchivedDataToFileFromWebService implements IWriteArchivedDataToFileFromWebService{

	Logger logger = LoggerFactory.getLogger(WriteArchivedDataToFileFromWebService.class);
	
	@Autowired
	private final CsvWriter writer = null;
	
	@Autowired
	private final FileNameUtils fileNameUtils = null;

	@Autowired
	private final NepseDataExtractorFromWeb extractor = null;
	
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public boolean update(String symbol) {
		
		String filePath = fileNameUtils.archivedFileName(symbol);
		File file = new File(filePath);
		boolean skippedWriting = false;
		if (file.exists()) {

			// check the last date and get latest and append in sorted order
			
			skippedWriting = appendToExistingFile(file, symbol);
		} else {
			// create a new file with the name

			try {
				file.createNewFile();
			} catch (IOException e) {
				String errorMsg = "error creating an archived file for " + symbol + " at location : " + filePath; 
				logger.error(errorMsg);
				throw new CompanyDataUpdateException(errorMsg, e);
				
			}
			writeInNewFile(file, symbol);
			System.out.println(file.getAbsolutePath());
			// get the data
		}
		return skippedWriting;
		
	}
	
	private void writeInNewFile(File file, String symbol) {

		Map<Date, CompanyData> extractArchivedDataForCompany = extractor.extractArchivedDataForCompanyAll(symbol);

		writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany,
				file, false, false);

	}
	
	private boolean appendToExistingFile(File file, String symbol) {
		boolean skippedWriting = true;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String line = null;
			String lastLine = null;
			while ((line = br.readLine()) != null) {
				lastLine = line;
			}

			String[] companyData = lastLine.split(",");

			Date latestDate = dateFormat.parse(companyData[0]);
			

			DateTime latestDateTime = new DateTime(latestDate);
			
			LocalDate yesterday = DateTime.now().minusDays(1).toLocalDate();
			LocalDate lastestDate = latestDateTime.toLocalDate();
			if(!yesterday.isEqual(lastestDate) && latestDateTime.isBeforeNow()) {
								
				latestDateTime = latestDateTime.plusDays(1);
	
				String latestDataAvaiDate = dateFormat.format(latestDateTime
						.toDate());
	
				DateTime currentDate = new DateTime();
				String toadyDate = dateFormat.format(currentDate.toDate());
				
				Map<Date, CompanyData> extractArchivedDataForCompany = extractor.extractArchivedDataForCompanyAll(
						symbol, latestDataAvaiDate, toadyDate);
	
				if(extractArchivedDataForCompany != null && !extractArchivedDataForCompany.isEmpty()) {
					skippedWriting = false;
					writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany, file, false, true);
				}
			}

		} catch (FileNotFoundException e) {
			String errorMsg = "could not find the archived file for :" + symbol; 
			logger.error(errorMsg);
			throw new CompanyDataUpdateException(errorMsg, e);
		} catch (IOException e) {
			String errorMsg = "Error accessing file for:" + symbol; 
			logger.error(errorMsg);
			throw new CompanyDataUpdateException(errorMsg, e);
		} catch (ParseException e) {
			throw new CompanyDataUpdateException(" ", e);
			
		} finally {
			if(br!= null) {
				try {
					br.close();
				} catch (IOException e) {
					String errorMsg = "Error closing the resource file for:" + symbol; 
					logger.error(errorMsg);
				}
			}
		}
		return skippedWriting;
	}
}

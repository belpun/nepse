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
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.writer.CsvWriter;

public class WriteOpeningPriceToFileFromWebService {
	
	private final String FILE_LOCATION = "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator + "openingPrice";
	private final String PREFIX = "openingPriceCompanyData-";
	private final String SUFIX = ".csv";
	
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private final NepseDataExtractorFromWeb extractor = null;
	
	@Autowired
	private final CsvWriter writer = null;
	
	
	public void update(Set<String> symbols) throws IOException, InterruptedException {
		
		for (String symbol : symbols) {

			System.out.println("processing company for Opening File " + symbol);
			String fileName = PREFIX + symbol + SUFIX;

			String fullPath = FILE_LOCATION + File.separator + fileName;
			File file = new File(fullPath);
			boolean skippedWriting = false;
			if (file.exists()) {
				// check the last date and get latest and append in sorted order
				skippedWriting = appendToExistingFile(file, symbol);
			} else {
				// create a new file with the name
				file.createNewFile();
				writeInNewFile(file, symbol);
				// get the data
			}
			
			if(!skippedWriting && symbols.size() > 1) {
				Thread.currentThread().sleep(5000);
			}
		}
	}
	
	public void writeInNewFile(File file, String symbol) {

		Map<Date, Double> extractOpeningDataForCompany = extractor.extractOpenData(symbol);

		writer.writeCompanyOpeningPriceToCsvFile(extractOpeningDataForCompany, file, false, false);

	}
	
	public boolean appendToExistingFile(File file, String symbol) {
		boolean skippedWriting = false;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String line = null;
			String lastLine = null;
			br.readLine();
			while ((line = br.readLine()) != null) {
				lastLine = line;
			}
			DateTime latestDateTime = null;
			Date latestDate = null;
			
			if(lastLine != null) {
				String[] companyData = lastLine.split(",");
				latestDate = dateFormat.parse(companyData[0]);
				latestDateTime = new DateTime(latestDate);
			} else {
				latestDateTime = new DateTime().minusYears(10);
			}
			
			Map<Date, Double> extractOpeningDataForCompany = extractor.extractOpenData(symbol);
			
			Map<Date, Double> dataToWrite = new TreeMap<Date, Double>();
			
			for(Entry<Date, Double> entry : extractOpeningDataForCompany.entrySet()) {
				
				DateTime keyDate = new DateTime(entry.getKey());
				if(latestDateTime.isBefore(keyDate)) {
					dataToWrite.put(entry.getKey(), entry.getValue());
				} else {
					continue;
				}
				
			}
			
			if(!dataToWrite.isEmpty()) {
				writer.writeCompanyOpeningPriceToCsvFile(extractOpeningDataForCompany, file, false, false);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if(br!= null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return skippedWriting;
	}

}
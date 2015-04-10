package com.nepse.data.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.domain.CompanyData;
import com.nepse.exception.CompanyDataUpdateException;
import com.nepse.loader.CompanyDataWriterWithModel;
import com.nepse.loader.CompanyOpeningPriceDataMapper;
import com.nepse.utils.FileNameUtils;
import com.nepse.writer.CsvWriter;

public class OpeningPriceService implements IOpeningPriceService {
	
	private static final Logger logger = LoggerFactory.getLogger(OpeningPriceService.class);
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private final NepseDataExtractorFromWeb extractor = null;
	
	@Autowired
	private final CsvWriter writer = null;
	
	@Autowired
	private final FileNameUtils fileNameUtils = null;
	
	@Autowired
	private final CompanyDataWriterWithModel companyDataWriterWithModel = null;
	
	@Override
	public void updateOpeningPriceFromFileToDb(String symbol) {
		String filePath = fileNameUtils.openingFileName(symbol);
		File file = new File(filePath);
		
		if(file.exists()) {
			
			try{
				FlatFileItemReader<CompanyData> itemReader = new FlatFileItemReader<CompanyData>();
				
				itemReader.setResource(new FileSystemResource(file));
				
				itemReader.setLinesToSkip(1);
							
				DefaultLineMapper<CompanyData> lineMapper = new DefaultLineMapper<CompanyData>();
				
				DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
				delimitedLineTokenizer.setNames(new String[]{"Date","Open"});
				
				lineMapper.setLineTokenizer(delimitedLineTokenizer);
				
				CompanyOpeningPriceDataMapper companyDataMapper = new CompanyOpeningPriceDataMapper();
				companyDataMapper.setSymbol(symbol);
				lineMapper.setFieldSetMapper(companyDataMapper);
				itemReader.setLineMapper(lineMapper);
				itemReader.open(new ExecutionContext());
				CompanyData read = itemReader.read();
				
				List<CompanyData> companies = new ArrayList<CompanyData>();
				
				while (read != null) {
					companies.add(read);
					read = itemReader.read();
				}

				if(!companies.isEmpty()) {
				
					companyDataWriterWithModel.write(companies);
				}
				
			} catch(Exception e) {
				String errorMsg = "error writing file to database for + " + symbol + " at location : " + filePath; 
				logger.error(errorMsg);
				throw new CompanyDataUpdateException(errorMsg, e);
			}
			
		} else {
			String errorMsg = "unable to find the file for + " + symbol + " at location : " + filePath; 
			logger.error(errorMsg);
			throw new CompanyDataUpdateException(errorMsg);
		}
		
	}
	
	@Override
	public boolean updateOpeningPriceFromWebToFile(String symbol) {

			System.out.println("processing company for Opening File " + symbol);

			String fullPath = fileNameUtils.openingFileName(symbol);
			File file = new File(fullPath);
			boolean skippedWriting = false;
			if (file.exists()) {
				// check the last date and get latest and append in sorted order
				skippedWriting = appendToExistingFile(file, symbol);
			} else {
				// create a new file with the name
				try {
					file.createNewFile();
				} catch (IOException e) {
					String errorMsg = "error creating a file at :" + fullPath;
					logger.error(errorMsg);
					throw new CompanyDataUpdateException(errorMsg, e);
				}
				writeInNewFile(file, symbol);
			}
			return skippedWriting;
			
	}
	
	private void writeInNewFile(File file, String symbol) {

		Map<Date, Double> extractOpeningDataForCompany = extractor.extractOpenData(symbol);

		writer.writeCompanyOpeningPriceToCsvFile(extractOpeningDataForCompany, file, false, false);

	}
	
	private boolean appendToExistingFile(File file, String symbol) {
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
			String errorMsg = "could not find the archived file for :" + symbol;
			logger.error(errorMsg);
			throw new CompanyDataUpdateException(errorMsg, e);
		} catch (IOException e) {
			String errorMsg = "Error accessing file for:" + symbol;
			logger.error(errorMsg);
			throw new CompanyDataUpdateException(errorMsg, e);
		} catch (ParseException e) {
			String errorMsg = "Could not parse the data while reading file for symbol : ";
			logger.error(errorMsg);
			throw new CompanyDataUpdateException(errorMsg, e);
		} finally {
			if(br!= null) {
				try {
					br.close();
				} catch (IOException e) {
					String errorMsg =  "Error closing the resource file for:" + symbol;
					logger.error(errorMsg);
					throw new CompanyDataUpdateException(errorMsg, e);
				}
			}
		}
		return skippedWriting;
	}

}

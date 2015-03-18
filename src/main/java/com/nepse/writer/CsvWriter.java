package com.nepse.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepse.domain.CompanyData;
import com.nepse.exception.FileCreationException;

public class CsvWriter {
	private final static String NEPSE_LIVE_HEADERS = "S.No,Company Name,Symbol,LTP,LTV,% Change,Open, Low, High,Volume";
	private final static String NEPSE_ARCHIVED_HEADERS = "S.No,Traded Companies,No.of Transaction,Max.price,Min.price,Closing Price,Total Share,Amount,Prevous Closing, Difference Rs.";
	private final static String NEPSE_COMPANY_HEADERS = "Date,No.of Transaction,Total Share,Amount,Max.price,Min.price,Closing Price";
	private final static String DELIMITER = ",";
	
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private final static Logger logger = LoggerFactory.getLogger(CsvWriter.class);

	public void writeLiveDataToCsvFile(List<CompanyData> companies, String location, String fileName) {
		File outputFile;
		OutputStream outputStream = null;
		try {
			outputFile = createNewOutputFile(location, fileName);

			outputStream = new FileOutputStream(outputFile);

			String headerLine = NEPSE_LIVE_HEADERS + "\n";
			outputStream.write(headerLine.getBytes());

			for (CompanyData company : companies) {
				StringBuilder data = new StringBuilder();
				data.append(company.getSymbolNumber()).append(DELIMITER);
				data.append(company.getName()).append(DELIMITER);
				data.append(company.getSymbol()).append(DELIMITER);
				data.append(company.getLastTradedPrice()).append(DELIMITER);
				data.append(company.getLastTradedVolume()).append(DELIMITER);
				data.append(company.getPercentageChange()).append(DELIMITER);
				data.append(company.getOpenPrice()).append(DELIMITER);
				data.append(company.getLow()).append(DELIMITER);
				data.append(company.getHigh()).append(DELIMITER);
				data.append(company.getVolume()).append("\n");

				outputStream.write(data.toString().getBytes());
			}

		} catch (IOException e) {
			throw new FileCreationException("cannot create a file");
		} finally {
			if(outputStream != null) {
				try {
					outputStream.flush();
			
				outputStream.close();
				} catch (IOException e) {
					throw new FileCreationException("cannot create a file");
				}
			}
		}

	}
	
	public void writeArchivedDataToCsvFile(List<CompanyData> companies, String location, String fileName, String requestedDate) {

		File outputFile;
		OutputStream outputStream = null;
		try {
			outputFile = createNewOutputFileforArchived(location, fileName, requestedDate);

			outputStream = new FileOutputStream(outputFile);

			String headerLine = NEPSE_ARCHIVED_HEADERS + "\n";
			outputStream.write(headerLine.getBytes());

			for (CompanyData company : companies) {
				StringBuilder data = new StringBuilder();
				data.append(company.getSymbolNumber()).append(DELIMITER);
				data.append(company.getName()).append(DELIMITER);
				data.append(company.getNoOfTransaction()).append(DELIMITER);
				data.append(company.getHigh()).append(DELIMITER);
				data.append(company.getLow()).append(DELIMITER);
				data.append(company.getClosingPrice()).append(DELIMITER);
				data.append(company.getVolume()).append(DELIMITER);
				data.append(company.getAmount()).append(DELIMITER);
				data.append(company.getPreviousClosingPrice()).append(DELIMITER);
				data.append(company.getDifference()).append("\n");

				outputStream.write(data.toString().getBytes());
			}

		} catch (IOException e) {
			System.out.println("cannot open a file");
			logger.error("cannot open a file", e);
		} finally {
			if(outputStream != null) {
				try {
					outputStream.flush();
			
				outputStream.close();
				} catch (IOException e) {
					System.out.println("cannot write or flush to the file ");
					logger.error("cannot write or flush to the file ", e);
				}
			}
		}

	}
	
	public void writeDataPerCompanyToCsvFile(Map<Date, CompanyData> companiesDatas, File outputFile, boolean dateInMilliSecond, boolean append) {
		
		Writer fw = null;
		try {

//			outputStream = new FileOutputStream(outputFile);
			
			fw = new FileWriter(outputFile, append);

			if(!append) {
				String headerLine = NEPSE_COMPANY_HEADERS + "\n";
				fw.append(headerLine);
			}

			for(Date key : companiesDatas.keySet()){
				
				CompanyData company = companiesDatas.get(key);
					
					StringBuilder data = new StringBuilder();
					if (dateInMilliSecond) {
						data.append(key.getTime()).append(DELIMITER);
					} else {
						data.append(df.format(key)).append(DELIMITER);
					}
					
//					data.append(company.getSymbolNumber()).append(DELIMITER);
//					data.append(company.getName()).append(DELIMITER);
					data.append(company.getNoOfTransaction()).append(DELIMITER);
					data.append(company.getTotalSharesTraded()).append(DELIMITER);
					data.append(company.getVolume()).append(DELIMITER);
					data.append(company.getHigh()).append(DELIMITER);
					data.append(company.getLow()).append(DELIMITER);
					data.append(company.getClosingPrice()).append("\n");
//					data.append(company.getPreviousClosingPrice()).append(DELIMITER);
//					data.append(company.getDifference()).append("\n");
					fw.append(data.toString());
		}

		} catch (IOException e) {
			throw new FileCreationException("cannot create a file");
		} finally {
			if(fw != null) {
				try {
					fw.flush();
			
					fw.close();
				} catch (IOException e) {
					throw new FileCreationException("cannot create a file");
				}
			}
		}

	}

	private File createNewOutputFileforArchived(String location,
			String fileName, String requestedDate) throws IOException {
		
		String outDir = location;

		String outFileName = fileName;

		
		String fileNameWithTimestamp = fileName.substring(0,
				fileName.length() - 4);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String timestamp = dateFormat.format(new Date(System.currentTimeMillis()));

		outFileName = fileNameWithTimestamp +"for-"+ requestedDate +"createdOn-" + timestamp + ".csv";

		File file = new File(outDir, File.separatorChar + outFileName);

		File directories = new File(outDir);
		if(!directories.exists()){
		
			directories.mkdirs();
		}
		
		file.createNewFile();

		return file;
	}


	private File createNewOutputFile(String location, String fileName) throws IOException {
		String outDir = location;

		String outFileName = fileName;

		outFileName = appendTimestamp(outFileName, System.currentTimeMillis());

		File file = new File(outDir, File.separatorChar + outFileName);

		File directories = new File(outDir);
		if(!directories.exists()){
		
			directories.mkdirs();
		}
		
		file.createNewFile();

		return file;
	}

	protected String appendTimestamp(String fileName, Long time) {
		String fileNameWithTimestamp = fileName.substring(0,
				fileName.length() - 4);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String timestamp = dateFormat.format(new Date(time));

		return fileNameWithTimestamp + "-" + timestamp + ".csv";
	}

}

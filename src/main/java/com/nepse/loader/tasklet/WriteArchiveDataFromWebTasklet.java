package com.nepse.loader.tasklet;

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
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.domain.CompanyData;
import com.nepse.writer.CsvWriter;

public class WriteArchiveDataFromWebTasklet implements Tasklet{
	private final String FILE_LOCATION = "src" + File.separator + "main"
			+ File.separator + "resources";
	private final String PREFIX = "companyData-";
	private final String SUFIX = ".csv";

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private final NepseDataExtractorFromWeb extractor = new NepseDataExtractorFromWeb();

	private final CsvWriter writer = new CsvWriter();

	@Autowired
	public JDBCCompanyRepository companyRepository;

//	public static void main(String[] args) throws Exception {
//		new WriteArchiveDataFromWebTasklet().execute();
//	}

	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		Map<String, String> companySymbol = companyRepository
				.getCompanySymbol();
		// Map<String, String> companySymbol = new HashMap<String, String>();
		// companySymbol.put("ADBL", "for test");

		for (String symbol : companySymbol.keySet()) {

			System.out.println("processing company " + symbol);
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
			
			if(!skippedWriting) {
				Thread.currentThread().sleep(20000);
			}


		}

		System.out.println("stop");

		return RepeatStatus.FINISHED;
	}

	public void writeInNewFile(File file, String symbol) {

		Map<Date, CompanyData> extractArchivedDataForCompany = extractor
				.extractArchivedDataForCompanyAll(symbol);

		writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany,
				file, false, false);

	}

	public JDBCCompanyRepository getCompanyRepository() {
		return companyRepository;
	}

	public void setCompanyRepository(JDBCCompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public boolean appendToExistingFile(File file, String symbol) {
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
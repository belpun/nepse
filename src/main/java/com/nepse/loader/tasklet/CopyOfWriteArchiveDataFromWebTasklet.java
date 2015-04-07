package com.nepse.loader.tasklet;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

public class CopyOfWriteArchiveDataFromWebTasklet implements Tasklet{
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

			ClassPathResource fileResource = new ClassPathResource(fileName);

			if (fileResource.exists()) {

				// check the last date and get latest and append in sorted order
			} else {
				// create a new file with the name
				String fullPath = FILE_LOCATION + File.separator + fileName;

				File file = new File(fullPath);

				file.createNewFile();
				writeInNewFile(file, symbol);

				// get the data
			}

			Thread.currentThread().sleep(20000);

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

	// private Map<Date, CompanyData> extractAllTheData(String symbol,
	// String pastDate) {
	//
	// DateTime currentDate = new DateTime();
	// String currentDateText = dateFormat.format(currentDate.toDate());
	//
	// Map<Date, CompanyData> mainData = new TreeMap<Date, CompanyData>();
	//
	// Map<Date, CompanyData> extractArchivedDataForCompany = extractor
	// .extractArchivedDataForCompany(symbol, pastDate,
	// currentDateText);
	//
	// while (extractArchivedDataForCompany.size() == 500) {
	// Date lastKey = ((TreeMap<Date, CompanyData>)
	// extractArchivedDataForCompany)
	// .lastKey();
	// pastDate = dateFormat.format(lastKey);
	// mainData.putAll(extractArchivedDataForCompany);
	// extractArchivedDataForCompany.clear();
	// extractArchivedDataForCompany = extractor
	// .extractArchivedDataForCompany(symbol, pastDate,
	// currentDateText);
	// }
	//
	// mainData.putAll(extractArchivedDataForCompany);
	// return mainData;
	// }

	// public void appendInNewFile(File file, String symbol) {
	//
	// FileReader fr = null;
	// BufferedReader br = null;
	// try {
	// fr = new FileReader(file);
	// br = new BufferedReader(fr);
	//
	// String line = null;
	// String lastLine = null;
	// while ((line = br.readLine()) != null) {
	// lastLine = line;
	// }
	//
	// String[] companyData = lastLine.split(",");
	//
	// Date latestDate = dateFormat.parse(companyData[0]);
	//
	// DateTime latestDateTime = new DateTime(latestDate);
	// latestDateTime = latestDateTime.plusDays(1);
	//
	// String latestDataAvaiDate = dateFormat.format(latestDateTime
	// .toDate());
	//
	// Map<Date, CompanyData> extractArchivedDataForCompany = extractAllTheData(
	// symbol, latestDataAvaiDate);
	//
	// writer.writeDataPerCompanyToCsvFile(extractArchivedDataForCompany,
	// file, false, true);
	//
	// // fr.re
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

}
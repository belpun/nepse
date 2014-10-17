package com.nepse.service.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nepse.data.CompanyData;

public class CsvCompanyDataProvider implements ICompanyDataProvider {
	
	private static final String datePattern = "yyyy-MM-dd";
	
	@Override
	public Map<Date, CompanyData> getCompanyData(String fileName) throws FileNotFoundException {
		
		Map<Date, CompanyData> datas = new HashMap<Date, CompanyData>();
		
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName + ".csv");
		
		if(resourceAsStream ==null) {
			throw new FileNotFoundException();
		}
//		File file = new File("src\\main\\resources\\" + fileName + ".csv");
		
		BufferedReader br = null;
		
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(resourceAsStream);
			br = new BufferedReader(inputStreamReader);

			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			String header = br.readLine();
			System.out.println("reading the first line of the "
					+ "compnay symbol file " + fileName
					+ " and the content" + "is  " + header);
			String compnayDate = null;
			while ((compnayDate = br.readLine()) != null) {
				String[] data = compnayDate.split(",");
				Date stockDate = sdf.parse(data[0]);
				
				CompanyData companyData = new CompanyData(data[1], data[2], data[3],
								data[4], data[5], data[6]); 
		
				datas.put(stockDate, companyData);
			} 
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return datas;
	}


}

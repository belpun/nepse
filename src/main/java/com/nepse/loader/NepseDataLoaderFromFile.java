package com.nepse.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nepse.data.CompanyData;

public class NepseDataLoaderFromFile implements IDataLoader {
	
	private static final String datePattern = "yyyy-MM-dd";

	// @Override
	public Map<String, String> getCompanySymbol(String resourceFile) {
		Map<String, String> companies = null;

		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(resourceFile);

			br = new BufferedReader(fr);

			String header = br.readLine();
			System.out.println("reading the first line of the "
					+ "compnay symbol file " + resourceFile
					+ " and the content" + "is  " + header);
			String compnayDate = null;
			while ((compnayDate = br.readLine()) != null) {
				String[] data = compnayDate.split(",");

				if (companies == null) {
					companies = new HashMap<String, String>();
				}
				companies.put(data[2], data[1]);

			}

		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return companies;
	}
	
	public Map<Date, CompanyData> getCompanyData(String fileName) throws FileNotFoundException {
		
		Map<Date, CompanyData> datas = new HashMap<Date, CompanyData>();
		
		File file = new File(fileName);
		
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName);

			br = new BufferedReader(fr);

			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			String header = br.readLine();
			System.out.println("reading the first line of the "
					+ "compnay symbol file " + fileName
					+ " and the content" + "is  " + header);
			String compnayDate = null;
			while ((compnayDate = br.readLine()) != null) {
				String[] data = compnayDate.split(",");
				Date stockDate = sdf.parse(data[0]);
				
				CompanyData companyData = new CompanyData(Integer.valueOf(data[1]), data[2], data[3],
						data[4], data[5], data[6], data[7], data[8], data[9], data[10], true);
		
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

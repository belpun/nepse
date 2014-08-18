package com.nepse.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NepseDataLoader implements IDataLoader {

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
				companies.put(data[1], data[2]);

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
		return null;
	}

}

package com.nepse.data.service;


public interface IArchivedDataService {

	boolean updateArchievedDataFromWebToFile(String symbol);

	void updateDbFromFile(String symbol);

}

package com.nepse.data.service;

public interface IOpeningPriceService {

	boolean updateOpeningPriceFromWebToFile(String symbol);

	void updateOpeningPriceFromFileToDb(String symbol);

}

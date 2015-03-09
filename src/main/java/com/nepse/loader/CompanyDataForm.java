package com.nepse.loader;

public class CompanyDataForm {
	
	private String date, noOfTransaction, totalShares, amount, maxPrice, minPrice, closingPrice, companySymbol;

	public CompanyDataForm(String date, String noOfTransaction,
			String totalShares, String amount, String maxPrice,
			String minPrice, String closingPrice, String companySymbol) {
		this.date = date;
		this.noOfTransaction = noOfTransaction;
		this.totalShares = totalShares;
		this.amount = amount;
		this.maxPrice = maxPrice;
		this.minPrice = minPrice;
		this.closingPrice = closingPrice;
		this.companySymbol = companySymbol;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNoOfTransaction() {
		return noOfTransaction;
	}

	public void setNoOfTransaction(String noOfTransaction) {
		this.noOfTransaction = noOfTransaction;
	}

	public String getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(String totalShares) {
		this.totalShares = totalShares;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(String closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getCompanySymbol() {
		return companySymbol;
	}

	public void setCompanySymbol(String companySymbol) {
		this.companySymbol = companySymbol;
	}
	

}

package com.nepse.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint( columnNames = {"date", "company_id"}) })
public class CompanyData {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private Company company;
	private int symbolNumber;
	private String name;
	
	private String symbol;
	private String lastTradedPrice;
	private String lastTradedVolume;
	private String percentageChange;
	private String low;
	
	@Column(nullable=true)
	private String openPrice;
	private String high;
	
	//this is the total shares as ell
	private String volume;
	
	private String totalSharesTraded;
	
	private String noOfTransaction;
	private String closingPrice;
	private String amount;
	private String previousClosingPrice;
	private String difference;
	
	private Date date;
	
	public CompanyData() {
		
	}
	
	public CompanyData(String noOfTransaction, String totalSharesTraded, String volume,
			String high, String low,
			String closingPrice, Date date) {
		this.noOfTransaction = noOfTransaction;
		this.totalSharesTraded = totalSharesTraded;
		this.volume = volume;
		this.high = high;
		this.low = low;
		this.closingPrice = closingPrice;
		this.date = date;
	}
	
	//for file
	public CompanyData(String noOfTransaction, String totalSharesTraded, String volume,
			String high, String low,
			String closingPrice) {
		this.noOfTransaction = noOfTransaction;
		this.totalSharesTraded = totalSharesTraded;
		this.volume = volume;
		this.high = high;
		this.low = low;
		this.closingPrice = closingPrice;
	}
	
	
	//this is for archived data
	public CompanyData(int symbolNumber, String name, String noOfTransaction,
			String high, String low,
			String closingPrice, String volume, String amount, String previousClosingPrice,
			String difference, boolean archived) {
		this.symbolNumber = symbolNumber;
		this.name = name;
		this.noOfTransaction = noOfTransaction;
		this.high = high;
		this.low = low;
		this.closingPrice = closingPrice;
		this.volume = volume;
		this.amount = amount;
		this.previousClosingPrice = previousClosingPrice;
		this.difference = difference;
	}
	
	
	//this is for live data
		public CompanyData(int symbolNumber, String name, String symbol,
				String lastTradedPrice, String lastTradedVolume,
				String percentageChange, String open, String low, String high,
				String volume) {
			this.symbolNumber = symbolNumber;
			this.name = name;
			this.symbol = symbol;
			this.lastTradedPrice = lastTradedPrice;
			this.lastTradedVolume = lastTradedVolume;
			this.percentageChange = percentageChange;
			this.openPrice = open;
			this.low = low;
			this.high = high;
			this.volume = volume;
		}
	public int getSymbolNumber() {
		return symbolNumber;
	}
	public void setSymbolNumber(int symbolNumber) {
		this.symbolNumber = symbolNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getLastTradedPrice() {
		return lastTradedPrice;
	}
	public void setLastTradedPrice(String lastTradedPrice) {
		this.lastTradedPrice = lastTradedPrice;
	}
	public String getLastTradedVolume() {
		return lastTradedVolume;
	}
	public void setLastTradedVolume(String lastTradedVolume) {
		this.lastTradedVolume = lastTradedVolume;
	}
	public String getPercentageChange() {
		return percentageChange;
	}
	public void setPercentageChange(String percentageChange) {
		this.percentageChange = percentageChange;
	}
	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String open) {
		this.openPrice = open;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPreviousClosingPrice() {
		return previousClosingPrice;
	}
	public void setPreviousClosingPrice(String previousClosingPrice) {
		this.previousClosingPrice = previousClosingPrice;
	}

	public String getNoOfTransaction() {
		return noOfTransaction;
	}

	public void setNoOfTransaction(String noOfTransaction) {
		this.noOfTransaction = noOfTransaction;
	}

	public String getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(String closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}


	public String getTotalSharesTraded() {
		return totalSharesTraded;
	}


	public void setTotalSharesTraded(String totalSharesTraded) {
		this.totalSharesTraded = totalSharesTraded;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

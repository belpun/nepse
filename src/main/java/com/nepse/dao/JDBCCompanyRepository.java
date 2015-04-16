package com.nepse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.nepse.domain.CompanyData;

@Repository
public class JDBCCompanyRepository {
	 private static final Logger LOGGER = LoggerFactory.getLogger(JDBCCompanyRepository.class);
	 
	 private JdbcTemplate jdbcTemplate;
	
	 private static String GET_COMPANY_CLOSING_PRICE = "select data.date as dd, data.closingPrice as price from CompanyData data inner join Company com "
			 + " on data.company_id = com.id "
			 + " where com.symbol = ? ORDER BY data.date ASC";
	 
	 private static String GET_COMPANY_AND_SYMBOL = "select symbol, name from Company";

	 private static String GET_COMPANYDATA = "select cd.date, cd.openPrice, cd.closingPrice, cd.high, cd.low from CompanyData cd "
			 + " inner join Company c on c.id = cd.company_id "
			 + " where c.symbol = ? ";
	    
	 private static String GET_LATEST_DATE = "select MAX(cd.date) from Company c inner join CompanyData cd "
			 + " on c.id = cd.company_id "
			 + " where c.symbol = ? ";
	@Autowired
	public JDBCCompanyRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Map<Long, Double> getClosingPrice(String symbol) {
		
	       ResultSetExtractor<Map<Long, Double>> extractor = new ResultSetExtractor<Map<Long, Double>>() {
               @Override
               public Map<Long, Double> extractData(ResultSet rs) throws SQLException {
            	   Map<Long, Double> closingPrices = new TreeMap<Long, Double>();
                   while (rs.next()) {
                	   DateTime dt = new DateTime(rs.getDate(1).getTime());
                	   dt = dt.plusHours(12);
                	   Date date = dt.toDate();
                	   Double closingPrice = rs.getDouble(2);
                	   
                	   closingPrices.put(date.getTime(), closingPrice);
                   }
                   return closingPrices;
               }

           };
           
		return jdbcTemplate.query(GET_COMPANY_CLOSING_PRICE, new Object[]{symbol}, extractor);
	}
	
	public Map<String, String> getCompanySymbol() {
		
	       ResultSetExtractor<Map<String, String>> extractor = new ResultSetExtractor<Map<String, String>>() {
            @Override
            public Map<String, String> extractData(ResultSet rs) throws SQLException {
         	   Map<String, String> companyInfo = new TreeMap<String, String>();
                while (rs.next()) {
             	   String name = rs.getString("name");
             	   String symbol = rs.getString("symbol");
             	   
             	   companyInfo.put(symbol, name);
                }
                return companyInfo;
            }

        };
        
		return jdbcTemplate.query(GET_COMPANY_AND_SYMBOL, extractor);
	}
	
	public Map<Long, CompanyData> getCompanyData(String symbol) {
		
	     ResultSetExtractor<Map<Long, CompanyData>> extractor = new ResultSetExtractor<Map<Long, CompanyData>>() {
	    	 
         @Override
         public Map<Long, CompanyData> extractData(ResultSet rs) throws SQLException {
      	   Map<Long, CompanyData> companyInfo = new TreeMap<Long, CompanyData>();
             while (rs.next()) {
        	   DateTime dt = new DateTime(rs.getTimestamp("date").getTime());
        	   dt = dt.plusHours(12);
          	   Date date = new Date(dt.getMillis());
          	   String open = rs.getString("openPrice");
          	   String close = rs.getString("closingPrice");
          	   String high = rs.getString("high");
          	   String low = rs.getString("low");
          	   
          	   CompanyData companyData = new CompanyData();
          	   companyData.setDate(date);
          	   companyData.setOpenPrice(open);
          	   companyData.setClosingPrice(close);
          	   companyData.setHigh(high);
          	   companyData.setLow(low);
          	  
          	 companyInfo.put(date.getTime(), companyData);
             }
             return companyInfo;
         }

     };
     
		return jdbcTemplate.query(GET_COMPANYDATA,  new Object[]{symbol}, extractor);
	}
	
	public Date getlatestData(String symbol) {
	    try{
			return jdbcTemplate.queryForObject(GET_LATEST_DATE,new Object[]{symbol},  Date.class);
	    } catch (DataAccessException e) {
	    	return null;
	    }
    }
	
}

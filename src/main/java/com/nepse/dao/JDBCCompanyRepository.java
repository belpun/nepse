package com.nepse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCCompanyRepository {
	 private static final Logger LOGGER = LoggerFactory.getLogger(JDBCCompanyRepository.class);
	 private JdbcTemplate jdbcTemplate;
	
	 private static String GET_COMPANY_CLOSING_PRICE = "select data.date as dd, data.closingPrice as price from CompanyData data inner join Company com "
			 + " on data.company_id = com.id "
			 + " where com.symbol = ? ORDER BY data.date ASC";
	 
	 private static String GET_COMPANY_AND_SYMBOL = "select symbol, name from Company";
	    
	    
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
                	   
                	   Date date = rs.getDate(1);
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

}

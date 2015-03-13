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
			 + " where com.symbol = ? ";
	    
	    
	@Autowired
	public JDBCCompanyRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Map<Long, Float> getClosingPrice(String symbol) {
		
	       ResultSetExtractor<Map<Long, Float>> extractor = new ResultSetExtractor<Map<Long, Float>>() {
               @Override
               public Map<Long, Float> extractData(ResultSet rs) throws SQLException {
            	   Map<Long, Float> closingPrices = new TreeMap<Long, Float>();
                   while (rs.next()) {
                	   
                	   Date date = rs.getDate(1);
                	   Float closingPrice = rs.getFloat(2);
                	   
                	   closingPrices.put(date.getTime(), closingPrice);
                   }
                   return closingPrices;
               }

           };
           
		return jdbcTemplate.query(GET_COMPANY_CLOSING_PRICE, new Object[]{symbol}, extractor);
	}

}

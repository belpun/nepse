package com.nepse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Company")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public Company(){
		
	}
	
	public Company(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false, unique=true)
	private String symbol;

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
	
}

package com.nepse.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.nepse.config.properties.IPropertiesRepository;
import com.nepse.config.properties.PropertiesRepositoryImpl;

@Configuration
public class PropertyFilesConfig {

	@Bean
	public IPropertiesRepository customerPropertiesRepository (
			@Value("#{customerProperties}") Properties properties) {
		return new PropertiesRepositoryImpl(properties);
	}
	
	@Bean
	public PropertiesFactoryBean customerProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();

		propertiesFactoryBean.setLocations(new Resource[] {
				new ClassPathResource("properties/default-customer.properties"),
				new ClassPathResource("properties/customer.properties") });

		propertiesFactoryBean.setIgnoreResourceNotFound(true);
		return propertiesFactoryBean;
	}
}

package com.nepse.config.properties;

import java.util.Properties;

import com.nepse.exception.ConfigurationException;


public class PropertiesRepositoryImpl implements IPropertiesRepository {

	private final Properties properties;

	private String propertyPrefix = "";

	public PropertiesRepositoryImpl() {
		this.properties = new Properties();
	}

	public PropertiesRepositoryImpl(Properties properties) {
		this.properties = properties;
	}

	public PropertiesRepositoryImpl(Properties properties, String propertyPrefix) {
		this.properties = properties;
		this.propertyPrefix = propertyPrefix;
	}

	public String get(String key) {
		String value = (String) this.properties.get(key);
		if (value == null) {
			throw new ConfigurationException("Expected value for: " + key
					+ " could not be found in properties file.");
		}

		return value;
	}

	public String get(String key,String defaultValue) {
		String value = (String) this.properties.get(key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Not implemented.
	 */
	public String getByIndex(int index) {
		return (String) properties.get(this.propertyPrefix + index);
	}

	public int size() {
		return properties.size();
	}

	public boolean readBoolean(String key) {
		return this.readBoolean(key,"false");
		//		return "true".equals(this.properties.getProperty(key, "false"));
	}

	public int countNotesForPage(String pageName) {
		Object count = properties.get(pageName);
		if (count == null) {
			return 0;
		}
		return Integer.parseInt(count.toString());
	}

	public boolean readBoolean(String key, String defaultValue) {
		String result = this.properties.getProperty(key, defaultValue);
		return "true".equals(result.trim());	
	}

	public int readInt(String value, String defaultValue) {
		String sessionTimeout = this.properties.getProperty(value, defaultValue);

		return Integer.parseInt(sessionTimeout);
	}
	
	public String[] getArray(String key) {
		String value = (String) this.properties.get(key);
		if (value == null) {
			throw new ConfigurationException("Expected value for: " + key
					+ " could not be found in properties file.");
		}
		if ("".equals(value.trim())) {
			return new String[0];
		}
		value = value.replaceAll(",(\\s)+", ",");
		return value.split(",");
	}

}
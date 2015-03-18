package com.nepse.config.properties;

public interface IPropertiesRepository {

	public String get(String key);

	public String get(String key,String defaultValue);

	public String getByIndex(int index);
	
	public int size();

	public boolean readBoolean(String key);

	public boolean readBoolean(String key, String defaultValue);

	public int readInt(String value, String defaultValue);
	
	public String[] getArray(String key);
}

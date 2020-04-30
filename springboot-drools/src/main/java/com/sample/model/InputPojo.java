package com.sample.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InputPojo {

	private Map<String, String> stringColumns = new HashMap<String, String>();

	private Map<String, Integer> numberColumns = new HashMap<String, Integer>();

	private Map<String, Date> dateColumns = new HashMap<String, Date>();
	
	private Map<String, Double> doubleColumns = new HashMap<String, Double>();
	
	private Map<String, String> outputMap = new HashMap<String, String>();

	public Map<String, String> getOutputMap() {
		return outputMap;
	}

	public void setOutputMap(Map<String, String> outputMap) {

		outputMap.forEach((k, v) -> this.outputMap.put(k, v));
	}

	public Map<String, String> getStringColumns() {
		return stringColumns;
	}

	public void setStringColumns(Map<String, String> stringColumns) {
		this.stringColumns = stringColumns;
	}

	public Map<String, Integer> getNumberColumns() {
		return numberColumns;
	}

	public void setNumberColumns(Map<String, Integer> numberColumns) {
		this.numberColumns = numberColumns;
	}

	public Map<String, Date> getDateColumns() {
		return dateColumns;
	}

	public void setDateColumns(Map<String, Date> dateColumns) {
		this.dateColumns = dateColumns;
	}
	
	public Map<String, Double> getDoubleColumns() {
		return doubleColumns;
	}

	public void setDoubleColumns(Map<String, Double> doubleColumns) {
		this.doubleColumns = doubleColumns;
	}
	
/*	
	public void setValueToStringMap(String key, String value) {
		stringColumns.put(key, value);
	}
	
	public void setValueToIntegerMap(String key, String value) {
		numberColumns.put(key, Integer.parseInt(value));
	}

	public void setValueToDoubleMap(String key, String value) {
		doubleColumns.put(key, Double.parseDouble(value));
	}

	*/
}

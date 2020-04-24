package com.sample.model;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class InputPojo {

	private Map<String, String> stringColumns = Collections.EMPTY_MAP;
	
	private Map<String, Integer> numberColumns = Collections.EMPTY_MAP;

	private Map<String, Date> dateColumns = Collections.EMPTY_MAP;

	private Map<String, String> outputMap = Collections.EMPTY_MAP;
	
	public Map<String, String> getOutputMap() {
		return outputMap;
	}

	public void setOutputMap(Map<String, String> outputMap) {
		this.outputMap = outputMap;
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
}

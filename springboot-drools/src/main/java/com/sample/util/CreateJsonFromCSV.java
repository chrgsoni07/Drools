package com.sample.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CreateJsonFromCSV {

	private static void createJson() throws IOException {

		File input = new File("src/main/resources/csv/TestData1.csv");
		File output = new File("src/main/resources/csv/output.json");

		CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
		CsvMapper csvMapper = new CsvMapper();

		// Read data from CSV file
		List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

		ObjectMapper mapper = new ObjectMapper();

		// Write JSON formated data to output.json file
		mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);

		// Write JSON formated data to stdout
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
	}
	
	public static void main(String[] args) {
		try {
			createJson();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.sample.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.sample.model.InputPojo;

public class CreateRuleInputFromCSV {

	public static String[] readHeader(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine().split(",");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<InputPojo> csvReader(String path) throws IOException {
		Reader reader;
		try {
			reader = new FileReader(new File(path));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found with path specified");
		}

		List<InputPojo> inputList = new ArrayList<>();

		try {
			Iterator<Map<String, String>> iterator = new CsvMapper().readerFor(Map.class)
					.with(CsvSchema.emptySchema().withHeader()).readValues(reader);

			while (iterator.hasNext()) {
				Map<String, String> oneRow = iterator.next();

				InputPojo pojo = getInputPojoBasedOnDataType(oneRow);
				inputList.add(pojo);
			}
		} catch (IOException e) {
			throw new IOException("Error while reading tht csv file");
		}

		System.out.println("size of all inputs from pojo=>" + inputList.size());
		return inputList;
	}

	public static InputPojo getInputPojoBasedOnDataType(Map<String, String> oneRow) {
		InputPojo pojo = new InputPojo();
		Map<String, String> stringColumns = new HashMap<>();
		Map<String, Integer> numberColumns = new HashMap<>();
		Map<String, Double> doubleColumns = new HashMap<>();

		Set<String> keys = oneRow.keySet();

		keys.stream().forEach(key -> {
			String[] splitkey = key.split("-");
			String variableName = splitkey[0].trim();
			String dataType = splitkey[1].trim();

			if (dataType.equals("Double")) {
				Double value = Double.parseDouble(oneRow.get(key).trim());
				doubleColumns.put(variableName, value);
			}
			if (dataType.equals("Integer")) {
				Integer value = Integer.parseInt(oneRow.get(key).trim());
				numberColumns.put(variableName, value);
			}
			if (dataType.equals("String")) {
				String value = oneRow.get(key).trim();
				stringColumns.put(variableName, value);
			}

		});

		pojo.setStringColumns(stringColumns);
		pojo.setNumberColumns(numberColumns);
		pojo.setDoubleColumns(doubleColumns);

		return pojo;
	}

}

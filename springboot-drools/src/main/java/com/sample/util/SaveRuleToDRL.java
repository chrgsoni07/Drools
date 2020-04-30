package com.sample.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang3.RandomStringUtils;

public class SaveRuleToDRL {

	public void save(String rule) {

		BufferedWriter bufferedWriter = null;
		try {

			String strContent = rule;
			String fileName = RandomStringUtils.randomAlphanumeric(8);
			File myFile = new File("src/main/resources/rules/" + fileName + ".drl");

			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			Writer writer = new FileWriter(myFile);
			bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(strContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (Exception ex) {

			}
		}
	}
}
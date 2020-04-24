package com.sample.calling;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.model.InputPojo;

public class DroolsTestMap {

	public static final void main(String[] args) {
		try {
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			//InputPojo input = getFirstRuleInput();
			//InputPojo input = getSecondRuleInput();
			InputPojo input = getDynamicRuleInput();
			
			kSession.insert(input);

			kSession.fireAllRules();

			input.getOutputMap().forEach((k, v) -> System.out.println("Key:" + k + ", value: " + v));

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static InputPojo getSecondRuleInput() {
		InputPojo input = new InputPojo();

		Map<String, String> stringColumns = new HashMap<>();
		stringColumns.put("Type", "Indian");

		Map<String, Date> dateColumns = new HashMap<>();
		dateColumns.put("Createdate", new Date());

		input.setStringColumns(stringColumns);
		input.setDateColumns(dateColumns);
		return input;
	}

	public static InputPojo getFirstRuleInput() {
		InputPojo input = new InputPojo();

		Map<String, String> stringColumns = new HashMap<>();
		stringColumns.put("Owner", "Brun2 Booth");
		stringColumns.put("Type", "Italian");

		Map<String, Integer> numberColumns = new HashMap<>();
		numberColumns.put("Capacity", 11);
		numberColumns.put("Timezone", 100);

		input.setStringColumns(stringColumns);
		input.setNumberColumns(numberColumns);
		return input;
	}

	public static InputPojo getDynamicRuleInput() {
		InputPojo input = new InputPojo();
		
		Map<String, String> stringColumns = new HashMap<>();
		stringColumns.put("batch", "ABCD");

		Map<String, Integer> numberColumns = new HashMap<>();
		numberColumns.put("price", 11);

		input.setStringColumns(stringColumns);
		input.setNumberColumns(numberColumns);
		return input;
	}
}

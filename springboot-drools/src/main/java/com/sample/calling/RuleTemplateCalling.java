package com.sample.calling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import com.sample.model.InputPojo;
import com.sample.model.rule.Rule;
import com.sample.util.ConvertRuleToDRL;
import com.sample.util.SaveRuleToDRL;

public class RuleTemplateCalling {

	// Event parameter is an interface and OrderEvent implements it
	private static String applyRuleTemplate(List<Rule> rules) throws Exception {
		ConvertRuleToDRL ruleToDRL = new ConvertRuleToDRL();
		List<Map<String, Object>> allData = new ArrayList<>();

		for (Rule rule : rules) {
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("name", rule.getRuleName());
			data.put("condition", ruleToDRL.mergeAllConditionParts(rule.getConditions()));
			data.put("action", ruleToDRL.buildAction(rule.getActions(), rule.getRuleName()));
			data.put("salienceNumber", rule.getSalienceNumber());

			allData.add(data);
		}

		ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
		return objectDataCompiler.compile(allData,
				Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/RuleTemplate.drl"));
	}

	private static void evaluateRuleWithStatelessSession(String drl, InputPojo input) throws Exception {
		System.out.println("DRL **********\n\n" + drl);

		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write("drools/rules/RuleTemplate.drl", drl);
		kieServices.newKieBuilder(kieFileSystem).buildAll();

		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		StatelessKieSession statelessKieSession = kieContainer.getKieBase().newStatelessKieSession();

		statelessKieSession.execute(input);

		input.getOutputMap().forEach((k, v) -> System.out.println("Key:" + k + ", value: " + v));
	}

	public static InputPojo getDynamicRuleInput() {
		InputPojo input = new InputPojo();

		Map<String, String> stringColumns = new HashMap<>();
		stringColumns.put("Owner", "Bruno Booth");
		stringColumns.put("Type", "Italian");
		stringColumns.put("Group", "Group-1");

		input.setStringColumns(stringColumns);
		
		return input;
	}

	public static void executeSavedDRL(Rule rule) {
		ConvertRuleToDRL ruleToDRL = new ConvertRuleToDRL();
		String drl = ruleToDRL.getDRLFromRule(rule);

		SaveRuleToDRL saveRuleToDrl = new SaveRuleToDRL();
		saveRuleToDrl.save(drl);

		try {
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			InputPojo input = getDynamicRuleInput();

			kSession.insert(input);
			//Note: Higher salience number rule will execute first
			kSession.fireAllRules();

			input.getOutputMap().forEach((k, v) -> System.out.println("Key:" + k + ", value: " + v));

		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public static void executeDynamicRule(List<Rule> rules) {
		try {
			String dynamicDRL = applyRuleTemplate(rules);

			evaluateRuleWithStatelessSession(dynamicDRL, getDynamicRuleInput());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

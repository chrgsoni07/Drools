package com.sample.calling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import com.sample.model.InputPojo;
import com.sample.model.rule.Rule;
import com.sample.util.ConvertJsonToRule;
import com.sample.util.ConvertRuleToDRL;
import com.sample.util.SaveRuleToDRL;

public class RuleTemplateCalling {

	public static void main(String[] args) {

		ConvertJsonToRule jsonToRule = new ConvertJsonToRule();
		Rule rule = jsonToRule.getRuleFromJson();

		//NOTE refresh the project and uncomment this line
		 //executeSavedDRL(rule);
		
		
		//NOTE uncomment the part for dynamic rule calling  
		executeDynamicRule(rule);
	}

	// Event parameter is an interface and OrderEvent implements it
	private static String applyRuleTemplate(String name, String condition, String action) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);
		data.put("condition", condition);
		data.put("action", action);

		ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
		return objectDataCompiler.compile(Arrays.asList(data),
				Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/RuleTemplate.drl"));
	}

	private static void evaluateRuleWithStatelessSession(String drl, InputPojo input) throws Exception {
		System.out.println("DRL " + drl);
		
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write("src/main/resources/rules/RuleTemplate.drl", drl);
		kieServices.newKieBuilder(kieFileSystem).buildAll();

		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		StatelessKieSession statelessKieSession = kieContainer.getKieBase().newStatelessKieSession();

		statelessKieSession.execute(input);

		input.getOutputMap().forEach((k, v) -> System.out.println("Key:" + k + ", value: " + v));
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

	public static void executeSavedDRL(Rule rule) {
		ConvertRuleToDRL ruleToDRL = new ConvertRuleToDRL();
		String drl = ruleToDRL.getDRLFromRule(rule);
		
		SaveRuleToDRL saveRuleToDrl = new SaveRuleToDRL();
		//saveRuleToDrl.save(drl);

		try {
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			// InputPojo input = getFirstRuleInput();
			// InputPojo input = getSecondRuleInput();
			InputPojo input = getDynamicRuleInput();

			kSession.insert(input);

			kSession.fireAllRules();
			
			input.getOutputMap().forEach((k, v) -> System.out.println("Key:" + k + ", value: " + v));

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static void executeDynamicRule(Rule rule) {
		// Dynamic rule with template
		ConvertRuleToDRL ruleToDRL = new ConvertRuleToDRL();
		String conditions = ruleToDRL.mergeAllConditionParts(rule.getConditions());
		
		String actions = ruleToDRL.buildAction(rule.getActions());
		try {
			String dynamicDRL = applyRuleTemplate(rule.getRuleName(), conditions, actions);
			evaluateRuleWithStatelessSession(dynamicDRL, getDynamicRuleInput());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

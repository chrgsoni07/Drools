package com.sample.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.model.InputPojo;
import com.sample.model.rule.Rule;
import com.sample.service.RuleService;
import com.sample.util.ConvertJsonToRule;
import com.sample.util.ConvertRuleToDRL;
import com.sample.util.CreateRuleInputFromCSV;

@Service
public class RuleServiceImpl implements RuleService {
	private final KieContainer kieContainer;
	
	@Autowired
	public RuleServiceImpl(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}
	
	@Override
	public String createRulesFromJSon(String ruleJson) {
		String dynamicDrl = null;
		List<Rule> rules = new ArrayList<>();
		ConvertJsonToRule jsonToRule = new ConvertJsonToRule();
		rules = jsonToRule.getRulesFromJson(ruleJson);

		try {
			dynamicDrl = applyRuleTemplate(rules);
		} catch (Exception e) {
			throw new RuntimeException("Exception while appling Rules to template");
		}

		return dynamicDrl;
	}

	@Override
	public List<InputPojo> createAndExcecuteRules(String ruleJson, String filePath) {
		
		String generatedDrl = createRulesFromJSon(ruleJson);
		List<InputPojo> inputList = new ArrayList<>();

		try {
			inputList = CreateRuleInputFromCSV.csvReader(filePath);
		} catch (IOException e) {
			throw new RuntimeException("Exception while reading the csv file");
		}

		return inputList.stream()
				.map(input -> evaluateRuleWithStatelessSession(generatedDrl, input))
				.collect(Collectors.toList());
	
	}

	private InputPojo evaluateRuleWithStatelessSession(String drl, InputPojo input) {
		System.out.println("Generated DRL => \n" + drl);
		
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write("src/main/resources/rules/RuleTemplate.drl", drl);
		kieServices.newKieBuilder(kieFileSystem).buildAll();


		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		StatelessKieSession statelessKieSession = kieContainer.getKieBase().newStatelessKieSession();

		statelessKieSession.execute(input);
		
		//input.getOutputMap().forEach((k, v) -> System.out.println("Key:" + k + ", value: " + v));
		
		return input;
	}

	private String applyRuleTemplate(List<Rule> rules) throws Exception {
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
		return objectDataCompiler.compile(allData, Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("templates/RuleTemplate.drl"));
	}

}

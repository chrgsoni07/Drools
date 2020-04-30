package com.sample.service;

import java.util.List;

import com.sample.model.InputPojo;

public interface RuleService {

	String createRulesFromJSon(String ruleJson);

	List<InputPojo> createAndExcecuteRules(String ruleJson, String filePath);
}

package com.sample.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.service.RuleService;
import com.sample.model.InputPojo;

@RestController
@RequestMapping("/drools")
public class RuleController {
	private RuleService ruleService;

	public RuleController(RuleService ruleService) {
		this.ruleService = ruleService;
	}
	
	@PostMapping("/create-rule")
	public String createRule(@RequestBody String ruleJson) {
		
		return ruleService.createRulesFromJSon(ruleJson);
	}

	//specify file location from src folder ex: src/main/resources/csv/TestData1.csv
	@PostMapping("/execute-rule")
	public List<InputPojo> loadCsvFileAndExecute(@RequestParam(value = "path") String filePath, @RequestBody String ruleJson){
		
		return ruleService.createAndExcecuteRules(ruleJson, filePath);
	}
	
}

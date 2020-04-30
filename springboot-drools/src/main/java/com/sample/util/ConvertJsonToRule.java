package com.sample.util;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sample.model.rule.Action;
import com.sample.model.rule.Condition;
import com.sample.model.rule.Rule;

import java.util.List;

public class ConvertJsonToRule {

/*	private final String ruleJson = "{'data':["

			+ "{'name' : 'rule1','object' : 'Product', 'salienceNumber' : '100'," 
			+ "'conditions' : [{'property' : 'Owner','value' : 'Bruno Booth','operator' : 'EQUAL_TO', 'datatype' : 'string'}, "
			+ "{'property' : 'Type','value' : 'Italian','operator' : 'EQUAL_TO', 'datatype' : 'string'}]"
			+ ", 'actions' : [{'action' : 'Region','actionarg' : 'Region-77'}, {'action' : 'State','actionarg' : 'CA'},"
			+ "{'action' : 'IsUpdated','actionarg' : 'Yes'}]" 
			+ "},"

			+ "{'name' : 'rule2','object' : 'Product', 'salienceNumber' : '500', "
			+ "'conditions' : [{'property' : 'Group','value' : 'Group-1','operator' : 'EQUAL_TO', 'datatype' : 'string'}]"
			+ ", 'actions' : [{'action' : 'Owner','actionarg' : 'Raj Singh'}, {'action' : 'IsUpdated','actionarg' : 'Yes'}]"
			+ "}"

			+ "]}";
*/
	
	public List<Rule> getRulesFromJson(String ruleJson) {

		JsonObject jsonObject = new Gson().fromJson(ruleJson, JsonObject.class);
		JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

		List<Rule> rules = new ArrayList<>();

		for (JsonElement element : dataArray) {
			JsonObject singleRuleJson = element.getAsJsonObject();

			Rule generatedRule = getRule(singleRuleJson);
			rules.add(generatedRule);
		}

		return rules;
	}

	public Rule getRule(JsonObject ruleJson) {

		JsonObject jsonObject = new Gson().fromJson(ruleJson, JsonObject.class);

		String ruleName = getRuleName(jsonObject);
		String objName = getClassName(jsonObject);
		int salienceNumber = getSalienceNumber(jsonObject);
		
		List<Condition> conditions = getConditions(jsonObject);

		List<Action> actions = getActions(jsonObject);

		Rule rule = new Rule();
		rule.setRuleName(ruleName);
		rule.setObjectName(objName);
		rule.setSalienceNumber(salienceNumber);
		rule.setConditions(conditions);
		rule.setActions(actions);

		return rule;
	}

	private String getRuleName(JsonObject jsonObject) {

		return jsonObject.get("name").getAsString();
	}

	private int getSalienceNumber(JsonObject jsonObject) {
		
		return jsonObject.get("salienceNumber").getAsInt();
	}
	
	private static String getClassName(JsonObject jsonObject) {

		return jsonObject.get("object").getAsString();
	}

	private List<Condition> getConditions(JsonObject jsonObject) {
		JsonArray conditionJson = jsonObject.get("conditions").getAsJsonArray();

		List<Condition> conditions = new ArrayList<>();

		for (JsonElement element : conditionJson) {
			Condition con = new Condition();
			JsonObject jObjects = element.getAsJsonObject();

			con.setFieldName(jObjects.get("property").getAsString());
			con.setFieldValue(jObjects.get("value").getAsString());
			con.setOperatorType(jObjects.get("operator").getAsString());
			con.setFieldType(jObjects.get("datatype").getAsString());

			conditions.add(con);
		}

		return conditions;
	}

	public List<Action> getActions(JsonObject jsonObject) {
		JsonArray actionJson = jsonObject.get("actions").getAsJsonArray();

		List<Action> actions = new ArrayList<>();

		for (JsonElement element : actionJson) {
			Action action = new Action();
			JsonObject jObjects = element.getAsJsonObject();

			action.setFieldName(jObjects.get("action").getAsString());
			action.setFieldValue(jObjects.get("actionarg").getAsString());

			actions.add(action);
		}

		return actions;
	}
}
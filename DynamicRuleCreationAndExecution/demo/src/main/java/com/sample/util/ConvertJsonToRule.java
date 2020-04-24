package com.sample.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sample.model.rule.Action;
import com.sample.model.rule.Condition;
import com.sample.model.rule.Rule;

import java.util.List;

public class ConvertJsonToRule {

	private final  String ruleJson = "{'name' : 'test1','object' : 'Product', 'conditions' : "
			+ "[{'property' : 'price','value' : '5','operator' : 'NOT_EQUAL_TO', 'datatype' : 'number'}, "
			+ "{'property' : 'batch','value' : 'ACB25','operator' : 'NOT_EQUAL_TO', 'datatype' : 'string'}]"
			+ ", 'action' : 'discount','actionarg' : '10'}";

	public  Rule getRuleFromJson() {

		JsonObject jsonObject = new Gson().fromJson(ruleJson, JsonObject.class);

		String ruleName = getRuleName(jsonObject);
		String objName = getClassName(jsonObject);

		List<Condition> conditions = getConditions(jsonObject);

		List<Action> actions = getActions(jsonObject);

		Rule rule = new Rule();
		rule.setRuleName(ruleName);
		rule.setObjectName(objName);
		rule.setConditions(conditions);
		rule.setActions(actions);

		return rule;
	}

	private String getRuleName(JsonObject jsonObject) {

		return jsonObject.get("name").getAsString();
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
		Action action = new Action();
		action.setFieldName(jsonObject.get("action").getAsString());
		action.setFieldValue(jsonObject.get("actionarg").getAsString());

		return Arrays.asList(action);
	}
}
package com.sample.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import com.sample.model.rule.Action;
import com.sample.model.rule.Condition;
import com.sample.model.rule.Rule;

public class ConvertRuleToDRL {
	Map<String, String> dataTypeMap = new HashedMap<>();
	Map<String, String> operatorMap = new HashedMap<>();

	public ConvertRuleToDRL() {
		dataTypeMap.put("number", "numberColumns");
		dataTypeMap.put("string", "stringColumns");
		dataTypeMap.put("date", "dateColumns");

		operatorMap.put("NOT_EQUAL_TO", "!=");
		operatorMap.put("GREATER_THAN", ">");
		operatorMap.put("EQUAL_TO", "==");
	}

	private String publicImports = "package com.sample\r\n" + " \r\n" + "import java.util.Map;\r\n"
			+ "import java.util.HashMap;\r\n" + "import com.sample.model.InputPojo;\r\n" + "import java.util.Date;\r\n"
			+ "import static com.sample.util.Util.isDateAfter;\r\n" + "import java.util.Collections;\r\n\n\n" + " ";

	private String ruleFormat = "rule \"%s\"\n when \n %s  \n then \n %s \n end";
	private String nullCheckFormat = "$%s.get(\"%s\") != null";

	private String stringConditionFormat = "( $%s.get(\"%s\") %s \"%s\" )";
	private String numberConditionFormat = "( (int) $%s.get(\"%s\") %s %s )";
	private String actionFormat = "output.put(\"%s\", \"%s\"); \n";
	private String stringEqualsConditionFormat = "$%s.get(\"%s\").equals(\"%s\")";

	public String getDRLFromRule(Rule rule) {

		List<Condition> conditions = rule.getConditions();

		// Conditions
		String complateCondition = mergeAllConditionParts(conditions);

		// actions
		String actionResult = buildAction(rule.getActions(), rule.getRuleName());

		// createRule
		StringBuilder ruleBuilder = new StringBuilder();
		ruleBuilder.append(publicImports);
		ruleBuilder.append(String.format(ruleFormat, rule.getRuleName(), complateCondition, actionResult));

		return ruleBuilder.toString();

	}

	public String mergeAllConditionParts(List<Condition> conditions) {
		// haader
		StringBuilder conditionBuilder = new StringBuilder();

		String headerResult = buildRuleHeader(conditions);
		conditionBuilder.append(headerResult);
		conditionBuilder.append("\n");

		// null check
		String nullResult = buildNullCondision(conditions);
		conditionBuilder.append(nullResult);
		conditionBuilder.append("\n");

		// mainCondition
		String mainConditionResult = buildMainCondition(conditions);
		conditionBuilder.append(mainConditionResult);

		return conditionBuilder.toString();
	}

	private String buildRuleHeader(List<Condition> conditions) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("$inputPojo : InputPojo (");

		List<String> columnType = conditions.stream().map(con -> dataTypeMap.get(con.getFieldType())).distinct()
				.collect(Collectors.toList());

		List<String> header = columnType.stream().map(column -> ("$" + column + ":" + column))
				.collect(Collectors.toList());
		buffer.append(String.join(",", header));
		buffer.append(")");

		return buffer.toString();
	}

	private String buildNullCondision(List<Condition> conditions) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("eval ( ");

		List<String> nullList = conditions.stream()
				.map(con -> String.format(nullCheckFormat, dataTypeMap.get(con.getFieldType()), con.getFieldName()))
				.collect(Collectors.toList());

		buffer.append(String.join(" && ", nullList));
		buffer.append(" )");

		return buffer.toString();
	}

	public String buildMainCondition(List<Condition> conditions) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("eval ( ");

		List<String> builtConditons = conditions.stream().map(con -> buildConditionBasedOnType(con))
				.collect(Collectors.toList());

		buffer.append(String.join(" && ", builtConditons));
		buffer.append(" )");
		return buffer.toString();
	}

	public String buildAction(List<Action> actions, String ruleName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Map<String, String> output =  new HashMap<String, String>(); \n");

		List<String> builtActions = actions.stream()
				.map(act -> String.format(actionFormat, act.getFieldName(), act.getFieldValue()))
				.collect(Collectors.toList());
		
		//add rule name to action for logging of rules
		builtActions.add("output.put(\"rule\", \"" + ruleName + "\");\n");
		
		buffer.append(String.join("", builtActions));
		buffer.append("$inputPojo.setOutputMap(output);");
		return buffer.toString();
	}

	private String buildConditionBasedOnType(Condition condition) {
		if (condition.getFieldType().equals("string")) {
			
			if (condition.getOperatorType().equals("EQUAL_TO")) {
				return String.format(stringEqualsConditionFormat, dataTypeMap.get(condition.getFieldType()), condition.getFieldName(), condition.getFieldValue());
			} else {
				return String.format(stringConditionFormat, dataTypeMap.get(condition.getFieldType()),
						condition.getFieldName(), operatorMap.get(condition.getOperatorType()),
						condition.getFieldValue());
			}
			
		} else {
			return String.format(numberConditionFormat, dataTypeMap.get(condition.getFieldType()),
					condition.getFieldName(), operatorMap.get(condition.getOperatorType()), condition.getFieldValue());
		}
	}
}

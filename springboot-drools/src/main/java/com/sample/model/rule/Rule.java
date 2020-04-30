package com.sample.model.rule;

import java.util.List;

public class Rule {

	private String ruleName;

	private int salienceNumber;
	
	private String objectName;
	
	private List<Condition> conditions;
	
	private List<Action> actions;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getSalienceNumber() {
		return salienceNumber;
	}

	public void setSalienceNumber(int salienceNumber) {
		this.salienceNumber = salienceNumber;
	}

}

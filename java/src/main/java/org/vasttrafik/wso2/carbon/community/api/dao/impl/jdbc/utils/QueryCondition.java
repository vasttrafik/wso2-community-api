package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils;

import java.util.Map;

/**
 * 
 * @author Lars Andersson
 *
 */
public class QueryCondition {
	
	private String concatenation;
	private String attribute;
	private String operator;
	private String value;
	private String columnName;
	
	public QueryCondition(String condition, Map<String, String> columnMappings) throws IllegalArgumentException {
		parse(condition, columnMappings);
	}

	public String getConcatenation() {
		return concatenation;
	}

	public void setConcatenation(String concatenation) {
		this.concatenation = concatenation;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String toSQL(String tableAlias) throws IllegalArgumentException {
		StringBuffer sb = new StringBuffer(concatenation);
		
		if (concatenation != null)
			sb.append(" ");
		
		if (tableAlias != null)
			sb.append(tableAlias + ".");
		
		sb.append(columnName);
		sb.append(" ");
		sb.append(operator);
		
		if (value != null) {
			sb.append(" ? ");
		}
		
		return sb.toString();
	}
	
	/**
	 * Parses a query condition in the form and/or;attribute:operator:value
	 * @param condition The condition to parse
	 * @throws IllegalArgumentException
	 */
	private void parse(String condition, Map<String, String> columnMappings) throws IllegalArgumentException {
		// Check for null or empty strings
		if (condition == null || "".equals(condition))
			throw new IllegalArgumentException("Condition is null or empty string");
		
		// Should be a semicolon in the string
		String[] parts = condition.split(";");
		
		if (parts.length != 2)
			throw new IllegalArgumentException("Condition is not right format");
		
		// The first part, before the semicolon is the concatenation part,
		// which may be empty.
		concatenation = parts[0];
		// Split the rest of the string
		parts = parts[1].split(":");
		
		// There should be 2 or 3 parts
		if (parts.length < 2 || parts.length > 3)
			throw new IllegalArgumentException("Condition consists of " + parts.length + " parts");
		
		// Get the parts
		attribute = parts[0];
		operator  = parts[1];
		
		if (parts.length == 3)
			value = parts[2];
		
		// Verify that the attribute is valid
		if (!columnMappings.containsKey(attribute))
			throw new IllegalArgumentException("Attribute " + attribute + " is not supported");
		else
			columnName = columnMappings.get(attribute);
	}
}

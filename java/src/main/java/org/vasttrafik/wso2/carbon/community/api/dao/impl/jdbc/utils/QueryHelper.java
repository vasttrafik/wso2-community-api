package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Lars Andersson
 *
 */
public class QueryHelper {
	
	/**
	 * The sql table or view name being queried
	 */
	private String tableName = null;
	
	/**
	 * List of query conditions
	 */
	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();
	
	private static final Log log = LogFactory.getLog(QueryHelper.class);
	
	/**
	 * Constructor.
	 * @param tableName The table or view being queried
	 * @param query A query in the format [AND/OR];attribute:operator[:value][#AND/OR;attribute:operator[:value]]
	 * @throws IllegalArgumentException
	 */
	public QueryHelper(String tableName, String query, Map<String, String> columnMappings) throws IllegalArgumentException {
		this.tableName = tableName;
		
		try {
			// Parse the query string into conditions
			parse(query, columnMappings);
		}
		catch (IllegalArgumentException ia) {
			log.error(ia);
			throw ia;
		}
	}
	
	/**
	 * Retrieve the list of conditions
	 * @return A list containing the conditions of the query
	 */
	public List<QueryCondition> getConditions() {
		return conditions;
	}
	
	/**
	 * Convert the conditions into SQL conditions in the format [column_name operator [?]] AND/OR [column_name operator [?]]
	 * @param columnMappings A map containing mappings from attribute names to column names
	 * @return A string containing the conditions of the SQL query
	 * @throws IllegalArgumentException
	 */
	public String toSQL(String tableAlias) throws IllegalArgumentException {
		StringBuffer sb = new StringBuffer();
		for (QueryCondition condition : conditions) {
			sb.append(condition.toSQL(tableAlias));
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * Binds the values of the conditions
	 * @param statement A prepared statement
	 * @return The column offset after all the values have been bound
	 */
	public int bindValues(MetaData metaData, PreparedStatement statement) throws SQLException {
		int parameterIndex = 1;
		
		for (QueryCondition condition : conditions) {
			// Get the condition value
			String value = condition.getValue();
			
			// Only bind if there is a value present
			if (value != null) {
				// Get the column name
				String columnName = condition.getColumnName();
				// Get the sql type for the column
				int targetSqlType = MetaData.getSQLType(tableName, columnName);
				// Get the appropriate Java class for the value
				Class<?> javaClass = MetaData.getJavaClass(tableName, columnName);
				// Create a value
				Object val = getTypeValue(javaClass, value);
				// Bind it
				statement.setObject(parameterIndex++, val, targetSqlType);
			}
		}
		return parameterIndex;
	}
	
	private Object getTypeValue(Class<?> typeClass, String value) throws IllegalArgumentException {
		try {
			// Date types can not be instantiated from strings
			if (typeClass.getSuperclass().equals(Date.class)) {
				// Parse the string as a date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date d = sdf.parse(value);
				// Convert to long
				long time = d.getTime();
				// Get a reference to the right constructor
				Constructor<?> constructor = typeClass.getConstructor(long.class);
				// And I can use that Constructor to instantiate the class
				return constructor.newInstance(time);
				
			}
			else {
				// Get a reference to the right constructor
				Constructor<?> constructor = typeClass.getConstructor(String.class);
				// And I can use that Constructor to instantiate the class
				return constructor.newInstance(value);
			}
		} 
		catch (NoSuchMethodException | SecurityException e) {
			log.error("getTypeValue:" + e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
		catch (ParseException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("getTypeValue:" + e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}		
	}
	
	/**
	 * Parses the query into separate query conditions
	 * @param query
	 * @throws IllegalArgumentException
	 */
	private void parse(String query, Map<String, String> columnMappings) throws IllegalArgumentException {
		if (query == null || "".equals(query))
			throw new IllegalArgumentException("Query is null or empty string");
		
		String[] lines = query.split("#");
		
		for (String line : lines) {
			QueryCondition condition = new QueryCondition(line, columnMappings);
			conditions.add(condition);
		}
	}
}

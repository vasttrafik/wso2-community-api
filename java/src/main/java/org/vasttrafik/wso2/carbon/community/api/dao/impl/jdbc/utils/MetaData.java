package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Singleton class to access table and column meta data.
 * 
 * @author Lars Andersson
 *
 */
public class MetaData {
	
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(MetaData.class);
	
	/**
	 * Class to store column meta data
	 *
	 */
	private static class Column {
		
		/**
		 * Column name
		 */
		private String name;
		/**
		 * Column type
		 */
		private int type;
		
		private Column(String name, int type) {
			this.setName(name);
			this.setType(type);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}
	
	/**
	 * Class to store table meta data
	 *
	 */
	private static class Table {
		
		/**
		 * Table name
		 */
		@SuppressWarnings("unused")
		private String name;
		/**
		 * Maps column names to columns
		 */
		private Map<String, Column> columns = null;
		
		private Table(String name, Map<String, Column> columns) {
			this.name = name;
			this.columns = columns;
		}
		
		public Column getColumn(String columnName) {
			if (columns != null)
				return columns.get(columnName);
			else
				return null;
		}
	}
	
	/**
	 * Database metadata
	 */
	private static DatabaseMetaData metadata = null;
	
	/**
	 * Instance of this singleton
	 */
	private static MetaData instance = null;
	
	/**
	 * Map of table names to table meta data
	 */
	private static Map<String, Table> tables = new Hashtable<String, Table>();
	
	/**
	 * Map of SQL to Java type mappings
	 */
	private static Map<Integer, Class<?>> typeMappings = getTypeMappings();
	
	/**
	 * Default constructor
	 * @param connection A database connection
	 * @throws SQLException If a MetaData object could not be retrieved from
	 * the connection.
	 */
	private MetaData(Connection connection) throws SQLException {
		metadata = connection.getMetaData();
	}
	
	/**
	 * Retrieves the singleton object
	 * @param connection A database connection
	 * @return The singleton instance of this class
	 * @throws SQLException If a MetaData object could not be retrieved from
	 * the connection
	 */
	public static MetaData getInstance(Connection connection) throws SQLException {
		if (instance == null) {
			instance = new MetaData(connection);
		}
		return instance;
	}
	
	/**
	 * Retrieves the SQL type for the specified column.
	 * @param tableName The database table name
	 * @param columnName The database column name
	 * @return The SQL type for the database column
	 * @throws SQLException
	 */
	public static int getSQLType(String tableName, String columnName) throws SQLException {
		// Get the column 
		Column column = getColumn(tableName, columnName);
		// Get the sql type
		return column.getType();
	}
	
	/**
	 * Retrieves the appropriate Java class for the specified column.
	 * @param tableName The database table name
	 * @param columnName The database column name
	 * @return The Java class appropriate for the database column
	 * @throws SQLException
	 */
	public static Class<?> getJavaClass(String tableName, String columnName) throws SQLException {
		// Get the column 
		Column column = getColumn(tableName, columnName);
		// Get the sql type
		int sqlType = column.getType();
		// Get the Java class
		return typeMappings.get(sqlType);
	}
	
	private static Column getColumn(String tableName, String columnName) throws SQLException {
		// Get the table from the map
		Table table = tables.get(tableName.toUpperCase());
				
		// If the table was not found, retrieve table meta data
		if (table == null)
			table = getTableMetaData(tableName);
		
		if (table == null)
			throw new SQLException("No such table name:" + tableName);
				
		// Get the column from the meta data
		Column column = table.getColumn(columnName.toUpperCase());
				
		if (column == null)
			throw new SQLException("No such column name:" + columnName);
		
		return column;
	}
	
	/**
	 * Retrieves column meta data for the specified table.
	 * @param tableName The database table name
	 * @return An instance of the Table class that holds the column information
	 * @throws SQLException
	 */
	private static Table getTableMetaData(String tableName) throws SQLException {
		Map<String, Column> columns = new Hashtable<String, Column>();
		// Store as uppercase
		String uppercase = tableName.toUpperCase();
		// Get column meta data
		ResultSet rs = metadata.getColumns(null, null, uppercase, null);

		// Get the columns
	    while (rs.next()) {
	    	Column column = new Column(
	    			rs.getString("COLUMN_NAME"), 
	    			rs.getInt("DATA_TYPE"));
	    	columns.put(column.getName().toUpperCase(), column);
	     }
	     
	     Table table = new Table(uppercase, columns);
	     tables.put(uppercase, table);
	     return table;
	}

	private static Map<Integer, Class<?>> getTypeMappings() {
		if (typeMappings == null) {
			typeMappings = new Hashtable<Integer, Class<?>>();
			typeMappings.put(Types.BIGINT, Long.class);
			typeMappings.put(Types.BIT, Boolean.class);
			typeMappings.put(Types.CHAR, String.class);
			typeMappings.put(Types.DATE, Date.class);
			typeMappings.put(Types.DECIMAL, BigDecimal.class);
			typeMappings.put(Types.DOUBLE, Double.class);
			typeMappings.put(Types.FLOAT, Double.class);
			typeMappings.put(Types.INTEGER, Integer.class);
			typeMappings.put(Types.NCHAR, String.class);
			typeMappings.put(Types.NVARCHAR, String.class);
			typeMappings.put(Types.REAL, Float.class);
			typeMappings.put(Types.SMALLINT, Short.class);
			typeMappings.put(Types.TIME, Time.class);
			typeMappings.put(Types.TIMESTAMP, Timestamp.class);
			typeMappings.put(Types.TINYINT, Byte.class);
			typeMappings.put(Types.VARCHAR, String.class);
		}
		return typeMappings;
	}
}

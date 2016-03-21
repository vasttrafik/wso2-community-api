package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

/**
 * DataSource provider
 * 
 * @author Lars Andersson
 *
 */
public class DataSourceProvider {
	
	private final static String DS_NAME = "jdbc/WSO2UM_DB";
	
	/**
	 * The unique DataSource instance (singleton)
	 */
	private static DataSource dataSource = createDataSource() ;
	
	public static DataSource getDataSource() {
		return dataSource;
	}

	private static DataSource createDataSource() {
		try {
			Context ctx = new InitialContext();
            return (DataSource)ctx.lookup(DS_NAME);
        }
		catch (NamingException e) {
			throw new RuntimeException("Error while looking up the data source: " + DS_NAME, e);
        }
	}
}

package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Generic DAO interface intended for use by DAO implementations that require the re-use of connections across
 * DAO:s to support the execution of multiple statements as a single transaction.
 * 
 * @author Lars Andersson
 *
 */
public interface TransactionalDAO {
	
	/**
	 * Retrieves the connection currently assigned to the DAO. If one does not exist, retrieves a connection from
	 * the data source.
	 * @param autoCommit If the connection should be set to auto commit mode
	 * @return A database connection
	 * @throws SQLException If a database connection could not be retrieved
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * Sets the connection currently assigned to the DAO. 
	 * @param connection A database connection
	 */
	public void setConnection(Connection connection);
	
	/**
	 * Sets the auto commit mode for connections
	 * @param autoCommit If connections should be set to auto commit mode
	 */
	public void setAutoCommit(boolean autocommit);
	
	/**
	 * Commits the current transaction
	 * @param closeConnection Flag indicating if the connection should be closed
	 * @throws SQLException If the current transaction could not be committed
	 */
	public void commitTransaction(boolean closeConnection) throws SQLException;
	
	/**
	 * Rollbacks the current transaction
	 * @throws SQLException If the current transaction could not be committed
	 */
	public void rollbackTransaction(boolean closeConnection) throws SQLException;
}

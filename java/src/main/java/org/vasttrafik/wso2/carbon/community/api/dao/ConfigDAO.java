package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.model.ConfigurationDTO;

/**
 * ConfigDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface ConfigDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public ConfigurationDTO find(Integer id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param configDTO
	 * @return true if found, false if not found
	 */
	public boolean load(ConfigurationDTO configDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param configDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(ConfigurationDTO configDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param configDTO
	 * @return
	 */
	public int update(ConfigurationDTO configDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id
	 * @return
	 */
	public int delete(Integer id) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param configDTO
	 * @return
	 */
	public int delete(ConfigurationDTO configDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id
	 * @return
	 */
	public boolean exists(Integer id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param configDTO
	 * @return
	 */
	public boolean exists(ConfigurationDTO configDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

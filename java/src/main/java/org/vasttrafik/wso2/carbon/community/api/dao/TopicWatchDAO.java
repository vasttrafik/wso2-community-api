package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.model.TopicWatchDTO;

/**
 * TopicWatchDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface TopicWatchDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public TopicWatchDTO find(Long id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param topicWatchDTO
	 * @return true if found, false if not found
	 */
	public boolean load(TopicWatchDTO topicWatchDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param topicWatchDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Long insert(TopicWatchDTO topicWatchDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param topicWatchDTO
	 * @return
	 */
	public int update(TopicWatchDTO topicWatchDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id
	 * @return
	 */
	public int delete(Long id) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param topicWatchDTO
	 * @return
	 */
	public int delete(TopicWatchDTO topicWatchDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id
	 * @return
	 */
	public boolean exists(Long id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param topicWatchDTO
	 * @return
	 */
	public boolean exists(TopicWatchDTO topicWatchDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

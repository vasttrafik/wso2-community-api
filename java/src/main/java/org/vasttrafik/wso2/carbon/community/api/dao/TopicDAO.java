package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.community.api.model.TopicDTO;

/**
 * TopicDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface TopicDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public TopicDTO find(Long id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * List topics by forum 
	  * @param ifModifiedSince
	  * @param forumId
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<TopicDTO> findByForum(Integer forumId, String ifModifiedSince, Integer offset, Integer limit) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
      * List topics by label 
	  * @param label
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
    public List<TopicDTO> findByLabel(String label, Integer offset, Integer limit) throws SQLException;
    
	//----------------------------------------------------------------------
	/**
      * List topics by query
	  * @param query
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
    public List<TopicDTO> findByQuery(String query, Integer offset, Integer limit) throws SQLException;
    
	//----------------------------------------------------------------------
	/**
      * List topics
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
    public List<TopicDTO> find(Integer offset, Integer limit) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param topicDTO
	 * @return true if found, false if not found
	 */
	public boolean load(TopicDTO topicDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param topicDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Long insert(TopicDTO topicDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param topicDTO
	 * @return
	 */
	public int update(TopicDTO topicDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Updates the topic subject
	 * @param topicDTO
	 * @return
	 */
	public int updateSubject(AuthenticatedUser user, TopicDTO topicDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Increments the number of views on a topic by one
	 * @param topicDTO
	 * @return
	 */
	public int incrementViews(TopicDTO topicDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Closes the topic
	 * @param topicDTO
	 * @return
	 */
	public int closeTopic(TopicDTO topicDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param comId
	 * @return
	 */
	public int delete(Long id) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param topicDTO
	 * @return
	 */
	public int delete(TopicDTO topicDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param comId
	 * @return
	 */
	public boolean exists(Long id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param topicDTO
	 * @return
	 */
	public boolean exists(TopicDTO topicDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

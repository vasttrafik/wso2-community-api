package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;

/**
 * ForumDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface ForumDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public ForumDTO find(Integer id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * Find a forum by name
	  * @param name
	  * @return the bean found or null if not found 
	  */
	public List<ForumDTO> findByName(String name) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * List forums by label
	  * @param label
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<ForumDTO> findByLabel(String label, Integer offset, Integer limit) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public List<ForumDTO> findByCategory(Integer categoryId, String ifModifiedSince, Integer offset, Integer limit) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * List forums
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<ForumDTO> find(Integer offset, Integer limit) throws SQLException;
	
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param forumDTO
	 * @return true if found, false if not found
	 */
	public boolean load(ForumDTO forumDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param forumDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(ForumDTO forumDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param forumDTO
	 * @return
	 */
	public int update(ForumDTO forumDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id
	 * @return
	 */
	public int delete( Integer id) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param forumDTO
	 * @return
	 */
	public int delete(ForumDTO forumDTO) throws SQLException;

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
	 * @param forumDTO
	 * @return
	 */
	public boolean exists(ForumDTO forumDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

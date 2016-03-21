package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;

/**
 * PostDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface PostDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public PostDTO find(Long id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * Finds posts by label 
	  * @param label
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<PostDTO> findByLabel(String label, Integer offset, Integer limit) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	 * Finds posts by label 
	 * @param label
	 * @param offset
	 * @param limit
	 * @return the bean found or null if not found 
	 */
	public List<PostDTO> findByQuery(String query, Integer offset, Integer limit) throws SQLException;	
	
	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public List<PostDTO> findByTopic(Long topicId, String ifModifiedSince, Integer offset, Integer limit) throws SQLException;


	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param postDTO
	 * @return true if found, false if not found
	 */
	public boolean load(PostDTO postDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param postDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Long insert(PostDTO postDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param postDTO
	 * @return
	 */
	public int update(PostDTO postDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Updates the post and marks it as having been answered 
	 * @param postDTO
	 * @return
	 */
	public int markAsAnswer(PostDTO postDTO) throws SQLException;

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
	 * @param postDTO
	 * @return
	 */
	public int delete(PostDTO postDTO) throws SQLException;

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
	 * @param postDTO
	 * @return
	 */
	public boolean exists(PostDTO postDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

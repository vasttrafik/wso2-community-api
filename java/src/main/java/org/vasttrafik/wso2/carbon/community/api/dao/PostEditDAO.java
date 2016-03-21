package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.PostEditDTO;

/**
 * PostEditDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface PostEditDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public PostEditDTO find(Long id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * Lists all edits belonging to a post in descending version order  
	  * @param postId
	  * @return the bean found or null if not found 
	  */
	public List<PostEditDTO> findByPost(Long postId) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param postEditDTO
	 * @return true if found, false if not found
	 */
	public boolean load(PostEditDTO postEditDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param postEditDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Long insert(PostEditDTO postEditDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param postEditDTO
	 * @return
	 */
	public int update(PostEditDTO postEditDTO) throws SQLException;

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
	 * @param postEditDTO
	 * @return
	 */
	public int delete(PostEditDTO postEditDTO) throws SQLException;

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
	 * @param postEditDTO
	 * @return
	 */
	public boolean exists(PostEditDTO postEditDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

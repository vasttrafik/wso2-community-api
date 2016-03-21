package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.TagDTO;

/**
 * TagDTO DAO interface
 * 
 * @author Lars Andersson
 **/
public interface TagDAO extends TransactionalDAO {
	
	//----------------------------------------------------------------------
	/**
	  * Finds a bean by its primary key 
	  * @param id
	  * @return the bean found or null if not found 
	  */
	public TagDTO find(Integer id) throws SQLException;
		
	//----------------------------------------------------------------------
	/**
	 * Finds all tags within specified range 
	 * @param offset
	 * @param limit
	 * @return the bean found or null if not found 
	 */
	public List<TagDTO> findAll(Integer offset, Integer limit) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param tag
	 * @return true if found, false if not found
	 */
	public boolean load(TagDTO tagDTO) throws SQLException;
		
	//----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param tag
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(TagDTO tagDTO) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param tag
	 * @return
	 */
	public int update(TagDTO tagDTO) throws SQLException;

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
	 * @param tag
	 * @return
	 */
	public int delete(TagDTO tagDTO) throws SQLException;

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
	 * @param tag
	 * @return
	 */
	public boolean exists(TagDTO tagDTO) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

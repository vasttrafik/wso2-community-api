package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.CategoryDTO;

/**
 * CategoryDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface CategoryDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public CategoryDTO find(Integer id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	 * List categories
	 * @param includePrivate Flag indicating if private categories should be included
	 * @param includeForums Flag indicating if forums should be included in the result
	 * @return the bean found or null if not found 
	 */
	public List<CategoryDTO> find(Boolean includePrivate) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param category
	 * @return true if found, false if not found
	 */
	public boolean load(CategoryDTO categoryDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param category
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(CategoryDTO categoryDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param category
	 * @return
	 */
	public int update(CategoryDTO categoryDTO) throws SQLException;

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
	 * @param category
	 * @return
	 */
	public int delete(CategoryDTO categoryDTO) throws SQLException;

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
	 * @param category
	 * @return
	 */
	public boolean exists(CategoryDTO categoryDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

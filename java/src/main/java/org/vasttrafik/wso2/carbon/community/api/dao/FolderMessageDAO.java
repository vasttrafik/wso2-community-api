package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.model.FolderMessageDTO;

/**
 * FolderMessageDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface FolderMessageDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public FolderMessageDTO find(Integer id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param folderMessageDTO
	 * @return true if found, false if not found
	 */
	public boolean load(FolderMessageDTO folderMessageDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param folderMessageDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(FolderMessageDTO folderMessageDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param folderMessageDTO
	 * @return
	 */
	public int update(FolderMessageDTO folderMessageDTO) throws SQLException;

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
	 * @param folderMessageDTO
	 * @return
	 */
	public int delete(FolderMessageDTO folderMessageDTO) throws SQLException;

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
	 * @param folderMessageDTO
	 * @return
	 */
	public boolean exists(FolderMessageDTO folderMessageDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.model.ReportMeasureDTO;

/**
 * ReportMeasureDTO DAO interface
 * 
 * @author Lars Andersson
 **/
public interface ReportMeasureDAO extends TransactionalDAO {
	
	//----------------------------------------------------------------------
	/**
	* Finds a bean by its primary key 
	* @param id
	* @return the bean found or null if not found 
	*/
	public ReportMeasureDTO find(Integer id) throws SQLException;

	//----------------------------------------------------------------------
	/**
	* Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	* If found, the given instance is populated with the values retrieved from the database<br>
	* If not found, the given instance remains unchanged
	* @param measure
	* @return true if found, false if not found
	*/
	public boolean load(ReportMeasureDTO measureDTO) throws SQLException;
			
	//----------------------------------------------------------------------
	/**
	* Inserts the given bean in the database 
	* @param measure
	* @return the generated value for the auto-incremented column
	*/
	public Integer insert(ReportMeasureDTO measureDTO) throws SQLException;

	//----------------------------------------------------------------------
	/**
	* Updates the given bean in the database 
	* @param measure
	* @return
	*/
	public int update(ReportMeasureDTO measureDTO) throws SQLException;

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
	* @param measure
	* @return
	*/
	public int delete(ReportMeasureDTO measureDTO) throws SQLException;

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
	* @param measure
	* @return
	*/
	public boolean exists(ReportMeasureDTO measureDTO) throws SQLException;

	//----------------------------------------------------------------------
	/**
	* Counts all the records present in the database table
	* @return
	*/
	public long count() throws SQLException;
}

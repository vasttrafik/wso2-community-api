package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.model.ReportDTO;

/**
 * ReportDTO DAO interface
 * 
 * @author Lars Andersson
 **/
public interface ReportDAO extends TransactionalDAO {
	
	    //----------------------------------------------------------------------
		/**
		 * Finds a bean by its primary key 
		 * @param id
		 * @return the bean found or null if not found 
		 */
		public ReportDTO find(Integer id) throws SQLException;

		//----------------------------------------------------------------------
		/**
		 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
		 * If found, the given instance is populated with the values retrieved from the database<br>
		 * If not found, the given instance remains unchanged
		 * @param report
		 * @return true if found, false if not found
		 */
		public boolean load(ReportDTO reportDTO) throws SQLException;
		
	    //----------------------------------------------------------------------
		/**
		 * Inserts the given bean in the database 
		 * @param report
		 * @return the generated value for the auto-incremented column
		 */
		public Integer insert(ReportDTO reportDTO) throws SQLException;

	    //----------------------------------------------------------------------
		/**
		 * Updates the given bean in the database 
		 * @param report
		 * @return
		 */
		public int update(ReportDTO reportDTO) throws SQLException;

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
		 * @param report
		 * @return
		 */
		public int delete(ReportDTO reportDTO) throws SQLException;

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
		 * @param report
		 * @return
		 */
		public boolean exists(ReportDTO reportDTO) throws SQLException;

	    //----------------------------------------------------------------------
		/**
		 * Counts all the records present in the database table
		 * @return
		 */
		public long count() throws SQLException;
}

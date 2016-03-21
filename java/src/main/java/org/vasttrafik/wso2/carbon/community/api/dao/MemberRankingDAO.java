package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;

/**
 * MemberRankingDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface MemberRankingDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public MemberRankingDTO find(Integer id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * Finds a bean by its primary key 
	  * @param memberId
	  * @return the bean found or null if not found 
	  */
	public List<MemberRankingDTO> findByMember(Integer memberId) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param userRankingDTO
	 * @return true if found, false if not found
	 */
	public boolean load(MemberRankingDTO userRankingDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param userRankingDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(MemberRankingDTO userRankingDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param userRankingDTO
	 * @return
	 */
	public int update(MemberRankingDTO userRankingDTO) throws SQLException;

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
	 * @param userRankingDTO
	 * @return
	 */
	public int delete(MemberRankingDTO userRankingDTO) throws SQLException;

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
	 * @param userRankingDTO
	 * @return
	 */
	public boolean exists(MemberRankingDTO userRankingDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

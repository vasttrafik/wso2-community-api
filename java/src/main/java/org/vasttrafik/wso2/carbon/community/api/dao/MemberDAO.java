package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;

/**
 * MemberDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface MemberDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public MemberDTO find(Integer id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	  * Performs a partial match search against email or signature (auto-complete)
	  * @param id
	  * @return the bean found or null if not found 
	  */
	public List<MemberDTO> findByEmailOrSignature(String member, Integer offset, Integer limit) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param memberDTO
	 * @return true if found, false if not found
	 */
	public boolean load(MemberDTO memberDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param memberDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Integer insert(MemberDTO memberDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param memberDTO
	 * @return
	 */
	public int update(MemberDTO memberDTO) throws SQLException;

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
	 * @param memberDTO
	 * @return
	 */
	public int delete(MemberDTO memberDTO) throws SQLException;

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
	 * @param memberDTO
	 * @return
	 */
	public boolean exists(MemberDTO memberDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

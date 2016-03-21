package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.VoteDTO;

/**
 * VoteDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface VoteDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id
	 * @return the bean found or null if not found 
	 */
	public VoteDTO find(Long id) throws SQLException;
	
	//----------------------------------------------------------------------
	/**
	 * Finds votes filtered by topic and member id
	 * @param topicId The topic id
	 * @param memberId The member id, which may be null
	 * @return A list of votes matching the criterias
	 */
	public List<VoteDTO> findByTopicAndMember(Long topicId, Integer memberId) throws SQLException;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param voteDTO
	 * @return true if found, false if not found
	 */
	public boolean load(VoteDTO voteDTO) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param voteDTO
	 * @return the generated value for the auto-incremented column
	 */
	public Long insert(VoteDTO voteDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param voteDTO
	 * @return
	 */
	public int update(VoteDTO voteDTO) throws SQLException;

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
	 * @param voteDTO
	 * @return
	 */
	public int delete(VoteDTO voteDTO) throws SQLException;

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
	 * @param voteDTO
	 * @return
	 */
	public boolean exists(VoteDTO voteDTO) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() throws SQLException;
}

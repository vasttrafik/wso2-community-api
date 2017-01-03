package org.vasttrafik.wso2.carbon.community.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.model.WatchDTO;

/**
 * WatchDTO DAO interface
 * 
 * @author Lars Andersson
 */
public interface WatchDAO extends TransactionalDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds watches by member id
	 * @param memberId The id of the member
	 * @return list of found watches
	 */
	public List<WatchDTO> findByMember(Integer memberId, Integer offset, Integer limit) throws SQLException;

	/**
	 * Finds watches by topic id
	 * @param topicId The id of the topic
	 * @return list of found watches
	 */
	public List<WatchDTO> findByTopic(Integer topicId) throws SQLException;
	
	/**
	 * Finds watches by forum id
	 * @param forumId The id of the forum
	 * @return list of found watches
	 */
	public List<WatchDTO> findByForum(Integer forumId) throws SQLException;
	
	/**
	 * Finds watches by forum id or topic id
	 * @param forumId The id of the forum
	 * @param topicId The id of the topic
	 * @return list of found watches
	 */
	public List<WatchDTO> findByForumOrTopic(Integer forumId, Integer topicId) throws SQLException;
	
    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id
	 * @return
	 */
	public int delete(Boolean isAdmin, Integer memberId, List<WatchDTO> watches) throws SQLException;
}

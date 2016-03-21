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
	 * @return the bean found or null if not found 
	 */
	public List<WatchDTO> findByMember(Integer memberId, Integer offset, Integer limit) throws SQLException;

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id
	 * @return
	 */
	public int delete(Boolean isAdmin, Integer memberId, List<WatchDTO> watches) throws SQLException;
}

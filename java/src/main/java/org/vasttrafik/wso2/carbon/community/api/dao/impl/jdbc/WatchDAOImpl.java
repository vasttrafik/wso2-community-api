package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.WatchDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.WatchDTO;

/**
 * TopicWatchDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class WatchDAOImpl extends GenericDAO<WatchDTO> implements WatchDAO {
	
	private static final Log log = LogFactory.getLog(WatchDAOImpl.class);
	
	private final static String SQL_SELECT =
		"select com_id, com_forum_id, com_topic_id, com_member_id, com_title from com_watch_view";
	
	private final static String SQL_SELECT_WITH = 
		"with com_watches_results as";
	
	private final static String SQL_SELECT_WITH_ROWNUM = 
		"select com_id, com_forum_id, com_topic_id, com_member_id, com_title from com_watches_results where row_num between ? and ?";

	private final static String SQL_ORDER_BY_ROWNUM = 
		", row_number() over (order by a.com_title asc) as row_num";
	
	private final static String SQL_ORDER_BY = 
		" order by a.com_title asc";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public WatchDAOImpl() {
		super();
	}

    //----------------------------------------------------------------------
	/**
	 * Finds watches by member id
	 * @param memberId The id of the member
	 * @return list of found watches
	 */
	public List<WatchDTO> findByMember(Integer memberId, Integer offset, Integer limit) throws SQLException {
		List<WatchDTO> watches = new ArrayList<WatchDTO>();
		WatchDTO watch = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = "select a.*";
			
			if (offset != null)  
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			sql += " from com_watch_view a where com_member_id = ?";
			
			// Check if pagination is required
			if (offset != null)  {
				sql = SQL_SELECT_WITH + "(" + sql + ")" + SQL_SELECT_WITH_ROWNUM;
			}
			else {
				// Add the ORDER BY clause
				sql += SQL_ORDER_BY;
			}
				
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
			
			int i = 1;
			
			// Bind the member id
			setValue(ps, i++,  memberId);
				
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, i++,  offset);
				setValue(ps, i++, (offset + limit - 1));
			}
				
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				watch = new WatchDTO();
				// Populate the bean
				populateBean(rs, watch);
				// Add the bean to the result list
				watches.add(watch);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. WatchDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return watches;
	}
	

	/**
	 * Finds watches by topic id
	 * @param topicId The id of the topic
	 * @return list of found watches
	 */
	@Override
	public List<WatchDTO> findByTopic(Integer topicId) throws SQLException {

		List<WatchDTO> watches = new ArrayList<WatchDTO>();
		WatchDTO watch = null;

		try {

			String sql = SQL_SELECT + " where com_topic_id = ?";

			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql);

			setValue(ps, 1, topicId);

			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				watch = new WatchDTO();
				// Populate the bean
				populateBean(rs, watch);
				// Add the bean to the result list
				watches.add(watch);
			}
		} catch (SQLException e) {
			log.error("Database error. WatchDAOImpl.findByTopic could not execute query." + e.getMessage(), e);
			throw e;
		} finally {
			closeAll();
		}

		return watches;
	}

	/**
	 * Finds watches by forum id
	 * @param forumId The id of the forum
	 * @return list of found watches
	 */
	@Override
	public List<WatchDTO> findByForum(Integer forumId) throws SQLException {

		List<WatchDTO> watches = new ArrayList<WatchDTO>();
		WatchDTO watch = null;

		try {

			String sql = SQL_SELECT + " where com_forum_id = ?";

			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql);

			setValue(ps, 1, forumId);

			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				watch = new WatchDTO();
				// Populate the bean
				populateBean(rs, watch);
				// Add the bean to the result list
				watches.add(watch);
			}
		} catch (SQLException e) {
			log.error("Database error. WatchDAOImpl.findByForum could not execute query." + e.getMessage(), e);
			throw e;
		} finally {
			closeAll();
		}

		return watches;
	}
	
	/**
	 * Finds watches by forum id or topic id
	 * @param forumId The id of the forum
	 * @param topicId The id of the topic
	 * @return list of found watches
	 */
	@Override
	public List<WatchDTO> findByForumOrTopic(Integer forumId, Integer topicId) throws SQLException {

		List<WatchDTO> watches = new ArrayList<WatchDTO>();
		WatchDTO watch = null;

		try {

			String sql = SQL_SELECT + " where com_forum_id = ? OR com_topic_id = ?";

			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql);

			setValue(ps, 1, forumId);
			setValue(ps, 2, topicId);

			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				watch = new WatchDTO();
				// Populate the bean
				populateBean(rs, watch);
				// Add the bean to the result list
				watches.add(watch);
			}
		} catch (SQLException e) {
			log.error("Database error. WatchDAOImpl.findByForumOrTopic could not execute query." + e.getMessage(), e);
			throw e;
		} finally {
			closeAll();
		}

		return watches;
	}

    //----------------------------------------------------------------------
	
	/**
	 * Deletes a list of member watches.  
	 * @param isAdmin Flag indicating if the user requesting the delete is an admin
	 * @param memberId The id of the member making the request
	 * @param watches The list of watches to delete
	 * @return The list of watches that could not be deleted
	 * @exception SQLException if an error occurred
	 */
	public int delete(Boolean isAdmin, Integer memberId, List<WatchDTO> watches) throws SQLException {
		List<Long> forumIds = new ArrayList<Long>();
		List<Long> topicIds = new ArrayList<Long>();
		
		for (WatchDTO watch : watches) {
			if (watch.getForumId() != null) {
				forumIds.add(watch.getId());
			}
			else if (watch.getTopicId() != null) {
				topicIds.add(watch.getId());
			}
		}
		
		int result = 0;
				
		if (forumIds.size() > 0)
			result += deleteWatches("com_forum_watch", forumIds, (isAdmin ? null : memberId));
		
		if (topicIds.size() > 0)
			result += deleteWatches("com_topic_watch", forumIds, (isAdmin ? null : memberId));

		return result;
	}

    //----------------------------------------------------------------------
	public int deleteWatches(String table, List<Long> watchIds, Integer memberId) throws SQLException {
		StringBuffer sql = new StringBuffer("delete from ");
		
		sql.append(table);
		sql.append(" where com_id in(");
		
		for (Iterator<Long> it = watchIds.iterator(); it.hasNext();) {
			sql.append(it.next());
			
			if (it.hasNext())
				sql.append(",");
		}
		
		sql.append(")");
		
		if (memberId != null)
			sql.append(" and com_member_id = ?");
		
		return deleteWatches(sql.toString(), memberId);
	}
	
	public int deleteWatches(String sql, Integer memberId) throws SQLException {
		int result = 0;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValue(ps, 1, memberId); 
			result = ps.executeUpdate();
		} 
        catch (SQLException e) {
			log.error("Database error. WatchDAOImpl.doDelete could not execute update." + e.getMessage(), e);
		} 
        finally {
			closeAll();
		}
		return result;
	}

    //----------------------------------------------------------------------
	@Override
	protected String getSqlSelect() {
		return "";
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlInsert() {
		return "";
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlUpdate() {
		return "";
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlDelete() {
		return "";
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCount() {
		return "";
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCountAll() {
		return "";
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, WatchDTO topicWatchDTO) throws SQLException {
	}

    //----------------------------------------------------------------------
	@Override
	protected WatchDTO populateBean(ResultSet rs, WatchDTO watchDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		watchDTO.setId(rs.getLong("com_id")); 				// java.lang.Long
		if (rs.wasNull()) { watchDTO.setId(null); }; 		// not primitive number => keep null value if any
		watchDTO.setForumId(rs.getInt("com_forum_id")); 	// java.lang.Integer
		if (rs.wasNull()) { watchDTO.setForumId(null); }; 	// not primitive number => keep null value if any
		watchDTO.setTopicId(rs.getLong("com_topic_id")); 	// java.lang.Long
		if (rs.wasNull()) { watchDTO.setTopicId(null); }; 	// not primitive number => keep null value if any
		watchDTO.setMemberId(rs.getInt("com_member_id")); 	// java.lang.Integer
		if (rs.wasNull()) { watchDTO.setMemberId(null); };  // not primitive number => keep null value if any
		watchDTO.setTitle(rs.getString("com_title")); 		// java.lang.String
		return watchDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, WatchDTO topicWatchDTO) throws SQLException {
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, WatchDTO topicWatchDTO) throws SQLException {
	}

}

package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.community.api.dao.TopicDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils.MetaData;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils.QueryHelper;
import org.vasttrafik.wso2.carbon.community.api.model.TopicDTO;

/**
 * TopicDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */

public final class TopicDAOImpl extends GenericDAO<TopicDTO> implements TopicDAO {
	
	private static final Log log = LogFactory.getLog(TopicDAOImpl.class);
	
	private final static String SQL_SELECT_LIST = 
		"select a.com_id, c.com_id as com_category_id, c.com_name as com_category_name, a.com_forum_id, b.com_name as com_forum_name, a.com_subject, a.com_created_date, a.com_last_post_date, a.com_created_by_id, a.com_closed_date, a.com_closed_by_id, a.com_num_posts, a.com_num_views, a.com_num_answers, a.com_first_post_id, a.com_last_post_id, a.com_answer_post_id, a.com_is_deleted";

	private final static String SQL_SELECT_WITH = 
		"with com_topic_results as";
	
	private final static String SQL_SELECT_WITH_ROWNUM = 
		" select * from com_topic_results where row_num between ? and ?";
	
	private final static String SQL_FIND_BY_FORUM_ID = 
		SQL_SELECT_LIST + " from com_topic_last_post_view a, com_forum b, com_category c where a.com_forum_id = ? and a.com_forum_id = b.com_id and b.com_category_id = c.com_id and a.com_is_deleted != 1";
	
	private final static String SQL_FIND_BY_ID = 
		SQL_SELECT_LIST + " from com_topic_last_post_view a, com_forum b, com_category c where a.com_id = ? and a.com_forum_id = b.com_id and b.com_category_id = c.com_id and a.com_is_deleted != 1";
	
	private final static String SQL_INSERT = 
		"insert into com_topic (com_forum_id, com_subject, com_created_by_id) values (?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_topic set com_forum_id = ?, com_subject = ?, com_closed_date = ?, com_closed_by_id = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_topic where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_topic";

	private final static String SQL_COUNT = 
		"select count(*) from com_topic where com_id = ?";
	
	private final static String SQL_ORDER_BY = 
		" order by a.com_last_post_date desc";
		
	private final static String SQL_ORDER_BY_ROWNUM = 
		", row_number() over (order by com_last_post_date desc) as row_num";
	
	private static Map<String, String> columnMappings = null;

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public TopicDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private TopicDTO newInstanceWithPrimaryKey(Long id) {
		TopicDTO topicDTO = new TopicDTO();
		topicDTO.setId(id);
		return topicDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public TopicDTO find(Long id) throws SQLException {
		TopicDTO topicDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(topicDTO)) {
			return topicDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	// TO-DO: Sort order??
	
	//----------------------------------------------------------------------
	/**
	  * List topics by forum 
	  * @param ifModifiedSince
	  * @param forumId
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<TopicDTO> findByForum(Integer forumId, String ifModifiedSince, Integer offset, Integer limit) throws SQLException {
		List<TopicDTO> topics = new ArrayList<TopicDTO>();
		TopicDTO topic = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST;
			
			if (offset != null)
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
				
			if (ifModifiedSince != null) // Need to join with com_post table to find forums updated after specified datetime?
				sql += " from com_topic a, com_post b where a.com_forum_id = ? and a.com_id = b.com_topic_id and b.com_created_date >= ?";
			else 
				sql = SQL_FIND_BY_FORUM_ID;
				
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
				
			// Variable needed to keep track of bind values¨
			int i = 1;
				
			// Bind the category id value
			setValue(ps, i++, forumId);
				
			// If we have a datetime value to bind, specify the value
			if (ifModifiedSince != null)
				setValue(ps, i++, ifModifiedSince);
				
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, i++,  offset);
				setValue(ps, i++, (offset + limit - 1));
			}
				
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				topic = new TopicDTO();
				// Populate the bean
				populateBean(rs, topic);
				// Add the bean to the result list
				topics.add(topic);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return topics;
	}
	
	//----------------------------------------------------------------------
	/**
	  * List topics by label 
      * @param label
      * @param offset
      * @param limit
      * @return the bean found or null if not found 
      */
	public List<TopicDTO> findByLabel(String label, Integer offset, Integer limit) throws SQLException {
		List<TopicDTO> topics = new ArrayList<TopicDTO>();
		TopicDTO topic = null;
			
		try {

			String sql = "select * from (select top (?) * from com_topic_label_" + label + "_view a ";
			
			if(!"popular".equalsIgnoreCase(label)) {
				// Add the ORDER BY clause
				sql +=  SQL_ORDER_BY;
			}
			
			sql += ") x except " + sql + ") y";
				
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
				
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, 1, (offset + limit -1));
				setValue(ps, 2, (offset -1));
			}
				
			// Execute the query
			rs = ps.executeQuery();
			
			// Get the results
			while (rs.next()) {
				topic = new TopicDTO();
				
				// Populate the bean
				populateBean(rs, topic);
				
				// Add the bean to the result list
				topics.add(topic);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return topics;
	}
	    
	//----------------------------------------------------------------------
	/**
	  * List topics by query
	  * @param query
	  * @param offset
	  * @param limit
      * @return the bean found or null if not found 
	  */
	public List<TopicDTO> findByQuery(String query, Integer offset, Integer limit) throws SQLException {
		List<TopicDTO> topics = new ArrayList<TopicDTO>();
		TopicDTO topic = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST;
						
			if (offset != null)
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			// Get a query helper
			QueryHelper helper = new QueryHelper(
				"com_topic", 
				query, 
				columnMappings);
			
			// How to create solution with bind variables instead?
			sql += " from com_topic a, com_forum b, com_category c where a.com_forum_id = b.com_id and b.com_category_id = c.com_id";
			sql += helper.toSQL("a");
			
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
			
			// Bind the values
			int columnOffset = helper.bindValues(MetaData.getInstance(conn), ps);
				
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, columnOffset++,  offset);
				setValue(ps, columnOffset++, (offset + limit - 1));
			}
				
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				topic = new TopicDTO();
				// Populate the bean
				populateBean(rs, topic);
				// Add the bean to the result list
				topics.add(topic);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return topics;
	}
	
	//----------------------------------------------------------------------
	/**
      * List topics
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
    public List<TopicDTO> find(Integer offset, Integer limit) throws SQLException {
    	List<TopicDTO> topics = new ArrayList<TopicDTO>();
		TopicDTO topic = null;
			
		try {
			// The basic SELECT is just a list of columns
			String sql = SQL_SELECT_LIST;
						
			if (offset != null)
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			sql += " from com_topic a";
			
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
				
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, 1,  offset);
				setValue(ps, 2, (offset + limit - 1));
			}
				
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				topic = new TopicDTO();
				// Populate the bean
				populateBean(rs, topic);
				// Add the bean to the result list
				topics.add(topic);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return topics;
    }
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param topicDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(TopicDTO topicDTO) throws SQLException {
		return super.doSelect(topicDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param topicDTO
	 */
	@Override
	public Long insert(TopicDTO topicDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(topicDTO);
		return key;
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param topicDTO
	 * @return
	 */
	@Override
	public int update(TopicDTO topicDTO) throws SQLException {
		return super.doUpdate(topicDTO);
	}
	
	public int incrementViews(TopicDTO topicDTO) throws SQLException {
		int result = 0;
		
		try {
			String sql = "update com_topic set com_num_views = com_num_views + 1 where com_id = ?";
			
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
			
			// Bind the values
			setValue(ps, 1, topicDTO.getId());
			
			result = ps.executeUpdate();
		}
		catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.updateViews could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return result;
	}	
	
    //----------------------------------------------------------------------
	/**
	 * Updates the topic subject
	 * @param topicDTO
	 * @return
	 */
	public int updateSubject(AuthenticatedUser user, TopicDTO topicDTO) throws SQLException {
		int result = 0;
		
		try {
			String sql = "update com_topic set com_subject = ? where com_id = ? and com_closed_date is null and com_is_deleted = 0";
			
			if (!user.hasRole("community-admin"))
				sql += " and com_created_by_id = ?";
			
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
			
			// Bind the values
			setValue(ps, 1, topicDTO.getSubject());
			setValue(ps, 2, topicDTO.getId());
			
			if (!user.hasRole("community-admin"))
				setValue(ps, 3, user.getUserId());
			
			result = ps.executeUpdate();
		}
		catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.updateSubject could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return result;
	}
	
    //----------------------------------------------------------------------
	/**
	 * Closes the topic
	 * @param topicDTO
	 * @return
	 */
	public int closeTopic(TopicDTO topicDTO) throws SQLException {
		int result = 0;
		
		try {
			String sql = "update com_topic set com_closed_date = ?, com_closed_by_id = ? where com_id = ?";
			
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
			
			// Bind the values
			setValue(ps, 1, topicDTO.getClosedDate());
			setValue(ps, 2, topicDTO.getClosedById());
			setValue(ps, 3, topicDTO.getId());
			
			result = ps.executeUpdate();
		}
		catch (SQLException e) {
			log.error("Database error. TopicDAOImpl.updateSubject could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return result;
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Long id) throws SQLException {
		TopicDTO topicDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(topicDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param topicDTO
	 * @return
	 */
	@Override
	public int delete(TopicDTO topicDTO) throws SQLException {
		return super.doDelete(topicDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Long id) throws SQLException {
		TopicDTO topicDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(topicDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param topicDTO
	 * @return
	 */
	@Override
	public boolean exists(TopicDTO topicDTO) throws SQLException {
		return super.doExists(topicDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database
	 * @return
	 */
	@Override
	public long count() throws SQLException {
		return super.doCountAll();
	}

    //----------------------------------------------------------------------
	@Override
	protected String getSqlSelect() {
		return SQL_FIND_BY_ID ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlInsert() {
		return SQL_INSERT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlUpdate() {
		return SQL_UPDATE ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlDelete() {
		return SQL_DELETE ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCount() {
		return SQL_COUNT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCountAll() {
		return SQL_COUNT_ALL ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, TopicDTO topicDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, topicDTO.getId()) ; 						// "com_id" : java.lang.Long
	}

    //----------------------------------------------------------------------
	@Override
	protected TopicDTO populateBean(ResultSet rs, TopicDTO topicDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		topicDTO.setId(rs.getLong("com_id")); 						// java.lang.Long
		if (rs.wasNull()) { topicDTO.setId(null); }; 				// not primitive number => keep null value if any
		topicDTO.setCategoryId(rs.getInt("com_category_id")); 		// java.lang.Integer
		if (rs.wasNull()) { topicDTO.setCategoryId(null); }; 		// not primitive number => keep null value if any
		topicDTO.setCategoryName(rs.getString("com_category_name"));// java.lang.String
		topicDTO.setForumId(rs.getInt("com_forum_id")); 			// java.lang.Integer
		if (rs.wasNull()) { topicDTO.setForumId(null); }; 			// not primitive number => keep null value if any
		topicDTO.setForumName(rs.getString("com_forum_name"));		// java.lang.String
		topicDTO.setSubject(rs.getString("com_subject")); 			// java.lang.String
		topicDTO.setCreatedDate(rs.getTimestamp("com_created_date")); 	// java.util.Date
		topicDTO.setLastPostDate(rs.getTimestamp("com_last_post_date")); 	// java.util.Date
		topicDTO.setCreatedById(rs.getInt("com_created_by_id")); 	// java.lang.Integer
		if (rs.wasNull()) { topicDTO.setCreatedById(null); }; 		// not primitive number => keep null value if any
		topicDTO.setClosedDate(rs.getTimestamp("com_closed_date")); 	    // java.util.Date
		topicDTO.setClosedById(rs.getInt("com_closed_by_id")); 	    // java.lang.Integer
		if (rs.wasNull()) { topicDTO.setClosedById(null); }; 		// not primitive number => keep null value if any
		topicDTO.setNumPosts(rs.getShort("com_num_posts")); 		// java.lang.Short
		if (rs.wasNull()) { topicDTO.setNumPosts(null); }; 			// not primitive number => keep null value if any
		topicDTO.setNumViews(rs.getShort("com_num_views")); 		// java.lang.Short
		if (rs.wasNull()) { topicDTO.setNumViews(null); }; 			// not primitive number => keep null value if any
		topicDTO.setNumAnswers(rs.getShort("com_num_answers")); 	// java.lang.Short
		if (rs.wasNull()) { topicDTO.setNumAnswers(null); }; 		// not primitive number => keep null value if any
		topicDTO.setFirstPostId(rs.getLong("com_first_post_id")); 	// java.lang.Long
		if (rs.wasNull()) { topicDTO.setFirstPostId(null); }; 		// not primitive number => keep null value if any
		topicDTO.setLastPostId(rs.getLong("com_last_post_id")); 	// java.lang.Long
		if (rs.wasNull()) { topicDTO.setLastPostId(null); }; 		// not primitive number => keep null value if any
		topicDTO.setAnswerPostId(rs.getLong("com_answer_post_id")); // java.lang.Long
		if (rs.wasNull()) { topicDTO.setAnswerPostId(null); }; 		// not primitive number => keep null value if any
		topicDTO.setIsDeleted(rs.getBoolean("com_is_deleted")); 	// java.lang.Boolean
		if (rs.wasNull()) { topicDTO.setIsDeleted(null); }; 		// not primitive number => keep null value if any
		return topicDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, TopicDTO topicDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, topicDTO.getForumId()) ; 					// "com_forum_id" : java.lang.Integer
		setValue(ps, i++, topicDTO.getSubject()) ; 					// "com_subject" : java.lang.String
		setValue(ps, i++, topicDTO.getCreatedById()) ; 				// "com_created_by_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, TopicDTO topicDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, topicDTO.getForumId()) ; 					// "com_forum_id" : java.lang.Integer
		setValue(ps, i++, topicDTO.getSubject()) ; 					// "com_subject" : java.lang.String
		setValue(ps, i++, topicDTO.getClosedDate()) ; 				// "com_created_date" : java.util.Date
		setValue(ps, i++, topicDTO.getClosedById()) ; 				// "com_created_by_id" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, topicDTO.getId()) ; 						// "com_id" : java.lang.Long
	}
	
	static {
		columnMappings = new Hashtable<String, String>();
		columnMappings.put("createDate", "com_created_date");
		columnMappings.put("lastPostDate", "com_last_post_date");
		columnMappings.put("createdBy", "com_created_by_id");
		columnMappings.put("subject", "com_subject");
		columnMappings.put("text", "com_text");
		columnMappings.put("category", "com_category_id");
		columnMappings.put("forum", "com_forum_id");
	}
}

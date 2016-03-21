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
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils.MetaData;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.utils.QueryHelper;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;

/**
 * PostDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class PostDAOImpl extends GenericDAO<PostDTO> implements PostDAO {
	
	private static final Log log = LogFactory.getLog(PostDAOImpl.class);
	
	private final static String SQL_SELECT_LIST = 
		"select a.com_id, a.com_topic_id, a.com_forum_id, c.com_category_id, a.com_type, a.com_text, a.com_text_format, a.com_created_date, a.com_created_by_id, a.com_comment_to_id, a.com_points_awarded, a.com_is_answer, a.com_edit_date, a.com_edited_by_id, a.com_edit_count, a.com_is_moderated, a.com_is_deleted, a.com_is_reported";
	
	private final static String SQL_SELECT_WITH = 
		"with com_post_results as";
	
	private final static String SQL_SELECT_WITH_ROWNUM = 
		"select com_id, com_topic_id, com_forum_id, com_category_id, com_type, com_text, com_text_format, a.com_created_date, com_created_by_id, com_comment_to_id, com_points_awarded, com_is_answer, com_edit_date, com_edited_by_id, com_edit_count, com_is_moderated, com_is_deleted, com_is_reported" + 
		" from com_post_results where row_num between ? and ?";
	
	private final static String SQL_FIND_BY_ID = 
		SQL_SELECT_LIST + " from com_post a, com_topic b, com_forum c where a.com_id = ? and a.com_topic_id = b.com_id and b.com_forum_id = c.com_id";
	
	private final static String SQL_INSERT = 
		"insert into com_post (com_topic_id, com_forum_id, com_type, com_text, com_text_format, com_created_by_id, com_comment_to_id) values (?, ?, ?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_post set com_text = ?, com_text_format = ?, com_edit_date = ?, com_edited_by_id = ?, com_edit_count = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_post where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_post";

	private final static String SQL_COUNT = 
		"select count(*) from com_post where com_id = ?";
	
	private final static String SQL_ORDER_BY = 
		" order by com_edit_date desc";
		
	private final static String SQL_ORDER_BY_ROWNUM = 
		", row_number() over (order by a.com_edit_date desc) as row_num";
	
	private final static String SQL_ORDER_BY_ORDERING = 
		", row_number() over (order by ordering desc) as row_num";
	
	private static Map<String, String> columnMappings = null;

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public PostDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private PostDTO newInstanceWithPrimaryKey(Long id) {
		PostDTO postDTO = new PostDTO();
		postDTO.setId(id);
		return postDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public PostDTO find(Long id) throws SQLException {
		PostDTO postDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(postDTO)) {
			return postDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * Finds posts by label 
	  * @param label
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<PostDTO> findByLabel(String label, Integer offset, Integer limit) throws SQLException {
		List<PostDTO> posts = new ArrayList<PostDTO>();
		PostDTO post = null;
			
		try {
			// The basic SELECT is just a list of columns
			String sql = SQL_SELECT_LIST;
									
			if (offset != null)
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
				
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
				post = new PostDTO();
				// Populate the bean
				populateBean(rs, post);
				// Add the bean to the result list
				posts.add(post);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. PostDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return posts;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Finds posts by query
	 * @param label
	 * @param offset
	 * @param limit
	 * @return the bean found or null if not found 
	 */
	public List<PostDTO> findByQuery(String query, Integer offset, Integer limit) throws SQLException {
		List<PostDTO> posts = new ArrayList<PostDTO>();
		PostDTO post = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST;
						
			if (offset != null)
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			
			// Get a query helper
			QueryHelper helper = new QueryHelper(
					"com_post", 
					query, 
					columnMappings);
			
			// Add the FROM and WHERE clauses
			sql += " from com_post a, com_topic b, com_forum c where a.com_topic_id = b.com_id and b.com_forum_id = c.com_id and ";
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
				post = new PostDTO();
				// Populate the bean
				populateBean(rs, post);
				// Add the bean to the result list
				posts.add(post);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. PostDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return posts;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Lists post by topic. The posts are sorted in logical order, meaning that comments are
	 * sorted after the posts they are comments to. That is, posts are not sorted in
	 * chronological order.
	 * @param topicId
	 * @param ifModifiedSince
	 * @param offset
	 * @param limit
	 * @return the bean found or null if not found 
	 */
	public List<PostDTO> findByTopic(Long topicId, String ifModifiedSince, Integer offset, Integer limit) throws SQLException {
		List<PostDTO> posts = new ArrayList<PostDTO>();
		PostDTO post = null;
			
		try {
			// The basic SELECT is just a list of columns
			String sql = "select a.*";
			
			if (offset != null)  
				sql += ", row_number() over (order by a.ordering desc) as row_num"; 
			
			sql += " from com_post_view a where a.com_topic_id = ?";
			
			if (ifModifiedSince != null) 
				sql += " and com_edit_date >= ?";
			
			// Check if pagination is required
			if (offset != null)  {
				sql = SQL_SELECT_WITH + "(" + sql + ")" + SQL_SELECT_WITH_ROWNUM;
			}
			else {
				// Add the ORDER BY clause
				sql += SQL_ORDER_BY_ORDERING;
			}
				
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
			log.error(sql);
			// Variable needed to keep track of bind values¨
			int i = 1;
				
			// Bind the category id value
			setValue(ps, i++, topicId);
				
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
				post = new PostDTO();
				// Populate the bean
				populateBean(rs, post);
				// Add the bean to the result list
				posts.add(post);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. PostDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return posts;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param postDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(PostDTO postDTO) throws SQLException {
		return super.doSelect(postDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param postDTO
	 */
	@Override
	public Long insert(PostDTO postDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(postDTO);
		return key;
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param postDTO
	 * @return
	 */
	@Override
	public int update(PostDTO postDTO) throws SQLException {
		return super.doUpdate(postDTO);
	}	
	
    //----------------------------------------------------------------------
	/**
	 * Updates the post and marks it as having been answered 
	 * @param postDTO
	 * @return
	 */
	public int markAsAnswer(PostDTO postDTO) throws SQLException {
		int result = 0;
		
		try {
			String sql = "update com_post set com_is_answer = 1 where com_id = ?";
			
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 		
			// Bind the values
			setValue(ps, 1, postDTO.getId());
						
			result = ps.executeUpdate();
		}
		catch (SQLException e) {
			log.error("Database error. PostDAOImpl.find could not execute query." + e.getMessage(), e);
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
		PostDTO postDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(postDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param postDTO
	 * @return
	 */
	@Override
	public int delete(PostDTO postDTO) throws SQLException {
		return super.doDelete(postDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Long id)  throws SQLException{
		PostDTO postDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(postDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param postDTO
	 * @return
	 */
	@Override
	public boolean exists(PostDTO postDTO) throws SQLException {
		return super.doExists(postDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, PostDTO postDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, postDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected PostDTO populateBean(ResultSet rs, PostDTO postDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		postDTO.setId(rs.getLong("com_id")); 							// java.lang.Long
		if (rs.wasNull()) { postDTO.setId(null); }; 					// not primitive number => keep null value if any
		postDTO.setTopicId(rs.getLong("com_topic_id")); 				// java.lang.Long
		if (rs.wasNull()) { postDTO.setTopicId(null); }; 				// not primitive number => keep null value if any
		postDTO.setForumId(rs.getInt("com_forum_id")); 					// java.lang.Integer
		if (rs.wasNull()) { postDTO.setForumId(null); }; 				// not primitive number => keep null value if any
		postDTO.setCategoryId(rs.getInt("com_category_id")); 			// java.lang.Integer
		if (rs.wasNull()) { postDTO.setCategoryId(null); }; 			// not primitive number => keep null value if any
		postDTO.setType(rs.getString("com_type")); 						// java.lang.String
		postDTO.setText(rs.getString("com_text")); 						// java.lang.String
		postDTO.setTextFormat(rs.getString("com_text_format")); 		// java.lang.String
		postDTO.setCreateDate(rs.getDate("com_created_date")); 			// java.util.Date
		postDTO.setCreatedById(rs.getInt("com_created_by_id")); 		// java.lang.Integer
		if (rs.wasNull()) { postDTO.setCreatedById(null); }; 			// not primitive number => keep null value if any
		postDTO.setCommentToId(rs.getLong("com_comment_to_id")); 		// java.lang.Integer
		if (rs.wasNull()) { postDTO.setCommentToId(null); }; 			// not primitive number => keep null value if any
		postDTO.setPointsAwarded(rs.getShort("com_points_awarded")); 	// java.lang.Short
		if (rs.wasNull()) { postDTO.setPointsAwarded(null); }; 			// not primitive number => keep null value if any
		postDTO.setIsAnswer(rs.getBoolean("com_is_answer")); 			// java.lang.Boolean
		if (rs.wasNull()) { postDTO.setIsAnswer(null); }; 				// not primitive number => keep null value if any
		postDTO.setEditDate(rs.getDate("com_edit_date")); 				// java.util.Date
		postDTO.setEditedById(rs.getInt("com_edited_by_id")); 			// java.lang.Integer
		if (rs.wasNull()) { postDTO.setEditedById(null); }; 			// not primitive number => keep null value if any
		postDTO.setEditCount(rs.getShort("com_edit_count")); 			// java.lang.Short
		if (rs.wasNull()) { postDTO.setEditCount(null); }; 				// not primitive number => keep null value if any
		postDTO.setIsModerated(rs.getBoolean("com_is_moderated")); 		// java.lang.Boolean
		if (rs.wasNull()) { postDTO.setIsModerated(null); }; 			// not primitive number => keep null value if any
		postDTO.setIsDeleted(rs.getBoolean("com_is_deleted")); 			// java.lang.Boolean
		if (rs.wasNull()) { postDTO.setIsDeleted(null); }; 				// not primitive number => keep null value if any
		postDTO.setIsReported(rs.getBoolean("com_is_reported")); 		// java.land.Boolean
		if (rs.wasNull()) { postDTO.setIsReported(null); }; 			// not primitive number => keep null value if any
		return postDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, PostDTO postDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, postDTO.getTopicId()) ; 						// "com_topic_id" : java.lang.Long
		setValue(ps, i++, postDTO.getForumId()) ; 						// "com_forum_id" : java.lang.Integer
		setValue(ps, i++, postDTO.getType()) ; 							// "com_type" : java.lang.String
		setValue(ps, i++, postDTO.getText()) ; 							// "com_text" : java.lang.String
		setValue(ps, i++, postDTO.getTextFormat()) ; 					// "com_text_format" : java.lang.String
		setValue(ps, i++, postDTO.getCreatedById()) ; 					// "com_created_by_id" : java.lang.Integer
		setValue(ps, i++, postDTO.getCommentToId()) ; 					// "com_comment_to_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, PostDTO postDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, postDTO.getText()) ; 							// "com_text" : java.lang.String
		setValue(ps, i++, postDTO.getTextFormat()) ; 					// "com_text_format" : java.lang.String
		setValue(ps, i++, postDTO.getEditDate()) ; 						// "com_edit_date" : java.util.Date
		setValue(ps, i++, postDTO.getEditedById()) ; 					// "com_edited_by_id" : java.lang.Integer
		setValue(ps, i++, postDTO.getEditCount()) ; 					// "com_edit_count" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, postDTO.getId()) ; 							// "com_id" : java.lang.Long
	}
	
	static {
		columnMappings = new Hashtable<String, String>();
		columnMappings.put("createDate", "com_created_date");
		columnMappings.put("createdBy", "com_created_by_id");
		columnMappings.put("text", "com_text");
		columnMappings.put("category", "com_category_id");
		columnMappings.put("forum", "com_forum_id");
	}
}

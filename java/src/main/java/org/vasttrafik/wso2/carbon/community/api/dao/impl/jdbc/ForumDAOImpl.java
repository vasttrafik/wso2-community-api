package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.ForumDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;

/**
 * ForumDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class ForumDAOImpl extends GenericDAO<ForumDTO> implements ForumDAO {
	
	private static final Log log = LogFactory.getLog(ForumDAOImpl.class);
	
	private final static String SQL_SELECT_LIST = 
		"select a.com_id, a.com_category_id, a.com_name, a.com_desc, a.com_image_url, a.com_num_topics, a.com_num_posts, a.com_last_post_id";
	
	private final static String SQL_SELECT_WITH = 
		"with com_forum_results as";
	
	private final static String SQL_SELECT_WITH_ROWNUM = 
		"select com_id, com_category_id, com_name, com_desc, com_image_url, com_num_topics, com_num_posts, com_last_post_id from com_forum_results where row_num between ? and ?";

	private final static String SQL_FIND_BY_ID = 
		SQL_SELECT_LIST + " from com_forum a where a.com_id = ?";
	
	private final static String SQL_FIND_BY_NAME = 
		SQL_SELECT_LIST + " from com_forum a where a.com_name = ?";
	
	private final static String SQL_INSERT = 
		"insert into com_forum (com_category_id, com_name, com_desc, com_image_url) values (?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_forum set com_category_id = ?, com_name = ?, com_desc = ?, com_image_url = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_forum where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_forum";

	private final static String SQL_COUNT = 
		"select count(*) from com_forum where com_id = ?";
	
	private final static String SQL_ORDER_BY = 
		" order by a.com_name asc";
	
	private final static String SQL_ORDER_BY_ROWNUM = 
		", row_number() over (order by a.com_name asc) as row_num";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public ForumDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private ForumDTO newInstanceWithPrimaryKey(Integer id) {
		ForumDTO forumDTO = new ForumDTO();
		forumDTO.setId(id);
		return forumDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public ForumDTO find(Integer id) throws SQLException {
		ForumDTO forumDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(forumDTO)) {
			return forumDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * Find a forum by name
	  * @param name
	  * @return the bean found or null if not found 
	  */
	public List<ForumDTO> findByName(String name) throws SQLException {
		List<ForumDTO> forums = new ArrayList<ForumDTO>();
		ForumDTO forum = null;
		
		try {
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(SQL_FIND_BY_NAME);
			// Bind the name value
			setValue(ps, 1, name);
			
			// This part can be extracted to separate method and be reused
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				forum = new ForumDTO();
				// Populate the bean
				populateBean(rs, forum);
				// Add the bean to the result list
				forums.add(forum);
			}
		}
		catch (SQLException e) {
			log.error("Database error. ForumDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
		
		return forums;
	}
	
	//----------------------------------------------------------------------
	/**
	  * List forums by label. Currently, the labels supported are popular (today, week, month etc.) and recent. Each label will correspond
	  * to a specific database view so the logic behind the label can be easily changed.
	  * @param label
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<ForumDTO> findByLabel(String label, Integer offset, Integer limit) 
		throws SQLException 
	{
		List<ForumDTO> forums = new ArrayList<ForumDTO>();
		ForumDTO forum = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST;
			
			if (offset != null)  
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			sql += " from com_forum_label_" + label + "_v";
				
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
				forum = new ForumDTO();
				// Populate the bean
				populateBean(rs, forum);
				// Add the bean to the result list
				forums.add(forum);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. ForumDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return forums;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Finds forums belonging to a certain category
	 * @param categoryId The category id
	 * @return the beans found or empty list if not found 
	 */
	public List<ForumDTO> findByCategory(Integer categoryId, String ifModifiedSince, Integer offset, Integer limit) 
		throws SQLException
	{
		List<ForumDTO> forums = new ArrayList<ForumDTO>();
		ForumDTO forum = null;
			
		try {
			// The basic SELECT is just a list of columns
			String sql = SQL_SELECT_LIST;
			
			if (offset != null)  
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
				
			if (ifModifiedSince != null) // Need to join with com_post table to find forums updated after specified datetime?
				sql += " from com_forum a, com_post b where a.com_category_id = ? and a.com_id = b.com_forum_id and b.com_create_date >= ?";
			else 
				sql += " from com_forum a where a.com_category_id = ?";
				
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
			setValue(ps, i++, categoryId);
				
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
				forum = new ForumDTO();
				// Populate the bean
				populateBean(rs, forum);
				// Add the bean to the result list
				forums.add(forum);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. ForumDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return forums;
	}
	
	//----------------------------------------------------------------------
	/**
	  * List forums
	  * @param offset
	  * @param limit
	  * @return the bean found or null if not found 
	  */
	public List<ForumDTO> find(Integer offset, Integer limit) throws SQLException {
		List<ForumDTO> forums = new ArrayList<ForumDTO>();
		ForumDTO forum = null;
			
		try {
			// The basic SELECT is just a list of columns
			String sql = SQL_SELECT_LIST;
			
			if (offset != null)  
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			sql += " from com_forum a";
					
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
				
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, i++,  offset);
				setValue(ps, i++, (offset + limit - 1));
			}
				
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				forum = new ForumDTO();
				// Populate the bean
				populateBean(rs, forum);
				// Add the bean to the result list
				forums.add(forum);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. ForumDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return forums;
	}
	
	//----------------------------------------------------------------------
	/**
	  * Finds forums belonging to a certain category or label
	  * @param categoryId The category id
	  * @return the beans found or empty list if not found 
	  */
	protected List<ForumDTO> findByCategoryOrLabel(Integer categoryId, String label, String ifModifiedSince, Integer offset, Integer limit) 
		throws SQLException
	{
		List<ForumDTO> forums = new ArrayList<ForumDTO>();
		ForumDTO forum = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST + " from com_forum";
				
			if (ifModifiedSince != null) // Need to join with com_post table to find forums updated after specified datetime?
				sql += "a, com_post b where a.com_category_id = ? and a.com_id = b.com_forum_id and b.com_create_date >= ?";
			else {
				if (categoryId != null)
					sql += " where a.com_category_id = ?";
				else
					sql += " where a.com_category_id = ?";
			}
				
			// Check if pagination is required
			if (offset != null)  {
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
				sql = SQL_SELECT_WITH + "(" + sql + ")" + SQL_SELECT_WITH_ROWNUM;
			}
			else {
				// Add the ORDER BY clause
				sql += SQL_ORDER_BY;
			}
				
			// Get a connection
			conn = getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
				
			// Variable needed to keep track of bind values¨
			int i = 1;
				
			// Bind the category id value
			setValue(ps, i++, categoryId);
				
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
				forum = new ForumDTO();
				// Populate the bean
				populateBean(rs, forum);
				// Add the bean to the result list
				forums.add(forum);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. ForumDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return forums;
	}
		
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param forumDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(ForumDTO forumDTO) throws SQLException {
		return super.doSelect(forumDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param forumDTO
	 */
	@Override
	public Integer insert(ForumDTO forumDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(forumDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param forumDTO
	 * @return
	 */
	@Override
	public int update(ForumDTO forumDTO) throws SQLException {
		return super.doUpdate(forumDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		ForumDTO forumDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(forumDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param forumDTO
	 * @return
	 */
	@Override
	public int delete(ForumDTO forumDTO) throws SQLException {
		return super.doDelete(forumDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		ForumDTO forumDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(forumDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param forumDTO
	 * @return
	 */
	@Override
	public boolean exists(ForumDTO forumDTO) throws SQLException {
		return super.doExists(forumDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ForumDTO forumDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, forumDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected ForumDTO populateBean(ResultSet rs, ForumDTO forumDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		forumDTO.setId(rs.getInt("com_id")); 						// java.lang.Integer
		if (rs.wasNull()) { forumDTO.setId(null); }; 				// not primitive number => keep null value if any
		forumDTO.setCategoryId(rs.getInt("com_category_id")); 		// java.lang.Integer
		if (rs.wasNull()) { forumDTO.setCategoryId(null); }; 		// not primitive number => keep null value if any
		forumDTO.setName(rs.getString("com_name")); 				// java.lang.String
		forumDTO.setDesc(rs.getString("com_desc")); 				// java.lang.String
		forumDTO.setImageUrl(rs.getString("com_image_url")); 		// java.lang.String
		forumDTO.setNumTopics(rs.getInt("com_num_topics")); 		// java.lang.Integer
		if (rs.wasNull()) { forumDTO.setNumTopics(null); }; 		// not primitive number => keep null value if any
		forumDTO.setNumPosts(rs.getInt("com_num_posts")); 			// java.lang.Integer
		if (rs.wasNull()) { forumDTO.setNumPosts(null); }; 			// not primitive number => keep null value if any
		forumDTO.setLastPostId(rs.getLong("com_last_post_id")); 	// java.lang.Long
		if (rs.wasNull()) { forumDTO.setLastPostId(null); }; 		// not primitive number => keep null value if any
		return forumDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ForumDTO forumDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, forumDTO.getCategoryId()) ; 				// "com_category_id" : java.lang.Integer
		setValue(ps, i++, forumDTO.getName()) ; 					// "com_name" : java.lang.String
		setValue(ps, i++, forumDTO.getDesc()) ; 					// "com_desc" : java.lang.String
		setValue(ps, i++, forumDTO.getImageUrl()) ; 				// "com_image_url" : java.lang.String
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ForumDTO forumDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, forumDTO.getCategoryId()) ; 				// "com_category_id" : java.lang.Integer
		setValue(ps, i++, forumDTO.getName()) ; 					// "com_name" : java.lang.String
		setValue(ps, i++, forumDTO.getDesc()) ; 					// "com_desc" : java.lang.String
		setValue(ps, i++, forumDTO.getImageUrl()) ; 				// "com_image_url" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, forumDTO.getId()) ; 						// "com_id" : java.lang.Integer
	}
}

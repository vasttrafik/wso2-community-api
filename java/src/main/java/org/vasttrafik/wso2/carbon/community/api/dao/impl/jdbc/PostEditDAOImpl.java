package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.PostEditDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.PostEditDTO;

/**
 * PostEditDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class PostEditDAOImpl extends GenericDAO<PostEditDTO> implements PostEditDAO {
	
	private static final Log log = LogFactory.getLog(PostEditDAOImpl.class);
	
	private final static String SQL_FIND_BY_POST = 
		"select com_id, com_post_id, com_edit_version, com_text, com_text_format, com_created_date, com_created_by_id from com_post_edit where com_post_id = ? order by com_edit_version desc";

	private final static String SQL_SELECT = 
		"select com_id, com_post_id, com_edit_version, com_text, com_text_format, com_created_date, com_created_by_id from com_post_edit where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_post_edit (com_post_id, com_edit_version, com_text, com_text_format, com_created_date, com_created_by_id) values (?, ?, ?, ?, ?, ?)";

	private final static String SQL_DELETE = 
		"delete from com_post_edit where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_post_edit";

	private final static String SQL_COUNT = 
		"select count(*) from com_post_edit where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public PostEditDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private PostEditDTO newInstanceWithPrimaryKey(Long id) {
		PostEditDTO postEditDTO = new PostEditDTO();
		postEditDTO.setId(id);
		return postEditDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public PostEditDTO find(Long id) throws SQLException {
		PostEditDTO postEditDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(postEditDTO)) {
			return postEditDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * Lists all edits belonging to a post in descending version order 
	  * @param postId
	  * @return the bean found or null if not found 
	  */
	public List<PostEditDTO> findByPost(Long postId) throws SQLException {
		List<PostEditDTO> edits = new ArrayList<PostEditDTO>();
		PostEditDTO edit = null;
		
		try {
			// Get a connection
			conn = getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(SQL_FIND_BY_POST);
			// Bind the name value
			setValue(ps, 1, postId);
			
			// This part can be extracted to separate method and be reused
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				edit = new PostEditDTO();
				// Populate the bean
				populateBean(rs, edit);
				// Add the bean to the result list
				edits.add(edit);
			}
		}
		catch (SQLException e) {
			log.error("Database error. PostEditDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
		
		return edits;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param postEditDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(PostEditDTO postEditDTO) throws SQLException {
		return super.doSelect(postEditDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param postEditDTO
	 */
	@Override
	public Long insert(PostEditDTO postEditDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(postEditDTO);
		return key;
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param postEditDTO
	 * @return
	 */
	@Override
	public int update(PostEditDTO postEditDTO) throws SQLException {
		return super.doUpdate(postEditDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Long id) throws SQLException {
		PostEditDTO postEditDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(postEditDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param postEditDTO
	 * @return
	 */
	@Override
	public int delete(PostEditDTO postEditDTO) throws SQLException {
		return super.doDelete(postEditDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Long id) throws SQLException {
		PostEditDTO postEditDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(postEditDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param postEditDTO
	 * @return
	 */
	@Override
	public boolean exists(PostEditDTO postEditDTO) throws SQLException {
		return super.doExists(postEditDTO);
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
		return SQL_SELECT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlInsert() {
		return SQL_INSERT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlUpdate() {
		return "";//SQL_UPDATE ;
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, PostEditDTO postEditDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, postEditDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected PostEditDTO populateBean(ResultSet rs, PostEditDTO postEditDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		postEditDTO.setId(rs.getLong("com_id")); 						// java.lang.Long
		if (rs.wasNull()) { postEditDTO.setId(null); }; 				// not primitive number => keep null value if any
		postEditDTO.setPostId(rs.getLong("com_post_id")); 				// java.lang.Long
		if (rs.wasNull()) { postEditDTO.setPostId(null); }; 			// not primitive number => keep null value if any
		postEditDTO.setEditVersion(rs.getShort("com_edit_version")); 	// java.lang.Short
		if (rs.wasNull()) { postEditDTO.setEditVersion(null); }; 		// not primitive number => keep null value if any
		postEditDTO.setText(rs.getString("com_text")); 					// java.lang.String
		postEditDTO.setTextFormat(rs.getString("com_text_format")); 	// java.lang.String
		postEditDTO.setCreateDate(rs.getDate("com_created_date")); 		// java.util.Date
		postEditDTO.setCreatedById(rs.getInt("com_created_by_id")); 	// java.lang.Integer
		if (rs.wasNull()) { postEditDTO.setCreatedById(null); }; 		// not primitive number => keep null value if any
		return postEditDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, PostEditDTO postEditDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, postEditDTO.getPostId()) ; 					// "com_post_id" : java.lang.Long
		setValue(ps, i++, postEditDTO.getEditVersion()) ; 				// "com_edit_version" : java.lang.Byte
		setValue(ps, i++, postEditDTO.getText()) ; 						// "com_text" : java.lang.String
		setValue(ps, i++, postEditDTO.getTextFormat()) ; 				// "com_text_format" : java.lang.String
		setValue(ps, i++, postEditDTO.getCreateDate()) ; 				// "com_create_date" : java.util.Date
		setValue(ps, i++, postEditDTO.getCreatedById()) ; 				// "com_created_by_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, PostEditDTO postEditDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, postEditDTO.getPostId()) ; 					// "com_post_id" : java.lang.Long
		setValue(ps, i++, postEditDTO.getEditVersion()) ; 				// "com_edit_version" : java.lang.Byte
		setValue(ps, i++, postEditDTO.getText()) ; 						// "com_text" : java.lang.String
		setValue(ps, i++, postEditDTO.getTextFormat()) ; 				// "com_text_format" : java.lang.String
		setValue(ps, i++, postEditDTO.getCreateDate()) ; 				// "com_create_date" : java.util.Date
		setValue(ps, i++, postEditDTO.getCreatedById()) ; 				// "com_created_by_id" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, postEditDTO.getId()) ; 						// "com_id" : java.lang.Long
	}
}

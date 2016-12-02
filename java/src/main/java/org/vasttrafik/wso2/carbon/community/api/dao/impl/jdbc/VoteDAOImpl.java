package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.VoteDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.VoteDTO;

/**
 * VoteDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
@SuppressWarnings("unused")
public final class VoteDAOImpl extends GenericDAO<VoteDTO> implements VoteDAO {
	
	private static final Log log = LogFactory.getLog(VoteDAOImpl.class);
	
	private final static String SQL_SELECT_LIST = 
		"select a.com_id, a.com_post_id, a.com_type, a.com_member_id, a.com_points";
	
	private final static String SQL_FIND_BY_ID = 
		SQL_SELECT_LIST + " from com_vote a where com_id = ?";
	
	private final static String SQL_FIND_BY_POST = 
			SQL_SELECT_LIST + " from com_vote a where com_post_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_vote (com_post_id, com_type, com_member_id, com_points) values (?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_vote set com_post_id = ?, com_type = ?, com_member_id = ?, com_points = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_vote where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_vote";

	private final static String SQL_COUNT = 
		"select count(*) from com_vote where com_id = ?";
	
	private final static String SQL_ORDER_BY = 
		" order by a.com_post_id desc";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public VoteDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private VoteDTO newInstanceWithPrimaryKey(Long id) {
		VoteDTO voteDTO = new VoteDTO();
		voteDTO.setId(id);
		return voteDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public VoteDTO find(Long id) throws SQLException {
		VoteDTO voteDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(voteDTO)) {
			return voteDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	 * Finds votes filtered by topic and member id
	 * @param topicId The topic id
	 * @param memberId The member id, which may be null
	 * @return A list of votes matching the criterias
	 */
	public List<VoteDTO> findByTopicAndMember(Long topicId, Integer memberId) throws SQLException {
		List<VoteDTO> votes = new ArrayList<VoteDTO>();
		VoteDTO vote = null;
		
		try {
			// The basic SELECT
			String sql = SQL_SELECT_LIST + " from com_vote a, com_post b where a.com_post_id = b.com_id and b.com_topic_id = ?";
						
			if (memberId != null)  
				sql += " and a.com_member_id = ?"; 
						
			sql += SQL_ORDER_BY;
			
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(sql); 
							
			// Variable needed to keep track of bind values¨
			int i = 1;
							
			// Bind the category id value
			setValue(ps, i++, topicId);
							
			// If we have a member id to bind, specify the value
			if (memberId != null)
				setValue(ps, i++, memberId);
			
			// Execute the query
			rs = ps.executeQuery();
			
			// Get the results
			while (rs.next()) {
				vote = new VoteDTO();
				// Populate the bean
				populateBean(rs, vote);
				// Add the bean to the result list
				votes.add(vote);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. VoteDAOImpl.findByTopicAndMember could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
		
		return votes;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Finds votes filtered by postId
	 * @param postId The post id
	 * @return A list of votes matching the criterias
	 */
	public List<VoteDTO> findByPost(Long postId) throws SQLException {
		List<VoteDTO> votes = new ArrayList<VoteDTO>();
		VoteDTO vote = null;
		
		try {
			
			// Get a connection
			getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(SQL_FIND_BY_POST); 
							
			// Variable needed to keep track of bind values¨
			int i = 1;
							
			// Bind the category id value
			setValue(ps, i, postId);
			
			// Execute the query
			rs = ps.executeQuery();
			
			// Get the results
			while (rs.next()) {
				vote = new VoteDTO();
				// Populate the bean
				populateBean(rs, vote);
				// Add the bean to the result list
				votes.add(vote);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. VoteDAOImpl.findByTopicAndMember could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
		
		return votes;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param voteDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(VoteDTO voteDTO) throws SQLException {
		return super.doSelect(voteDTO) ;
	}
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param voteDTO
	 */
	@Override
	public Long insert(VoteDTO voteDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(voteDTO);
		return key;
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param voteDTO
	 * @return
	 */
	@Override
	public int update(VoteDTO voteDTO) throws SQLException {
		return super.doUpdate(voteDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Long id) throws SQLException {
		VoteDTO voteDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(voteDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param voteDTO
	 * @return
	 */
	@Override
	public int delete(VoteDTO voteDTO) throws SQLException {
		return super.doDelete(voteDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Long id) throws SQLException {
		VoteDTO voteDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(voteDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param voteDTO
	 * @return
	 */
	@Override
	public boolean exists(VoteDTO voteDTO) throws SQLException {
		return super.doExists(voteDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, VoteDTO voteDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, voteDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected VoteDTO populateBean(ResultSet rs, VoteDTO voteDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		voteDTO.setId(rs.getLong("com_id")); 				// java.lang.Long
		if (rs.wasNull()) { voteDTO.setId(null); }; 		// not primitive number => keep null value if any
		voteDTO.setPostId(rs.getLong("com_post_id")); 		// java.lang.Long
		if (rs.wasNull()) { voteDTO.setPostId(null); }; 	// not primitive number => keep null value if any
		voteDTO.setType(rs.getString("com_type")); 			// java.lang.String
		voteDTO.setMemberId(rs.getInt("com_member_id")); 	// java.lang.Integer
		if (rs.wasNull()) { voteDTO.setMemberId(null); }; 	// not primitive number => keep null value if any
		voteDTO.setPoints(rs.getShort("com_points")); 		// java.lang.Short
		if (rs.wasNull()) { voteDTO.setPoints(null); }; 	// not primitive number => keep null value if any
		return voteDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, VoteDTO voteDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, voteDTO.getPostId()) ; 			// "com_post_id" : java.lang.Integer
		setValue(ps, i++, voteDTO.getType()) ; 				// "com_type" : java.lang.String
		setValue(ps, i++, voteDTO.getMemberId()) ; 			// "com_user_id" : java.lang.Integer
		setValue(ps, i++, voteDTO.getPoints()) ; 			// "com_points" : java.lang.Short
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, VoteDTO voteDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, voteDTO.getPostId()) ; 			// "com_post_id" : java.lang.Integer
		setValue(ps, i++, voteDTO.getType()) ; 				// "com_type" : java.lang.String
		setValue(ps, i++, voteDTO.getMemberId()) ; 			// "com_user_id" : java.lang.Integer
		setValue(ps, i++, voteDTO.getPoints()) ; 			// "com_points" : java.lang.Short
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, voteDTO.getId()) ; 				// "com_id" : java.lang.Integer
	}
}

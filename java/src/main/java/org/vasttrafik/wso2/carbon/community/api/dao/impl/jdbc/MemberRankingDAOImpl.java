package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberRankingDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;

/**
 * UserRankingDTO DAO implementation 
 * 
 * @author Lars Andersson
 * 
 *
 */
public final class MemberRankingDAOImpl extends GenericDAO<MemberRankingDTO> implements MemberRankingDAO {
	
	private static final Log log = LogFactory.getLog(MemberRankingDAOImpl.class);
	
	private final static String SQL_FIND_BY_MEMBER = 
		"select a.com_id, a.com_member_id, a.com_ranking_id, b.com_title, b.com_type, b.com_min_points, b.com_image_url, a.com_current_score from com_member_ranking a, com_ranking b where a.com_member_id = ? and a.com_ranking_id = b.com_id";

	private final static String SQL_SELECT = 
		"select com_id, com_member_id, com_ranking_id, com_current_score from com_user_ranking where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_user_ranking (com_member_id, com_ranking_id, com_current_score) values (?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_user_ranking set com_member_id = ?, com_ranking_id = ?, com_current_score = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_user_ranking where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_user_ranking";

	private final static String SQL_COUNT = 
		"select count(*) from com_user_ranking where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public MemberRankingDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private MemberRankingDTO newInstanceWithPrimaryKey(Integer id) {
		MemberRankingDTO userRankingDTO = new MemberRankingDTO();
		userRankingDTO.setId(id);
		return userRankingDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public MemberRankingDTO find(Integer id) throws SQLException {
		MemberRankingDTO userRankingDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(userRankingDTO)) {
			return userRankingDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * Finds a bean by its primary key 
	  * @param memberId
	  * @return the bean found or null if not found 
	  */
	public List<MemberRankingDTO> findByMember(Integer memberId) throws SQLException {
		List<MemberRankingDTO> rankings = new ArrayList<MemberRankingDTO>();
		MemberRankingDTO ranking = null;
		
		try {
			// Get a connection
			conn = getConnection();
			// Prepare the statement
			ps = conn.prepareStatement(SQL_FIND_BY_MEMBER);
			// Bind the name value
			setValue(ps, 1, memberId);
			
			// This part can be extracted to separate method and be reused
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				ranking = new MemberRankingDTO();
				// Populate the bean
				populateBean(rs, ranking);
				// Add the bean to the result list
				rankings.add(ranking);
			}
		}
		catch (SQLException e) {
			log.error("Database error. MemberRankingDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
		
		return rankings;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param userRankingDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(MemberRankingDTO userRankingDTO) throws SQLException {
		return super.doSelect(userRankingDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param userRankingDTO
	 */
	@Override
	public Integer insert(MemberRankingDTO userRankingDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(userRankingDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param userRankingDTO
	 * @return
	 */
	@Override
	public int update(MemberRankingDTO userRankingDTO) throws SQLException {
		return super.doUpdate(userRankingDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		MemberRankingDTO userRankingDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(userRankingDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param userRankingDTO
	 * @return
	 */
	@Override
	public int delete(MemberRankingDTO userRankingDTO) throws SQLException {
		return super.doDelete(userRankingDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		MemberRankingDTO userRankingDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(userRankingDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param userRankingDTO
	 * @return
	 */
	@Override
	public boolean exists(MemberRankingDTO userRankingDTO) throws SQLException {
		return super.doExists(userRankingDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, MemberRankingDTO userRankingDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, userRankingDTO.getId()) ; // "com_id" : java.lang.Integer
	}
	
	// b.com_title, b.com_type, b.com_min_points, b.com_image_url, a.com_current_score

    //----------------------------------------------------------------------
	@Override
	protected MemberRankingDTO populateBean(ResultSet rs, MemberRankingDTO userRankingDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		userRankingDTO.setId(rs.getInt("com_id")); 							// java.lang.Integer
		if (rs.wasNull()) { userRankingDTO.setId(null); }; 					// not primitive number => keep null value if any
		userRankingDTO.setMemberId(rs.getInt("com_member_id")); 			// java.lang.Integer
		if (rs.wasNull()) { userRankingDTO.setMemberId(null); }; 			// not primitive number => keep null value if any
		userRankingDTO.setRankingId(rs.getInt("com_ranking_id")); 			// java.lang.Integer
		if (rs.wasNull()) { userRankingDTO.setRankingId(null); }; 			// not primitive number => keep null value if any
		userRankingDTO.setTitle(rs.getString("com_title"));					// java.lang.String
		userRankingDTO.setType(rs.getString("com_type"));					// java.lang.String
		userRankingDTO.setMinPoints(rs.getInt("com_min_points")); 			// java.lang.Integer
		if (rs.wasNull()) { userRankingDTO.setMinPoints(null); }; 			// not primitive number => keep null value if any
		userRankingDTO.setImageUrl(rs.getString("com_image_url"));			// java.lang.String
		userRankingDTO.setCurrentScore(rs.getInt("com_current_score")); 	// java.lang.Integer
		if (rs.wasNull()) { userRankingDTO.setCurrentScore(null); }; 		// not primitive number => keep null value if any
		return userRankingDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, MemberRankingDTO userRankingDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, userRankingDTO.getMemberId()) ; 					// "com_user_id" : java.lang.Integer
		setValue(ps, i++, userRankingDTO.getRankingId()) ; 					// "com_ranking_id" : java.lang.Integer
		setValue(ps, i++, userRankingDTO.getCurrentScore()) ; 				// "com_current_score" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, MemberRankingDTO userRankingDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, userRankingDTO.getMemberId()) ; 					// "com_user_id" : java.lang.Integer
		setValue(ps, i++, userRankingDTO.getRankingId()) ; 					// "com_ranking_id" : java.lang.Integer
		setValue(ps, i++, userRankingDTO.getCurrentScore()) ; 				// "com_current_score" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, userRankingDTO.getId()) ; 						// "com_id" : java.lang.Integer
	}
}

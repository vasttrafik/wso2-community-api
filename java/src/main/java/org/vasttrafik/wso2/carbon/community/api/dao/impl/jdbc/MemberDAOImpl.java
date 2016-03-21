package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;

/**
 * memberDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class MemberDAOImpl extends GenericDAO<MemberDTO> implements MemberDAO {
	
	private static final Log log = LogFactory.getLog(MemberDAOImpl.class);
	
	private final static String SQL_SELECT_LIST = 
		"select com_id, com_user_name, com_email, com_status, com_show_email, com_show_rankings, com_signature, com_use_gravatar, com_gravatar_email, com_accept_all_msg, com_notify_email, com_notify_message, com_notify_text";

	private final static String SQL_SELECT_WITH = 
		"with com_member_results as";
		
	private final static String SQL_SELECT_WITH_ROWNUM = 
		SQL_SELECT_LIST + " from com_member_results where row_num between ? and ?";
	
	private final static String SQL_FIND_BY_ID = 
		SQL_SELECT_LIST + " from com_member where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_member (com_id, com_user_name, com_email, com_status, com_show_email, com_show_rankings, com_signature, com_use_gravatar, com_gravatar_email, com_accept_all_msg, com_notify_email, com_notify_message, com_notify_text) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_member set com_email = ?, com_status = ?, com_show_email = ?, com_show_rankings = ?, com_signature = ?, com_use_gravatar = ?, com_gravatar_email = ?, com_accept_all_msg = ?, com_notify_email = ?, com_notify_message = ?, com_notify_text = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_member where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_member";

	private final static String SQL_COUNT = 
		"select count(*) from com_member where com_id = ?";
	
	private final static String SQL_ORDER_BY = 
		" order by com_signature asc";
			
	private final static String SQL_ORDER_BY_ROWNUM = 
		", row_number() over (order by com_signature asc) as row_num";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public MemberDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private MemberDTO newInstanceWithPrimaryKey(Integer id) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(id);
		return memberDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public MemberDTO find(Integer id) throws SQLException {
		MemberDTO memberDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(memberDTO)) {
			return memberDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * Performs a partial match search against email or signature (auto-complete)
	  * @param id
	  * @return the bean found or null if not found 
	  */
	public List<MemberDTO> findByEmailOrSignature(String member, Integer offset, Integer limit) throws SQLException {
		List<MemberDTO> members = new ArrayList<MemberDTO>();
		MemberDTO memberDTO = null;
			
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST;
			
			if (offset != null)
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
				
			
			sql += " from com_member where com_email like ? or com_signature like ?";
				
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
				
			// Bind the member search value
			setValue(ps, i++, member.endsWith("%") ? member : member + "%");
			setValue(ps, i++, member.endsWith("%") ? member : member + "%");
					
			// If we have an offset and a limit value to bind, specify the values
			if (offset != null) {
				setValue(ps, i++,  offset);
				setValue(ps, i++, (offset + limit - 1));
			}
				
			// Execute the query
			rs = ps.executeQuery();

			// Get the results
			while (rs.next()) {
				memberDTO = new MemberDTO();
				// Populate the bean
				populateBean(rs, memberDTO);
				// Add the bean to the result list
				members.add(memberDTO);
			}
		} 
	    catch (SQLException e) {
			log.error("Database error. MemberDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
	    finally {
			closeAll();
		}
			
		return members;
	}
	
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param memberDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(MemberDTO memberDTO) throws SQLException {
		return super.doSelect(memberDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param memberDTO
	 */
	@Override
	public Integer insert(MemberDTO memberDTO) throws SQLException {
		super.doInsert(memberDTO);
		return memberDTO.getId();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param memberDTO
	 * @return
	 */
	@Override
	public int update(MemberDTO memberDTO) throws SQLException {
		return super.doUpdate(memberDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		MemberDTO memberDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(memberDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param memberDTO
	 * @return
	 */
	@Override
	public int delete(MemberDTO memberDTO) throws SQLException {
		return super.doDelete(memberDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		MemberDTO memberDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(memberDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param memberDTO
	 * @return
	 */
	@Override
	public boolean exists(MemberDTO memberDTO) throws SQLException {
		return super.doExists(memberDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, MemberDTO memberDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, memberDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected MemberDTO populateBean(ResultSet rs, MemberDTO memberDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		memberDTO.setId(rs.getInt("com_id")); 							// java.lang.Integer
		if (rs.wasNull()) { memberDTO.setId(null); }; 					// not primitive number => keep null value if any
		memberDTO.setEmail(rs.getString("com_user_name")); 				// java.lang.String
		memberDTO.setEmail(rs.getString("com_email")); 					// java.lang.String
		memberDTO.setStatus(rs.getString("com_status")); 				// java.lang.String
		if (rs.wasNull()) { memberDTO.setStatus(null); }; 				// not primitive number => keep null value if any
		memberDTO.setShowEmail(rs.getBoolean("com_show_email")); 		// java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setShowEmail(null); }; 			// not primitive number => keep null value if any
		memberDTO.setShowRankings(rs.getBoolean("com_show_rankings"));  // java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setShowRankings(null); }; 		// not primitive number => keep null value if any
		memberDTO.setSignature(rs.getString("com_signature")); 			// java.lang.String
		memberDTO.setUseGravatar(rs.getBoolean("com_use_gravatar")); 	// java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setUseGravatar(null); }; 			// not primitive number => keep null value if any
		memberDTO.setGravatarEmail(rs.getString("com_gravatar_email")); // java.lang.String
		memberDTO.setAcceptAllMsg(rs.getBoolean("com_accept_all_msg")); // java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setAcceptAllMsg(null); }; 		// not primitive number => keep null value if any
		memberDTO.setNotifyEmail(rs.getBoolean("com_notify_email")); 	// java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setNotifyEmail(null); }; 			// not primitive number => keep null value if any
		memberDTO.setNotifyMessage(rs.getBoolean("com_notify_message"));// java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setNotifyMessage(null); }; 		// not primitive number => keep null value if any
		memberDTO.setNotifyText(rs.getBoolean("com_notify_text")); 		// java.lang.Boolean
		if (rs.wasNull()) { memberDTO.setNotifyText(null); }; 			// not primitive number => keep null value if any
		return memberDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, MemberDTO memberDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, memberDTO.getId()) ; 							// "com_id" : java.lang.Integer
		setValue(ps, i++, memberDTO.getUserName()) ; 					// "com_user_name" : java.lang.String
		setValue(ps, i++, memberDTO.getEmail()) ; 						// "com_email" : java.lang.String
		setValue(ps, i++, memberDTO.getStatus()) ; 						// "com_status" : java.lang.String
		setValue(ps, i++, memberDTO.getShowEmail()) ; 					// "com_show_email" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getShowRankings()) ; 				// "com_show_rankings" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getSignature()) ; 					// "com_signature" : java.lang.String
		setValue(ps, i++, memberDTO.getUseGravatar()) ; 				// "com_use_gravatar" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getGravatarEmail()) ; 				// "com_gravatar_email" : java.lang.String
		setValue(ps, i++, memberDTO.getAcceptAllMsg()) ; 				// "com_accept_all_msg" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getNotifyEmail()) ; 				// "com_notify_email" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getNotifyMessage()) ; 				// "com_notify_message" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getNotifyText()) ; 					// "com_notify_text" : java.lang.Boolean
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, MemberDTO memberDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, memberDTO.getEmail()) ; 						// "com_email" : java.lang.String
		setValue(ps, i++, memberDTO.getStatus()) ; 						// "com_status" : java.lang.String
		setValue(ps, i++, memberDTO.getShowEmail()) ; 					// "com_show_email" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getShowRankings()) ; 				// "com_show_rankings" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getSignature()) ; 					// "com_signature" : java.lang.String
		setValue(ps, i++, memberDTO.getUseGravatar()) ; 				// "com_use_gravatar" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getGravatarEmail()) ; 				// "com_gravatar_email" : java.lang.String
		setValue(ps, i++, memberDTO.getAcceptAllMsg()) ; 				// "com_accept_all_msg" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getNotifyEmail()) ; 				// "com_notify_email" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getNotifyMessage()) ; 				// "com_notify_message" : java.lang.Boolean
		setValue(ps, i++, memberDTO.getNotifyText()) ; 					// "com_notify_text" : java.lang.Boolean
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, memberDTO.getId()) ; 							// "com_id" : java.lang.Integer
	}
}

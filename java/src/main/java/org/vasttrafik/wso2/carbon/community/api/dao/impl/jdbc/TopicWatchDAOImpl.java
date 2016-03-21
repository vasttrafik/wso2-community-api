package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.TopicWatchDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.TopicWatchDTO;

/**
 * TopicWatchDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class TopicWatchDAOImpl extends GenericDAO<TopicWatchDTO> implements TopicWatchDAO {

	private final static String SQL_SELECT = 
		"select com_id, com_topic_id, com_member_id from com_topic_watch where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_topic_watch (com_topic_id, com_member_id) values (?, ?)";

	private final static String SQL_UPDATE = 
		"update com_topic_watch set com_topic_id = ?, com_member_id = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_topic_watch where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_topic_watch";

	private final static String SQL_COUNT = 
		"select count(*) from com_topic_watch where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public TopicWatchDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private TopicWatchDTO newInstanceWithPrimaryKey(Long id) {
		TopicWatchDTO topicWatchDTO = new TopicWatchDTO();
		topicWatchDTO.setId(id);
		return topicWatchDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public TopicWatchDTO find(Long id) throws SQLException {
		TopicWatchDTO topicWatchDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(topicWatchDTO)) {
			return topicWatchDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param topicWatchDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(TopicWatchDTO topicWatchDTO) throws SQLException {
		return super.doSelect(topicWatchDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param topicWatchDTO
	 */
	@Override
	public Long insert(TopicWatchDTO topicWatchDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(topicWatchDTO);
		return key;
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param topicWatchDTO
	 * @return
	 */
	@Override
	public int update(TopicWatchDTO topicWatchDTO) throws SQLException {
		return super.doUpdate(topicWatchDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Long id) throws SQLException {
		TopicWatchDTO topicWatchDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(topicWatchDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param topicWatchDTO
	 * @return
	 */
	@Override
	public int delete(TopicWatchDTO topicWatchDTO) throws SQLException {
		return super.doDelete(topicWatchDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Long id) throws SQLException {
		TopicWatchDTO topicWatchDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(topicWatchDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param topicWatchDTO
	 * @return
	 */
	@Override
	public boolean exists(TopicWatchDTO topicWatchDTO) throws SQLException {
		return super.doExists(topicWatchDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, TopicWatchDTO topicWatchDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, topicWatchDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected TopicWatchDTO populateBean(ResultSet rs, TopicWatchDTO topicWatchDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		topicWatchDTO.setId(rs.getLong("com_id")); 				// java.lang.Long
		if (rs.wasNull()) { topicWatchDTO.setId(null); }; 		// not primitive number => keep null value if any
		topicWatchDTO.setTopicId(rs.getLong("com_topic_id")); 	// java.lang.Long
		if (rs.wasNull()) { topicWatchDTO.setTopicId(null); }; 	// not primitive number => keep null value if any
		topicWatchDTO.setMemberId(rs.getInt("com_member_id")); 	// java.lang.Integer
		if (rs.wasNull()) { topicWatchDTO.setMemberId(null); }; // not primitive number => keep null value if any
		return topicWatchDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, TopicWatchDTO topicWatchDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, topicWatchDTO.getTopicId()) ; 		// "com_topic_id" : java.lang.Long
		setValue(ps, i++, topicWatchDTO.getMemberId()) ; 		// "com_user_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, TopicWatchDTO topicWatchDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, topicWatchDTO.getTopicId()) ; 		// "com_topic_id" : java.lang.Long
		setValue(ps, i++, topicWatchDTO.getMemberId()) ; 		// "com_user_id" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, topicWatchDTO.getId()) ; 				// "com_id" : java.lang.Long
	}

}

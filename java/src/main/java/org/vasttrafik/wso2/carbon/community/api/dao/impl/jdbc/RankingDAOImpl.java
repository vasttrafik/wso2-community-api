package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.RankingDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.RankingDTO;

/**
 * RankingDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class RankingDAOImpl extends GenericDAO<RankingDTO> implements RankingDAO {

	private final static String SQL_SELECT = 
		"select com_id, com_title, com_type, com_min_points, com_image_url from com_ranking where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_ranking (com_title, com_type, com_min_points, com_image_url) values (?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_ranking set com_title = ?, com_type = ?, com_min_points = ?, com_image_url = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_ranking where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_ranking";

	private final static String SQL_COUNT = 
		"select count(*) from com_ranking where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public RankingDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private RankingDTO newInstanceWithPrimaryKey(Integer id) {
		RankingDTO rankingDTO = new RankingDTO();
		rankingDTO.setId(id);
		return rankingDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public RankingDTO find(Integer id) throws SQLException {
		RankingDTO rankingDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(rankingDTO)) {
			return rankingDTO ;
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
	 * @param rankingDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(RankingDTO rankingDTO) throws SQLException {
		return super.doSelect(rankingDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param rankingDTO
	 */
	@Override
	public Integer insert(RankingDTO rankingDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(rankingDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param rankingDTO
	 * @return
	 */
	@Override
	public int update(RankingDTO rankingDTO) throws SQLException {
		return super.doUpdate(rankingDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		RankingDTO rankingDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(rankingDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param rankingDTO
	 * @return
	 */
	@Override
	public int delete(RankingDTO rankingDTO) throws SQLException {
		return super.doDelete(rankingDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		RankingDTO rankingDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(rankingDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param rankingDTO
	 * @return
	 */
	@Override
	public boolean exists(RankingDTO rankingDTO) throws SQLException {
		return super.doExists(rankingDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, RankingDTO rankingDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, rankingDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected RankingDTO populateBean(ResultSet rs, RankingDTO rankingDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		rankingDTO.setId(rs.getInt("com_id")); 					// java.lang.Integer
		if (rs.wasNull()) { rankingDTO.setId(null); }; 			// not primitive number => keep null value if any
		rankingDTO.setTitle(rs.getString("com_title")); 		// java.lang.String
		rankingDTO.setType(rs.getString("com_type")); 			// java.lang.String
		rankingDTO.setMinPoints(rs.getInt("com_min_points")); 	// java.lang.Integer
		if (rs.wasNull()) { rankingDTO.setMinPoints(null); }; 	// not primitive number => keep null value if any
		rankingDTO.setImageUrl(rs.getString("com_image_url")); 	// java.lang.String
		return rankingDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, RankingDTO rankingDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, rankingDTO.getTitle()) ; 				// "com_title" : java.lang.String
		setValue(ps, i++, rankingDTO.getType()) ; 				// "com_type" : java.lang.String
		setValue(ps, i++, rankingDTO.getMinPoints()) ; 			// "com_min_points" : java.lang.Integer
		setValue(ps, i++, rankingDTO.getImageUrl()) ; 			// "com_image_url" : java.lang.String
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, RankingDTO rankingDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, rankingDTO.getTitle()) ; 				// "com_title" : java.lang.String
		setValue(ps, i++, rankingDTO.getType()) ; 				// "com_type" : java.lang.String
		setValue(ps, i++, rankingDTO.getMinPoints()) ; 			// "com_min_points" : java.lang.Integer
		setValue(ps, i++, rankingDTO.getImageUrl()) ; 			// "com_image_url" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, rankingDTO.getId()) ; 				// "com_id" : java.lang.Integer
	}
}

package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.TagDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.TagDTO;

/**
 * TagDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class TagDAOImpl extends GenericDAO<TagDTO> implements TagDAO {
	
	private static final Log log = LogFactory.getLog(TagDAOImpl.class);
	
	private final static String SQL_SELECT_LIST = 
		"select com_id, com_label";
	
	private final static String SQL_SELECT_WITH = 
		"with com_tag_results as";
	
	private final static String SQL_SELECT_WITH_ROWNUM = 
		SQL_SELECT_LIST + " from com_tag_results where row_num between ? and ?";
	
	private final static String SQL_FIND_BY_ID = 
		SQL_SELECT_LIST + " from com_tag where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_tag (com_label) values (?)";

	private final static String SQL_UPDATE = 
		"update com_tag set com_label = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_tag where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_tag";

	private final static String SQL_COUNT = 
		"select count(*) from com_tag where com_id = ?";
	
	private final static String SQL_ORDER_BY = 
		" order by com_label asc";
	
	private final static String SQL_ORDER_BY_ROWNUM = 
		", row_number() over (order by com_label asc) as row_num";

	//----------------------------------------------------------------------
	/**
	  * DAO constructor
	  */
	public TagDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	  * Creates a new instance of the bean and populates it with the given primary value(s)
	  * @param id;
	  * @return the new instance
	  */
	private TagDTO newInstanceWithPrimaryKey(Integer id) {
		TagDTO tagDTO = new TagDTO();
		tagDTO.setId(id);
		return tagDTO ;
	}
		
	//----------------------------------------------------------------------
	/**
	* Finds a bean by its primary key 
	* @param id;
	* @return the bean found or null if not found 
	*/
	@Override
	public TagDTO find(Integer id) throws SQLException {
		TagDTO tagDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(tagDTO)) {
			return tagDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * Finds all tags within specified range
	  * @param offset
	  * @param limit
	  * @return List of beans 
	  */
	public List<TagDTO> findAll(Integer offset, Integer limit) throws SQLException {
		List<TagDTO> tags = new ArrayList<TagDTO>();
		TagDTO tag = null;
		
		try {
			// The basic SELECT is just a list of columns and the FROM table clause
			String sql = SQL_SELECT_LIST;
						
			if (offset != null)  
				sql += SQL_ORDER_BY_ROWNUM; // Order the rows and add a row number column
			
			sql += " from com_tag";
			
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
				tag = new TagDTO();
				// Populate the bean
				populateBean(rs, tag);
				// Add the bean to the result list
				tags.add(tag);
			}
		} 
        catch (SQLException e) {
			log.error("Database error. TagDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
		
		return tags;
	}

	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param voteDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(TagDTO tagDTO) throws SQLException {
		return super.doSelect(tagDTO) ;
	}

	/**
	 * Inserts the given bean in the database 
	 * @param tagDTO
	 */
	@Override
	public Integer insert(TagDTO tagDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(tagDTO);
		return key.intValue();
	}

	/**
	 * Updates the given bean in the database 
	 * @param tagDTO
	 * @return
	 */
	@Override
	public int update(TagDTO tagDTO) throws SQLException {
		return super.doUpdate(tagDTO);
	}

	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		TagDTO tagDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(tagDTO);
	}

	/**
	 * Deletes the given bean in the database 
	 * @param tagDTO
	 * @return
	 */
	@Override
	public int delete(TagDTO tagDTO) throws SQLException {
		return super.doDelete(tagDTO);
	}

	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		TagDTO tagDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(tagDTO);
	}

	/**
	 * Checks the existence of the given bean in the database 
	 * @param tagDTO
	 * @return
	 */
	@Override
	public boolean exists(TagDTO tagDTO) throws SQLException {
		return super.doExists(tagDTO);
	}

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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, TagDTO tagDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, tagDTO.getId()) ; // "com_id" : java.lang.Integer
	}

	//----------------------------------------------------------------------
	@Override
	protected TagDTO populateBean(ResultSet rs, TagDTO tagDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		tagDTO.setId(rs.getInt("com_id")); 				// java.lang.Integer
		if (rs.wasNull()) { tagDTO.setId(null); }; 		// not primitive number => keep null value if any
		tagDTO.setLabel(rs.getString("com_label")); 	// java.lang.String
		return tagDTO ;
	}

	//----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, TagDTO tagDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, tagDTO.getLabel()) ; 			// "com_label" : java.lang.String
	}

	//----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, TagDTO tagDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, tagDTO.getLabel()) ; 			// "com_label" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, tagDTO.getId()) ; 			// "com_id" : java.lang.Integer
	}
}

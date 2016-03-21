package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.dao.CategoryDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.CategoryDTO;

/**
 * CategoryDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class CategoryDAOImpl extends GenericDAO<CategoryDTO> implements CategoryDAO {
	
	private static final Log log = LogFactory.getLog(CategoryDAOImpl.class);

	private final static String SQL_FIND = 
		"select com_id, com_name, com_is_public, com_image_url, com_num_forums from com_category";
	
	private final static String SQL_SELECT = 
		"select com_id, com_name, com_is_public, com_image_url, com_num_forums from com_category where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_category (com_name, com_is_public, com_image_url) values (?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_category set com_name = ?, com_is_public = ?, com_image_url = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_category where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_category";

	private final static String SQL_COUNT = 
		"select count(*) from com_category where com_id = ?";
	
	private final static String SQL_ORDER_BY =
		" order by com_name asc";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public CategoryDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private CategoryDTO newInstanceWithPrimaryKey(Integer id) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(id);
		return categoryDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public CategoryDTO find(Integer id) throws SQLException {
		CategoryDTO categoryDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(categoryDTO)) {
			return categoryDTO ;
		}
		else {
			return null ; // Not found
		}
	}
	
	//----------------------------------------------------------------------
	/**
	  * List categories 
	  * @param includePrivate Flag indicating if private categories should be included
	  * @return the bean found or null if not found 
	  */
	public List<CategoryDTO> find(Boolean includePrivate) throws SQLException {
		List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
		
		try {
			String sql = SQL_FIND;
			
			// Restrict result if private categories should be excluded
			if (!includePrivate)
				sql = sql + " where com_is_public = 1";
			
			// Add the ORDER BY clause
			sql = sql + SQL_ORDER_BY;
			
			getConnection();
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			CategoryDTO category = null;
			
			while (rs.next()) {
				category = new CategoryDTO();
				// Populate the DTO
				populateBean(rs, category);
				// Add DTO to list
				categories.add(category);
			}
		} 
        catch (SQLException e) {
			log.error("Database error. CategoryDAOImpl.find could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
		
		return categories;
	}
		
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param category
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(CategoryDTO categoryDTO) throws SQLException {
		return super.doSelect(categoryDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param categoryDTO
	 */
	@Override
	public Integer insert(CategoryDTO categoryDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(categoryDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param categoryDTO
	 * @return
	 */
	@Override
	public int update(CategoryDTO categoryDTO) throws SQLException {
		return super.doUpdate(categoryDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		CategoryDTO categoryDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(categoryDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param categoryDTO
	 * @return
	 */
	@Override
	public int delete(CategoryDTO categoryDTO) throws SQLException {
		return super.doDelete(categoryDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		CategoryDTO categoryDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(categoryDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param categoryDTO
	 * @return
	 */
	@Override
	public boolean exists(CategoryDTO categoryDTO) throws SQLException {
		return super.doExists(categoryDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, CategoryDTO categoryDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, categoryDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected CategoryDTO populateBean(ResultSet rs, CategoryDTO categoryDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		categoryDTO.setId(rs.getInt("com_id")); 				// java.lang.Integer
		if (rs.wasNull()) { categoryDTO.setId(null); }; 		// not primitive number => keep null value if any
		categoryDTO.setName(rs.getString("com_name")); 			// java.lang.String
		categoryDTO.setIsPublic(rs.getBoolean("com_is_public"));// java.lang.Boolean
		categoryDTO.setImageUrl(rs.getString("com_image_url")); // java.lang.String
		categoryDTO.setNumForums(rs.getInt("com_num_forums"));  // java.lang.Integer
		return categoryDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, CategoryDTO categoryDTO) throws SQLException {
		Boolean isPublic = categoryDTO.getIsPublic() != null ? categoryDTO.getIsPublic() : Boolean.TRUE;
		
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert	
		setValue(ps, i++, categoryDTO.getName()); 				// "com_name" : java.lang.String
		setValue(ps, i++, isPublic); 							// "com_is_public" : java.lang.Boolean
		setValue(ps, i++, categoryDTO.getImageUrl()); 			// "com_image_url" : java.lang.String
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, CategoryDTO categoryDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, categoryDTO.getName()); 				// "com_name" : java.lang.String
		setValue(ps, i++, categoryDTO.getIsPublic()); 			// "com_is_public" : java.lang.Byte
		setValue(ps, i++, categoryDTO.getImageUrl()); 			// "com_image_url" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, categoryDTO.getId()); 				// "com_id" : java.lang.Integer
	}
}

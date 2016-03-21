package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.FolderDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.FolderDTO;

/**
 * FolderDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class FolderDAOImpl extends GenericDAO<FolderDTO> implements FolderDAO {

	private final static String SQL_SELECT = 
		"select com_id, com_parent_id, com_member_id, com_name, com_type, com_image_url, com_num_msgs, com_num_unread_msgs, com_size from com_folder where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_folder (com_parent_id, com_member_id, com_name, com_type, com_image_url, com_num_msgs, com_num_unread_msgs, com_size) values (?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_folder set com_parent_id = ?, com_member_id = ?, com_name = ?, com_type = ?, com_image_url = ?, com_num_msgs = ?, com_num_unread_msgs = ?, com_size = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_folder where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_folder";

	private final static String SQL_COUNT = 
		"select count(*) from com_folder where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public FolderDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private FolderDTO newInstanceWithPrimaryKey(Integer id) {
		FolderDTO folderDTO = new FolderDTO();
		folderDTO.setId(id);
		return folderDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public FolderDTO find(Integer id) throws SQLException {
		FolderDTO folderDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(folderDTO)) {
			return folderDTO ;
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
	 * @param folderDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(FolderDTO folderDTO) throws SQLException {
		return super.doSelect(folderDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param folderDTO
	 */
	@Override
	public Integer insert(FolderDTO folderDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(folderDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param folderDTO
	 * @return
	 */
	@Override
	public int update(FolderDTO folderDTO) throws SQLException {
		return super.doUpdate(folderDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		FolderDTO folderDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(folderDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param folderDTO
	 * @return
	 */
	@Override
	public int delete(FolderDTO folderDTO) throws SQLException {
		return super.doDelete(folderDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		FolderDTO folderDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(folderDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param folderDTO
	 * @return
	 */
	@Override
	public boolean exists(FolderDTO folderDTO) throws SQLException {
		return super.doExists(folderDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, FolderDTO folderDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, folderDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected FolderDTO populateBean(ResultSet rs, FolderDTO folderDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		folderDTO.setId(rs.getInt("com_id")); 							// java.lang.Integer
		if (rs.wasNull()) { folderDTO.setId(null); }; 					// not primitive number => keep null value if any
		folderDTO.setParentId(rs.getInt("com_parent_id")); 				// java.lang.Integer
		if (rs.wasNull()) { folderDTO.setParentId(null); }; 			// not primitive number => keep null value if any
		folderDTO.setMemberId(rs.getInt("com_member_id")); 				// java.lang.Integer
		if (rs.wasNull()) { folderDTO.setMemberId(null); }; 			// not primitive number => keep null value if any
		folderDTO.setName(rs.getString("com_name")); 					// java.lang.String
		folderDTO.setType(rs.getString("com_type")); 					// java.lang.String
		folderDTO.setImageUrl(rs.getString("com_image_url")); 			// java.lang.String
		folderDTO.setNumMsgs(rs.getShort("com_num_msgs")); 				// java.lang.Short
		if (rs.wasNull()) { folderDTO.setNumMsgs(null); }; 				// not primitive number => keep null value if any
		folderDTO.setNumUnreadMsgs(rs.getShort("com_num_unread_msgs")); // java.lang.Short
		if (rs.wasNull()) { folderDTO.setNumUnreadMsgs(null); }; 		// not primitive number => keep null value if any
		folderDTO.setSize(rs.getInt("com_size")); 						// java.lang.Integer
		if (rs.wasNull()) { folderDTO.setSize(null); }; 				// not primitive number => keep null value if any
		return folderDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, FolderDTO folderDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, folderDTO.getParentId()) ; 					// "com_parent_id" : java.lang.Integer
		setValue(ps, i++, folderDTO.getMemberId()) ; 					// "com_user_id" : java.lang.Integer
		setValue(ps, i++, folderDTO.getName()) ; 						// "com_name" : java.lang.String
		setValue(ps, i++, folderDTO.getType()) ; 						// "com_type" : java.lang.String
		setValue(ps, i++, folderDTO.getImageUrl()) ; 					// "com_image_url" : java.lang.String
		setValue(ps, i++, folderDTO.getNumMsgs()) ; 					// "com_num_msgs" : java.lang.Short
		setValue(ps, i++, folderDTO.getNumUnreadMsgs()) ; 				// "com_num_unread_msgs" : java.lang.Short
		setValue(ps, i++, folderDTO.getSize()) ; 						// "com_size" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, FolderDTO folderDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, folderDTO.getParentId()) ; 					// "com_parent_id" : java.lang.Integer
		setValue(ps, i++, folderDTO.getMemberId()) ; 					// "com_user_id" : java.lang.Integer
		setValue(ps, i++, folderDTO.getName()) ; 						// "com_name" : java.lang.String
		setValue(ps, i++, folderDTO.getType()) ; 						// "com_type" : java.lang.String
		setValue(ps, i++, folderDTO.getImageUrl()) ; 					// "com_image_url" : java.lang.String
		setValue(ps, i++, folderDTO.getNumMsgs()) ; 					// "com_num_msgs" : java.lang.Short
		setValue(ps, i++, folderDTO.getNumUnreadMsgs()) ; 				// "com_num_unread_msgs" : java.lang.Short
		setValue(ps, i++, folderDTO.getSize()) ; 						// "com_size" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, folderDTO.getId()) ; 							// "com_id" : java.lang.Integer
	}

}

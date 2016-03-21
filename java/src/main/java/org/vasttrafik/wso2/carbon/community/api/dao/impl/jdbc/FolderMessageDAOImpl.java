package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.FolderMessageDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.FolderMessageDTO;

/**
 * FolderMessageDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class FolderMessageDAOImpl extends GenericDAO<FolderMessageDTO> implements FolderMessageDAO {

	private final static String SQL_SELECT = 
		"select com_id, com_folder_id, com_message_id, com_read_date from com_folder_message where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_folder_message (com_folder_id, com_message_id, com_read_date) values (?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_folder_message set com_folder_id = ?, com_message_id = ?, com_read_date = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_folder_message where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_folder_message";

	private final static String SQL_COUNT = 
		"select count(*) from com_folder_message where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public FolderMessageDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private FolderMessageDTO newInstanceWithPrimaryKey(Integer id) {
		FolderMessageDTO folderMessageDTO = new FolderMessageDTO();
		folderMessageDTO.setId(id);
		return folderMessageDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public FolderMessageDTO find(Integer id) throws SQLException {
		FolderMessageDTO folderMessageDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(folderMessageDTO)) {
			return folderMessageDTO ;
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
	 * @param folderMessageDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(FolderMessageDTO folderMessageDTO) throws SQLException {
		return super.doSelect(folderMessageDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param folderMessageDTO
	 */
	@Override
	public Integer insert(FolderMessageDTO folderMessageDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(folderMessageDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param folderMessageDTO
	 * @return
	 */
	@Override
	public int update(FolderMessageDTO folderMessageDTO) throws SQLException {
		return super.doUpdate(folderMessageDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		FolderMessageDTO folderMessageDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(folderMessageDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param folderMessageDTO
	 * @return
	 */
	@Override
	public int delete(FolderMessageDTO folderMessageDTO) throws SQLException {
		return super.doDelete(folderMessageDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		FolderMessageDTO folderMessageDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(folderMessageDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param folderMessageDTO
	 * @return
	 */
	@Override
	public boolean exists(FolderMessageDTO folderMessageDTO) throws SQLException {
		return super.doExists(folderMessageDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, FolderMessageDTO folderMessageDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, folderMessageDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected FolderMessageDTO populateBean(ResultSet rs, FolderMessageDTO folderMessageDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		folderMessageDTO.setId(rs.getInt("com_id")); 					// java.lang.Integer
		if (rs.wasNull()) { folderMessageDTO.setId(null); }; 			// not primitive number => keep null value if any
		folderMessageDTO.setFolderId(rs.getInt("com_folder_id")); 		// java.lang.Integer
		if (rs.wasNull()) { folderMessageDTO.setFolderId(null); }; 		// not primitive number => keep null value if any
		folderMessageDTO.setMessageId(rs.getInt("com_message_id")); 	// java.lang.Integer
		if (rs.wasNull()) { folderMessageDTO.setMessageId(null); }; 	// not primitive number => keep null value if any
		folderMessageDTO.setReadDate(rs.getDate("com_read_date")); 		// java.util.Date
		return folderMessageDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, FolderMessageDTO folderMessageDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, folderMessageDTO.getFolderId()) ; 			// "com_folder_id" : java.lang.Integer
		setValue(ps, i++, folderMessageDTO.getMessageId()) ; 			// "com_message_id" : java.lang.Integer
		setValue(ps, i++, folderMessageDTO.getReadDate()) ; 			// "com_read_date" : java.util.Date
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, FolderMessageDTO folderMessageDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, folderMessageDTO.getFolderId()) ; 			// "com_folder_id" : java.lang.Integer
		setValue(ps, i++, folderMessageDTO.getMessageId()) ; 			// "com_message_id" : java.lang.Integer
		setValue(ps, i++, folderMessageDTO.getReadDate()) ; 			// "com_read_date" : java.util.Date
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, folderMessageDTO.getId()) ; 					// "com_id" : java.lang.Integer
	}
}

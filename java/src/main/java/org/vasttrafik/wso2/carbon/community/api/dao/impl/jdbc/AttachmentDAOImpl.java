package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.AttachmentDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.AttachmentDTO;

/**
 * Attachment DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class AttachmentDAOImpl extends GenericDAO<AttachmentDTO> implements AttachmentDAO {

	private final static String SQL_SELECT = 
		"select com_id, com_message_id, com_file_name, com_content, com_mime_type, com_size from com_attachment where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_attachment ( com_message_id, com_file_name, com_content, com_mime_type, com_size ) values ( ?, ?, ?, ?, ? )";

	private final static String SQL_UPDATE = 
		"update com_attachment set com_message_id = ?, com_file_name = ?, com_content = ?, com_mime_type = ?, com_size = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_attachment where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_attachment";

	private final static String SQL_COUNT = 
		"select count(*) from com_attachment where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public AttachmentDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private AttachmentDTO newInstanceWithPrimaryKey(Integer id) {
		AttachmentDTO attachment = new AttachmentDTO();
		attachment.setId(id);
		return attachment ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public AttachmentDTO find(Integer id) throws SQLException {
		AttachmentDTO attachment = newInstanceWithPrimaryKey(id) ;
		if ( super.doSelect(attachment) ) {
			return attachment ;
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
	 * @param attachmentDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(AttachmentDTO attachmentDTO) throws SQLException {
		return super.doSelect(attachmentDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param attachmattachmentDTOent
	 */
	@Override
	public Integer insert(AttachmentDTO attachmentDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(attachmentDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param attachmentDTO
	 * @return
	 */
	@Override
	public int update(AttachmentDTO attachmentDTO) throws SQLException {
		return super.doUpdate(attachmentDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		AttachmentDTO attachment = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(attachment);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param attachmentDTO
	 * @return
	 */
	@Override
	public int delete(AttachmentDTO attachmentDTO) throws SQLException {
		return super.doDelete(attachmentDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		AttachmentDTO attachment = newInstanceWithPrimaryKey(id) ;
		return super.doExists(attachment);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param attachment
	 * @return
	 */
	@Override
	public boolean exists(AttachmentDTO attachmentDTO) throws SQLException {
		return super.doExists(attachmentDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, AttachmentDTO attachment) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, attachment.getId() ) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected AttachmentDTO populateBean(ResultSet rs, AttachmentDTO attachment) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		attachment.setId(rs.getInt("com_id")); 					// java.lang.Integer
		if ( rs.wasNull() ) { attachment.setId(null); }; 		// not primitive number => keep null value if any
		attachment.setMessageId(rs.getInt("com_message_id")); 	// java.lang.Integer
		if ( rs.wasNull() ) { attachment.setMessageId(null); }; // not primitive number => keep null value if any
		attachment.setFileName(rs.getString("com_file_name")); 	// java.lang.String
		attachment.setContent(rs.getBytes("com_content")); 		// byte[]
		attachment.setMimeType(rs.getString("com_mime_type")); 	// java.lang.String
		attachment.setSize(rs.getInt("com_size")); 				// java.lang.Integer
		if ( rs.wasNull() ) { attachment.setSize(null); }; 		// not primitive number => keep null value if any
		return attachment ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, AttachmentDTO attachment) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, attachment.getMessageId() ) ; 	// "com_message_id" : java.lang.Integer
		setValue(ps, i++, attachment.getFileName() ) ; 		// "com_file_name" : java.lang.String
		setValue(ps, i++, attachment.getContent() ) ; 		// "com_content" : byte[]
		setValue(ps, i++, attachment.getMimeType() ) ; 		// "com_mime_type" : java.lang.String
		setValue(ps, i++, attachment.getSize() ) ; 			// "com_size" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, AttachmentDTO attachment) throws SQLException {
		//--- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, attachment.getMessageId() ) ; 	// "com_message_id" : java.lang.Integer
		setValue(ps, i++, attachment.getFileName() ) ; 		// "com_file_name" : java.lang.String
		setValue(ps, i++, attachment.getContent() ) ; 		// "com_content" : byte[]
		setValue(ps, i++, attachment.getMimeType() ) ; 		// "com_mime_type" : java.lang.String
		setValue(ps, i++, attachment.getSize() ) ; 			// "com_size" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, attachment.getId() ) ; 			// "com_id" : java.lang.Integer
	}

}

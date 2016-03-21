package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.ReportMeasureDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.ReportMeasureDTO;

/**
 * ReportMeasureDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class ReportMeasureDAOImpl extends GenericDAO<ReportMeasureDTO> implements ReportMeasureDAO {
	
	private final static String SQL_SELECT = 
		"select com_id, com_report_id, com_type, com_message_id, com_measure_date, com_rectified_by_id, com_reported_by_id, com_notification_id from com_report_measure where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_report_measure (com_report_id, com_type, com_message_id, com_measure_date, com_rectified_by_id, com_notification_id) values (?, ?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_report_measure set com_report_id = ?, com_type = ?, com_message_id = ?, com_measure_date = ?, com_rectified_by_id = ?, com_notification_id = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_report_measure where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_report_measure";

	private final static String SQL_COUNT = 
		"select count(*) from com_report_measure where com_id = ?";

	//----------------------------------------------------------------------
	/**
	* DAO constructor
	*/
	public ReportMeasureDAOImpl() {
		super();
	}
	
	//----------------------------------------------------------------------
	/**
	* Creates a new instance of the bean and populates it with the given primary value(s)
	* @param id;
	* @return the new instance
	*/
	private ReportMeasureDTO newInstanceWithPrimaryKey(Integer id) {
		ReportMeasureDTO measureDTO = new ReportMeasureDTO();
		measureDTO.setId(id);
		return measureDTO ;
	}
		
	//----------------------------------------------------------------------
	/**
	* Finds a bean by its primary key 
	* @param id;
	* @return the bean found or null if not found 
	*/
	@Override
	public ReportMeasureDTO find(Integer id) throws SQLException {
		ReportMeasureDTO measureDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(measureDTO)) {
			return measureDTO ;
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
	* @param measureDTO
	* @return true if found, false if not found
	*/
	@Override
	public boolean load(ReportMeasureDTO measureDTO) throws SQLException {
		return super.doSelect(measureDTO) ;
	}

	//----------------------------------------------------------------------
	/**
	* Inserts the given bean in the database 
	* @param measureDTO
	*/
	@Override
	public Integer insert(ReportMeasureDTO measureDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(measureDTO);
		return key.intValue();
	}

	//----------------------------------------------------------------------
	/**
	* Updates the given bean in the database 
	* @param measureDTO
	* @return
	*/
	@Override
	public int update(ReportMeasureDTO measureDTO) throws SQLException {
		return super.doUpdate(measureDTO);
	}	

	//----------------------------------------------------------------------
	/**
	* Deletes the record in the database using the given primary key value(s) 
	* @param id;
	* @return
	*/
	@Override
	public int delete(Integer id) throws SQLException {
		ReportMeasureDTO measureDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(measureDTO);
	}

	//----------------------------------------------------------------------
	/**
	* Deletes the given bean in the database 
	* @param postreportDTODTO
	* @return
	*/
	@Override
	public int delete(ReportMeasureDTO measureDTO) throws SQLException {
		return super.doDelete(measureDTO);
	}

	//----------------------------------------------------------------------
	/**
	* Checks the existence of a record in the database using the given primary key value(s)
	* @param id;
	* @return
	*/
	@Override
	public boolean exists(Integer id) throws SQLException {
		ReportMeasureDTO measureDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(measureDTO);
	}

	//----------------------------------------------------------------------
	/**
	* Checks the existence of the given bean in the database 
	* @param postDTO
	* @return
	*/
	@Override
	public boolean exists(ReportMeasureDTO measureDTO) throws SQLException {
		return super.doExists(measureDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ReportMeasureDTO measureDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, measureDTO.getId()) ; // "com_id" : java.lang.Integer
	}
	
	//----------------------------------------------------------------------
	@Override
	protected ReportMeasureDTO populateBean(ResultSet rs, ReportMeasureDTO measureDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		measureDTO.setId(rs.getInt("com_id")); 							// java.lang.Integer
		if (rs.wasNull()) { measureDTO.setId(null); }; 					// not primitive number => keep null value if any
		measureDTO.setReportId(rs.getInt("com_report_id")); 			// java.lang.Integer
		if (rs.wasNull()) { measureDTO.setReportId(null); }; 			// not primitive number => keep null value if any
		measureDTO.setType(rs.getString("com_type")); 					// java.lang.String
		measureDTO.setMessageId(rs.getInt("com_message_id")); 			// java.lang.Integer	
		if (rs.wasNull()) { measureDTO.setMessageId(null); }; 			// not primitive number => keep null value if any
		measureDTO.setMeasureDate(rs.getDate("com_measure_date")); 		// java.util.Date
		measureDTO.setRectifiedById(rs.getInt("com_rectified_by_id")); 	// java.lang.Integer
		if (rs.wasNull()) { measureDTO.setRectifiedById(null); }; 		// not primitive number => keep null value if any
		measureDTO.setNotificationId(rs.getInt("com_notification_id")); // java.lang.Integer
		if (rs.wasNull()) { measureDTO.setNotificationId(null); }; 		// not primitive number => keep null value if any
		return measureDTO ;
	}
	
	//----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ReportMeasureDTO measureDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, measureDTO.getReportId()) ; 					// "com_report_id" : java.lang.Integer
		setValue(ps, i++, measureDTO.getType()) ; 						// "com_type" : java.lang.String
		setValue(ps, i++, measureDTO.getMessageId()) ; 					// "com_message_id" : java.lang.String
		setValue(ps, i++, measureDTO.getMeasureDate()) ; 				// "com_measure_date" : java.util.Date
		setValue(ps, i++, measureDTO.getRectifiedById()) ; 				// "com_rectified_by_id" : java.lang.Integer
		setValue(ps, i++, measureDTO.getNotificationId()) ; 			// "com_notification_id" : java.lang.Integer
	}
	
	//----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ReportMeasureDTO measureDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, measureDTO.getReportId()) ; 					// "com_report_id" : java.lang.Integer
		setValue(ps, i++, measureDTO.getType()) ; 						// "com_type" : java.lang.String
		setValue(ps, i++, measureDTO.getMessageId()) ; 					// "com_message_id" : java.lang.String
		setValue(ps, i++, measureDTO.getMeasureDate()) ; 				// "com_measure_date" : java.util.Date
		setValue(ps, i++, measureDTO.getRectifiedById()) ; 				// "com_rectified_by_id" : java.lang.Integer
		setValue(ps, i++, measureDTO.getNotificationId()) ; 			// "com_notification_id" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, measureDTO.getId()) ; 							// "com_id" : java.lang.Integer
	}
}

package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.ReportDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.ReportDTO;

/**
 * ReportDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class ReportDAOImpl extends GenericDAO<ReportDTO> implements ReportDAO {
	
	private final static String SQL_SELECT = 
		"select com_id, com_post_id, com_type, com_text, com_report_date, com_create_date, com_reported_by_id from com_report where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_report (com_post_id, com_type, com_text, com_report_date, com_reported_by_id) values (?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE = 
		"update com_report set com_post_id = ?, com_type = ?, com_text = ?, com_report_date = ?, com_reported_by_id = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_report where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_report";

	private final static String SQL_COUNT = 
		"select count(*) from com_report where com_id = ?";

	//----------------------------------------------------------------------
	/**
	* DAO constructor
	*/
	public ReportDAOImpl() {
		super();
	}
	
	//----------------------------------------------------------------------
	/**
	* Creates a new instance of the bean and populates it with the given primary value(s)
	* @param id;
	* @return the new instance
	*/
	private ReportDTO newInstanceWithPrimaryKey(Integer id) {
		ReportDTO reportDTO = new ReportDTO();
		reportDTO.setId(id);
		return reportDTO ;
	}
	
	//----------------------------------------------------------------------
	/**
	* Finds a bean by its primary key 
	* @param id;
	* @return the bean found or null if not found 
	*/
	@Override
	public ReportDTO find(Integer id) throws SQLException {
		ReportDTO reportDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(reportDTO)) {
			return reportDTO ;
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
	* @param reportDTO
	* @return true if found, false if not found
	*/
	@Override
	public boolean load(ReportDTO reportDTO) throws SQLException {
		return super.doSelect(reportDTO) ;
	}

	//----------------------------------------------------------------------
	/**
	* Inserts the given bean in the database 
	* @param reportDTO
	*/
	@Override
	public Integer insert(ReportDTO reportDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(reportDTO);
		return key.intValue();
	}

	//----------------------------------------------------------------------
	/**
	* Updates the given bean in the database 
	* @param postDTO
	* @return
	*/
	@Override
	public int update(ReportDTO reportDTO) throws SQLException {
		return super.doUpdate(reportDTO);
	}	

	//----------------------------------------------------------------------
	/**
	* Deletes the record in the database using the given primary key value(s) 
	* @param id;
	* @return
	*/
	@Override
	public int delete(Integer id) throws SQLException {
		ReportDTO reportDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(reportDTO);
	}

	//----------------------------------------------------------------------
	/**
	* Deletes the given bean in the database 
	* @param postreportDTODTO
	* @return
	*/
	@Override
	public int delete(ReportDTO reportDTO) throws SQLException {
		return super.doDelete(reportDTO);
	}

	//----------------------------------------------------------------------
	/**
	* Checks the existence of a record in the database using the given primary key value(s)
	* @param id;
	* @return
	*/
	@Override
	public boolean exists(Integer id) throws SQLException {
		ReportDTO reportDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(reportDTO);
	}

	//----------------------------------------------------------------------
	/**
	* Checks the existence of the given bean in the database 
	* @param postDTO
	* @return
	*/
	@Override
	public boolean exists(ReportDTO reportDTO) throws SQLException {
		return super.doExists(reportDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ReportDTO reportDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, reportDTO.getId()) ; // "com_id" : java.lang.Integer
	}
	
	//----------------------------------------------------------------------
	@Override
	protected ReportDTO populateBean(ResultSet rs, ReportDTO reportDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		reportDTO.setId(rs.getInt("com_id")); 							// java.lang.Integer
		if (rs.wasNull()) { reportDTO.setId(null); }; 					// not primitive number => keep null value if any
		reportDTO.setPostId(rs.getInt("com_post_id")); 					// java.lang.Integer
		if (rs.wasNull()) { reportDTO.setPostId(null); }; 				// not primitive number => keep null value if any
		reportDTO.setType(rs.getString("com_type")); 					// java.lang.String
		reportDTO.setText(rs.getString("com_text")); 					// java.lang.String	
		reportDTO.setReportDate(rs.getDate("com_report_date")); 		// java.util.Date
		reportDTO.setReportedById(rs.getInt("com_reported_by_id")); 	// java.lang.Integer
		if (rs.wasNull()) { reportDTO.setReportedById(null); }; 		// not primitive number => keep null value if any
		return reportDTO ;
	}
	
	//----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ReportDTO reportDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, reportDTO.getPostId()) ; 						// "com_post_id" : java.lang.Integer
		setValue(ps, i++, reportDTO.getType()) ; 						// "com_type" : java.lang.String
		setValue(ps, i++, reportDTO.getText()) ; 						// "com_text" : java.lang.String
		setValue(ps, i++, reportDTO.getReportDate()) ; 					// "com_report_date" : java.util.Date
		setValue(ps, i++, reportDTO.getReportedById()) ; 				// "com_reported_by_id" : java.lang.Integer
	}
	
	//----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ReportDTO reportDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, reportDTO.getPostId()) ; 						// "com_post_id" : java.lang.Integer
		setValue(ps, i++, reportDTO.getType()) ; 						// "com_type" : java.lang.String
		setValue(ps, i++, reportDTO.getText()) ; 						// "com_text" : java.lang.String
		setValue(ps, i++, reportDTO.getReportDate()) ; 					// "com_report_date" : java.util.Date
		setValue(ps, i++, reportDTO.getReportedById()) ; 				// "com_reported_by_id" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, reportDTO.getId()) ; 							// "com_id" : java.lang.Integer
	}
}

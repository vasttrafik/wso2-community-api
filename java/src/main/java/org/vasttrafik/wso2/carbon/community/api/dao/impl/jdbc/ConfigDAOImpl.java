package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vasttrafik.wso2.carbon.community.api.dao.ConfigDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons.GenericDAO;
import org.vasttrafik.wso2.carbon.community.api.model.ConfigurationDTO;

/**
 * ConfigurationDTO DAO implementation 
 * 
 * @author Lars Andersson
 *
 */
public final class ConfigDAOImpl extends GenericDAO<ConfigurationDTO> implements ConfigDAO {

	private final static String SQL_SELECT = 
		"select com_id, com_name, com_value from com_config where com_id = ?";

	private final static String SQL_INSERT = 
		"insert into com_config (com_name, com_value) values (?, ?)";

	private final static String SQL_UPDATE = 
		"update com_config set com_name = ?, com_value = ? where com_id = ?";

	private final static String SQL_DELETE = 
		"delete from com_config where com_id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from com_config";

	private final static String SQL_COUNT = 
		"select count(*) from com_config where com_id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public ConfigDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private ConfigurationDTO newInstanceWithPrimaryKey(Integer id) {
		ConfigurationDTO configurationDTO = new ConfigurationDTO();
		configurationDTO.setId(id);
		return configurationDTO ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param id;
	 * @return the bean found or null if not found 
	 */
	@Override
	public ConfigurationDTO find(Integer id) throws SQLException {
		ConfigurationDTO configurationDTO = newInstanceWithPrimaryKey(id) ;
		if (super.doSelect(configurationDTO)) {
			return configurationDTO ;
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
	 * @param configurationDTO
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load(ConfigurationDTO configurationDTO) throws SQLException {
		return super.doSelect(configurationDTO) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param configurationDTO
	 */
	@Override
	public Integer insert(ConfigurationDTO configurationDTO) throws SQLException {
		Long key = super.doInsertAutoIncr(configurationDTO);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param configurationDTO
	 * @return
	 */
	@Override
	public int update(ConfigurationDTO configurationDTO) throws SQLException {
		return super.doUpdate(configurationDTO);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param id;
	 * @return
	 */
	@Override
	public int delete(Integer id) throws SQLException {
		ConfigurationDTO configurationDTO = newInstanceWithPrimaryKey(id) ;
		return super.doDelete(configurationDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param configurationDTO
	 * @return
	 */
	@Override
	public int delete(ConfigurationDTO configurationDTO) throws SQLException {
		return super.doDelete(configurationDTO);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		ConfigurationDTO configurationDTO = newInstanceWithPrimaryKey(id) ;
		return super.doExists(configurationDTO);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param configurationDTO
	 * @return
	 */
	@Override
	public boolean exists(ConfigurationDTO configurationDTO) throws SQLException {
		return super.doExists(configurationDTO);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ConfigurationDTO configurationDTO) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, configurationDTO.getId()) ; // "com_id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected ConfigurationDTO populateBean(ResultSet rs, ConfigurationDTO configurationDTO) throws SQLException {
		//--- Set data from ResultSet to Bean attributes
		configurationDTO.setId(rs.getInt("com_id")); 			// java.lang.Integer
		if (rs.wasNull()) { configurationDTO.setId(null); }; 	// not primitive number => keep null value if any
		configurationDTO.setName(rs.getString("com_name")); 	// java.lang.String
		configurationDTO.setValue(rs.getString("com_value")); 	// java.lang.String
		return configurationDTO ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ConfigurationDTO configurationDTO) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		// "com_id" is auto-incremented => no set in insert		
		setValue(ps, i++, configurationDTO.getName()) ; 		// "com_name" : java.lang.String
		setValue(ps, i++, configurationDTO.getValue()) ; 		// "com_value" : java.lang.String
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ConfigurationDTO configurationDTO) throws SQLException {
		//--- Set DATA from bean to PreparedStatement (SQL "SET x=?, y=?, ...")
		setValue(ps, i++, configurationDTO.getName()) ; 		// "com_name" : java.lang.String
		setValue(ps, i++, configurationDTO.getValue()) ; 		// "com_value" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement (SQL "WHERE key=?, ...")
		setValue(ps, i++, configurationDTO.getId()) ; 			// "com_id" : java.lang.Integer
	}
}

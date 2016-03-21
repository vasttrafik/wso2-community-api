package org.vasttrafik.wso2.carbon.community.api.dao.impl.jdbc.commons;

import java.math.BigDecimal;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.vasttrafik.wso2.carbon.community.api.dao.TransactionalDAO;

/**
 * Generic abstract class for basic JDBC DAO
 * 
 * @author Lars Andersson
 *
 * @param <T> DAO Interface
 */
public abstract class GenericDAO<T> implements TransactionalDAO {
	
    private static final Log log = LogFactory.getLog(GenericDAO.class);

    /**
	 * The DataSource providing the connections
	 */
	private DataSource dataSource;

    /**
     * A connection to use for SQL statements
     */
    protected Connection conn = null;
    
    /**
     * A Prepared statement to use for SQL statements
     */
    protected PreparedStatement ps = null;

    /**
     * A resultset to use for SQL statements
     */
    protected ResultSet rs = null;
    
    /**
     * Flag indicating if we should execute statements in autocommmit mode
     */
    protected boolean autoCommit = true;

	
	/**
	 * Constructor
	 */
	protected GenericDAO() {
		super();
	}
	
	/**
     * Retrieve a connection from the data source
     * @return A database connection
     * @throws SQLException
     */
	public Connection getConnection() throws SQLException {
        if (dataSource == null)
          dataSource = DataSourceProvider.getDataSource() ;

        if (conn == null)
        	conn = dataSource.getConnection();
        
    	conn.setAutoCommit(autoCommit);
        
		return conn;
	}
	
	/**
	 * Sets the connection. This allows for the use of the same connection across DAO:s, which in turn
	 * is required in order to execute multiple statements as a single transaction.
	 * @param connection The connection to set
	 */
	public void setConnection(Connection connection) {
		this.conn = connection;
	}
	
	/**
	 * Sets the auto commit mode for connections
	 * @param autoCommit If connections should be set to auto commit mode
	 */
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}
	
	/**
	 * Commits the current transaction
	 * @throws SQLException If the current transaction could not be committed
	 */
	public void commitTransaction(boolean closeConnection) throws SQLException {
		if (conn != null) {
			conn.commit();
		
			if (closeConnection)
				closeConnection();
		}
	}
	
	/**
	 * Rollbacks the current transaction
	 * @throws SQLException If the current transaction could not be committed
	 */
	public void rollbackTransaction(boolean closeConnection) throws SQLException {
		if (conn != null) {
			conn.rollback();
			
			if (closeConnection)
				closeConnection();
		}
	}

	/**
	 * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from the database
	 * @return The SQL SELECT string
	 */
	protected abstract String getSqlSelect();
	
	/**
	 * Returns the SQL INSERT REQUEST to be used to insert the bean in the database
	 * @return The SQL INSERT string
	 */
	protected abstract String getSqlInsert();
	
	/**
	 * Returns the SQL UPDATE REQUEST to be used to update the bean in the database
	 * @return The SQL UPDATE string
	 */
	protected abstract String getSqlUpdate();
	
	/**
	 * Returns the SQL DELETE REQUEST to be used to delete the bean from the database
	 * @return The SQL DELETE string
	 */
	protected abstract String getSqlDelete();
	
	/**
	 * Returns the SQL COUNT REQUEST to be used to check if the bean exists in the database
	 * @return The SQL SELECT COUNT string
	 */
	protected abstract String getSqlCount();
	
	/**
	 * Returns the SQL COUNT REQUEST to be used to count all the beans present in the database
	 * @return The SQL SELECT COUNT string
	 */
	protected abstract String getSqlCountAll();
	
	/**
	 * Set the primary key value(s) in the given PreparedStatement
	 * @param ps PreparedStatement
	 * @param i Parameter position
	 * @param bean The bean
	 * @throws SQLException
	 */
	protected abstract void setValuesForPrimaryKey(PreparedStatement ps, int i, T bean) throws SQLException ;
	
	/**
	 * Set the bean values in the given PreparedStatement before SQL INSERT
	 * @param ps PreparedStatement
	 * @param i Parameter position
	 * @param bean The bean
	 * @throws SQLException
	 */
	protected abstract void setValuesForInsert(PreparedStatement ps, int i, T bean) throws SQLException ; 
	
	/**
	 * Set the bean values in the given PreparedStatement before SQL UPDATE
	 * @param ps PreparedStatement
	 * @param i Parameter position
	 * @param bean The bean
	 * @throws SQLException
	 */
	protected abstract void setValuesForUpdate(PreparedStatement ps, int i, T bean) throws SQLException ; 
	
	/**
	 * Populates the bean attributes from the given ResultSet
	 * @param rs The ResultSet
	 * @param bean The bean
	 * @return
	 * @throws SQLException
	 */
	protected abstract T populateBean(ResultSet rs, T bean) throws SQLException ;
    
    /**
     * Close the connection streams.
     * @param ps Preparred statement to close
     * @param con Connection to close
     * @param rs ResultSet to close
     */
    protected void closeAll() {
    	if (autoCommit)
    		closeConnection();
    	
        closeResultSet();
        closeStatement();
    }

    /**
     * Close connection.
     */
    protected void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} 
            catch (SQLException e) {
                log.warn("Database error. Could not close connection  - " + e.getMessage(), e);
            }
            finally {
                conn = null;
            }
		}
	}

    /**
     * Close ResultSet
     */
    protected void closeResultSet() {
        if (rs != null) {
            try {
                rs.close();
            } 
            catch (SQLException e) {
                log.warn("Database error. Could not close ResultSet  - " + e.getMessage(), e);
            }
            finally {
                rs = null;
            }
        }
    }

    /**
     * Close PreparedStatement
     */
    protected void closeStatement() {
        if (ps != null) {
            try {
                ps.close();
            } 
            catch (SQLException e) {
                log.warn("Database error. Could not close PreparedStatement." + e.getMessage(), e);
            }
            finally {
                ps = null;
            }
        }
    }
    
	/**
	 * Loads the given bean from the database using its primary key (SQL SELECT)<br>
	 * The given bean is populated from the ResultSet if found
	 * @param bean
	 * @return true if found and loaded, false if not found
	 */
	protected boolean doSelect(T bean) throws SQLException {
		boolean result = false;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getSqlSelect());
			setValuesForPrimaryKey(ps, 1, bean); 
			rs = ps.executeQuery();

			if (rs.next()) {
				populateBean(rs, bean);
				result = true;
			}
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doSelect could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
		return result;
	}

	/**
	 * Inserts the given bean in the database (SQL INSERT)
	 * @param bean
	 */
	protected void doInsert(T bean) throws SQLException {
		try {
			conn = getConnection();
			ps = conn.prepareStatement( getSqlInsert() );
			setValuesForInsert(ps, 1, bean);
			ps.executeUpdate();
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doInsert could not execute update." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}
	}	
    
	/**
	 * Inserts the given bean in the database (SQL INSERT) with an auto-incremented columns
	 * @param bean
	 */
	protected Long doInsertAutoIncr(T bean) throws SQLException {
		Long generatedKey = 0L ;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(getSqlInsert(), PreparedStatement.RETURN_GENERATED_KEYS);
			setValuesForInsert(ps, 1, bean); 
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if ( rs.next() ) {
				generatedKey = rs.getLong(1);
			}
			
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doInsertAutoIncr could not execute update." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}

		return generatedKey;
	}	
    
	/**
	 * Updates the given bean in the database (SQL UPDATE)
	 * @param bean
	 * @return the JDBC return code (i.e. the row count affected by the UPDATE operation : 0 or 1 )
	 */
	protected int doUpdate(T bean) throws SQLException {
		int result = 0 ;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(getSqlUpdate());
			setValuesForUpdate(ps, 1, bean); 
			result = ps.executeUpdate();
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doUpdate could not execute update." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}

		return result ;
	}	
	
	/**
	 * Deletes the given bean in the database (SQL DELETE)
	 * @param bean
	 * @return the JDBC return code (i.e. the row count affected by the DELETE operation : 0 or 1 )
	 */
	protected int doDelete(T bean) throws SQLException {
		int result = 0 ;
 
		try {
			conn = getConnection();
			ps = conn.prepareStatement( getSqlDelete() );
			setValuesForPrimaryKey(ps, 1, bean); 
			result = ps.executeUpdate();
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doDelete could not execute update." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}

		return result ;
	}
	
	/**
	 * Checks if the given bean exists in the database (SQL SELECT COUNT(*) )
	 * @param bean
	 * @return True if the bean exist in the database
	 */
	protected boolean doExists(T bean) throws SQLException {
		long result = 0 ;
 
		try {
			conn = getConnection();
			ps = conn.prepareStatement( getSqlCount() );
			setValuesForPrimaryKey(ps, 1, bean); 
			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getLong(1);
			}
			
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doExists could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}

		return result > 0 ;
	}
	
	/**
	 * Counts all the occurrences in the table ( SQL SELECT COUNT(*) )
	 * @return
	 */
	protected long doCountAll() throws SQLException {
		long result = 0 ;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getSqlCountAll());	
			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getLong(1);
			}
		} 
        catch (SQLException e) {
			log.error("Database error. GenericDAO.doCountAll could not execute query." + e.getMessage(), e);
			throw e;
		} 
        finally {
			closeAll();
		}

		return result;
	}
	
	protected void setValue(PreparedStatement ps, int i, String value) throws SQLException {
		ps.setString(i, value);
	}
	
	protected void setValue(PreparedStatement ps, int i, java.util.Date value) throws SQLException {
		if ( value != null ) {
			ps.setDate(i, new java.sql.Date(value.getTime())); // Convert util.Date to sql.Date
		}
		else {
			ps.setNull(i, java.sql.Types.DATE); 
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, java.sql.Date value) throws SQLException {
		ps.setDate(i, value);
	}
	
	protected void setValue(PreparedStatement ps, int i, java.sql.Time value) throws SQLException {
		ps.setTime(i, value);
	}
	
	protected void setValue(PreparedStatement ps, int i, java.sql.Timestamp value) throws SQLException {
		ps.setTimestamp(i, value);
	}
	
	protected void setValue(PreparedStatement ps, int i, Byte value) throws SQLException {
		if ( value != null ) {
			ps.setByte(i, value.byteValue());
		}
		else {
			ps.setNull(i, java.sql.Types.TINYINT); // JDBC : "TINYINT" => getByte/setByte
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, Boolean value) throws SQLException {
		if ( value != null ) {
			ps.setBoolean(i, value.booleanValue());
		}
		else {
			ps.setNull(i, java.sql.Types.BIT); // JDBC : "BIT" => getBoolean/setBoolean
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, Short value) throws SQLException {
		if ( value != null ) {
			ps.setShort(i, value.shortValue());
		}
		else {
			ps.setNull(i, java.sql.Types.SMALLINT);
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, Integer value) throws SQLException {
		if ( value != null ) {
			ps.setInt(i, value.intValue());
		}
		else {
			ps.setNull(i, java.sql.Types.INTEGER);
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, Long value) throws SQLException {
		if ( value != null ) {
			ps.setLong(i, value.longValue());
		}
		else {
			ps.setNull(i, java.sql.Types.BIGINT); // JDBC : "BIGINT" => getLong/setLong
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, Float value) throws SQLException {
		if ( value != null ) {
			ps.setFloat(i, value.floatValue());
		}
		else {
			ps.setNull(i, java.sql.Types.FLOAT); 
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, Double value) throws SQLException {
		if ( value != null ) {
			ps.setDouble(i, value.doubleValue());
		}
		else {
			ps.setNull(i, java.sql.Types.DOUBLE); 
		}
	}
	
	protected void setValue(PreparedStatement ps, int i, BigDecimal value) throws SQLException {
		ps.setBigDecimal(i, value );
	}
	
	protected void setValue(PreparedStatement ps, int i, byte[] value) throws SQLException {
		ps.setBytes(i, value );
	}
}


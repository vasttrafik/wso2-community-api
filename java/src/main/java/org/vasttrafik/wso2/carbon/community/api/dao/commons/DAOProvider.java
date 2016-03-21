/*
 * Created on 23 dec 2015 ( Time 08:53:38 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.vasttrafik.wso2.carbon.community.api.dao.commons;

/**
 * DAO instance provider
 * 
 * @author Lars Andersson
 *
 */
public class DAOProvider {

	/**
	 * Returns a DAO instance implementing the given interface
	 * @param interfaceClass
	 * @return
	 */
	public final static <T> T getDAO(Class<T> interfaceClass) {
		String daoClassName = buildDAOClassName(interfaceClass) ;
		
		try {
			Class<?> daoClass = DAOProvider.class.getClassLoader().loadClass(daoClassName);
			return createDAOInstance(interfaceClass, daoClass) ;
		} 
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot load class " + daoClassName, e );
		}
	}
	
	/**
	 * Builds the DAO class name from the given interface class name
	 * @param interfaceClass
	 * @return
	 */
	private final static String buildDAOClassName(Class<?> interfaceClass) {
		String interfaceSimpleName = interfaceClass.getSimpleName();
		
		//--- DAO implementation simple name should end with Impl
		String implemSimpleName = interfaceSimpleName + "Impl"; 
		
		//--- DAO implementation full name (with package)
		String interfacePackageName = interfaceClass.getPackage().getName();
		
		return interfacePackageName + ".impl.jdbc." + implemSimpleName ;
	}
	
	/**
	 * Creates a DAO instance using the given DAO class
	 * @param interfaceClass
	 * @param daoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private final static <T> T createDAOInstance(Class<T> interfaceClass, Class<?> daoClass) {
		Object daoInstance = null ;
		
		try {
			daoInstance = daoClass.newInstance();
		} 
		catch (InstantiationException e) {
			throw new RuntimeException("Cannot create instance for class " + daoClass.getCanonicalName(), e );
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot create instance for class " + daoClass.getCanonicalName(), e );
		}
		
		if ( interfaceClass.isInstance(daoInstance) ) {
			return (T) daoInstance ;
		}
		else {
			throw new RuntimeException("Class " + daoClass.getCanonicalName() + " does not implement " + interfaceClass.getSimpleName() );
		}
	}
}

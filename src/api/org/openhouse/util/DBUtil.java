/**
 * 
 */
package org.openhouse.util;

import org.openhouse.api.context.Context;

/**
 * Utility class to aid in database related functionality
 * like ascertain the DB, building dynamic <code>SQL statements</code>.
 * and run the statement on the back end.
 * 
 * <p>
 * Caution should always be taken to first ascertain the back end 
 * before calling utility methods on this <code>Class</code> as we do not guarantee 
 * that the <code>SQL</code> will run on all back ends appropriately without failing.
 * </p>
 * 
 * <p> 
 * The disparity in the implementation of <code>SQL</code> 
 * among different DBMS brought this and we do not generic way to run the same statements across all DBMS.  
 * </p>
 * 
 * <p>
 * You have been warned!
 * </p>
 * 
 * @author Samuel Mbugua
 *
 */
public class DBUtil {
	
	/**
	 * private constructor to prevent initialization of this class.
	 */
	private DBUtil() {}
	
	/**
	 * Ascertains if the current back end is MySQL.
	 * 
	 * @return <code>true if Derby</code> otherwise <code>false</code>
	 */
	static boolean ascertainMySQLDb() {
		String driver = Context.getRuntimeProperties().getProperty("hibernate.connection.driver_class");
		if(driver == null || driver.length() <= 0)
			driver = Context.getRuntimeProperties().getProperty("hibernate.connection.driver_class");
		
		return driver.contains("mysql");
	}

	public static String addIntegerFilter(String filter, String fieldName, Integer value){
		if(value != null){
			if(filter == null)
				filter = " where " + fieldName;
			else
				filter += " and " + fieldName;

			filter += " = " + value;
		}

		return filter;
	}

	/**
	 * Constructs the path to acquire the correct
	 * liquibase files to execute in relation to the back end the application is running on.
	 * 
	 * @return <code>String</code> path to where the correct files are.
	 */
	public static String constructLiquibaseFilePath() {
			
		return "";
	}

}

package org.openhouse.api.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import liquibase.exception.LiquibaseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.context.Context;
import org.openhouse.util.DBUtil;
import org.openhouse.util.OpenhouseUtil;

/**
 * Handles creation and updating of database changes.
 * 
 * @author Samuel Mbugua
 *
 */
public class DatabaseManager {

	/** The logger. */
	private static Log log = LogFactory.getLog(DatabaseManager.class);

    private static List<String> buildInitChangeLogFiles() {

		/** Ascertain correct path(location) for Liquibase Change log Files */
		final String path = DBUtil.constructLiquibaseFilePath();

		final String changelogfileSchema = "liquibase-create-database.xml";
		final String changelogfileData = "liquibase-insert-core-data.xml";

		final List<String> files = new Vector<String>();

		// Files should be added in order of execution to make sure changes are
		// consistent.
		files.add(path + changelogfileSchema);
		files.add(path + changelogfileData);

		return files;
	}
	
	private static List<String> buildUpdateChangeLogFiles() {
		
		/** Ascertain correct path(location) for Liquibase Change log Files */
		final String path = DBUtil.constructLiquibaseFilePath();

		/** Change set file for General Latest Code Changes to be applied to the database */
		final String changelog_Update_to_latest = "liquibase-update-to-latest.xml";

		final List<String> files = new Vector<String>();

		// Files should be added in order of execution to make sure changes are
		// consistent.
		files.add(path + changelog_Update_to_latest);

		return files;
	}

	/**
	 * Creates a new database.
	 */
	private static void createDatabase() throws Exception {
		final Connection con = getConnection(false);
		final String databasename = getProperties().getProperty("hibernate.connection.createdb.databasename");

		log.debug(".................... Attempting to create database; " + databasename + " ....................");
		con.createStatement().execute("create database " + databasename);
	}

	/**
	 * Executes all liquibase database updates.
	 */
	private static void executeLiquibaseUpdates() throws Exception {
		final Connection connection = getConnection();
		try {

			final List<String> changeLogFiles = buildUpdateChangeLogFiles();

			for (final String changeLogFile : changeLogFiles) {
				LiquibaseManager.executeChangelog(connection, changeLogFile);
			}
		} catch (final LiquibaseException ex) {
			log.error("Liquibase Exception occurred during the running of changesets in : "
							+ DatabaseManager.class
							+ "Error Message >>> :"
							+ ex.getLocalizedMessage());
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Executes all liquibase database creation and adding initial data .
	 */
	private static void executeLiquibaseInitial() throws Exception {
		final Connection connection = getConnection();
		try {

			final List<String> changeLogFiles = buildInitChangeLogFiles();

			for (final String changeLogFile : changeLogFiles) {
				LiquibaseManager.executeChangelog(connection, changeLogFile);
			}
		} catch (final LiquibaseException ex) {
			log.error("Liquibase Exception occurred during the running of changesets in : "
							+ DatabaseManager.class
							+ "Error Message >>> :"
							+ ex.getLocalizedMessage());
		} finally {
			connection.close();
		}
	}

	/**
	 * Gets a database connection
	 */
	private static Connection getConnection() throws Exception {
		return getConnection(true);
	}

	/**
	 * Gets a connection to the database engine.
	 */
	private static Connection getConnection(final boolean withDatabase)	throws Exception {
		
		final Properties props = getProperties();
		String url = props.getProperty("hibernate.connection.url");
		final String username = props.getProperty("hibernate.connection.username");
		final String password = props.getProperty("hibernate.connection.password");
		final String driver = props.getProperty("hibernate.connection.driver_class");

		if (!withDatabase) {
			url = props.getProperty("hibernate.connection.createdb.url");
		}

		Class.forName(driver);

		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Updates the database to match with the current code.
	 */
	public static void updateDatabase() throws Exception {
		try {
			executeLiquibaseUpdates();
		} catch (final SQLException ex) {
			createDatabase();
			executeLiquibaseInitial();
			executeLiquibaseUpdates();
		}
	}
	

	private static void mergeDefaultRuntimeProperties(Properties runtimeProperties) {
		
		// loop over runtime properties and precede each with "hibernate" if
		// it isn't already
		Set<Object> runtimePropertyKeys = new HashSet<Object>();
		runtimePropertyKeys.addAll(runtimeProperties.keySet()); // must do it this way to prevent concurrent mod errors
		for (Object key : runtimePropertyKeys) {
			String prop = (String) key;
			String value = (String) runtimeProperties.get(key);
			log.trace("Setting property: " + prop + ":" + value);
			if (!prop.startsWith("hibernate") && !runtimeProperties.containsKey("hibernate." + prop)) 
				runtimeProperties.setProperty("hibernate." + prop, value);
		}

		// load in the default hibernate properties from hibernate.default.properties
		InputStream propertyStream = null;
		try {
			Properties props = new Properties();
			propertyStream = DatabaseManager.class.getClassLoader().getResourceAsStream("hibernate.default.properties");
			OpenhouseUtil.loadProperties(props, propertyStream);
			// add in all default properties that don't exist in the runtime 
			// properties yet
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				if (!runtimeProperties.containsKey(entry.getKey()))
					runtimeProperties.put(entry.getKey(), entry.getValue());
			}
		}
		finally {
			try {
				propertyStream.close();
			}
			catch (Throwable t) {
				// pass 
			}
		}
	}
	
	private static Properties getProperties() {
		Properties props = Context.getRuntimeProperties();
		mergeDefaultRuntimeProperties(props);
		return props;
    }
}

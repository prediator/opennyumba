package org.openhouse.api.database;

import java.sql.Connection;

import liquibase.ClassLoaderFileOpener;
import liquibase.CompositeFileOpener;
import liquibase.FileOpener;
import liquibase.FileSystemFileOpener;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;

import org.apache.log4j.Logger;

/**
 * Manages liquibase based database creation and changes.
 * 
 * @author Samuel Mbugua
 *
 */
public class LiquibaseManager {
	
	/** The logger. */
	private static final Logger log = Logger.getLogger(LiquibaseManager.class);
	
	/**
	 * Uses Liquibase to execute a set of changes in the specified file
	 * 
	 * @param changeLogFile the file to execute
	 * @throws Exception
	 */
	public static void executeChangelog(Connection connection,String changeLogFile) throws Exception {
		Liquibase liquibase = getLiquibase(connection,changeLogFile);
		try {
    		liquibase.forceReleaseLocks();
    		liquibase.update(null);
		}
        catch (LiquibaseException e) {
            log.error(LiquibaseManager.class + ">>>" + e.getLocalizedMessage());
        }

	}
	
	/**
	 * Get a connection to the database through Liquibase. The calling method /must/ close the
	 * database connection when finished with this Liquibase object.
	 * liquibase.getDatabase().getConnection().close()
	 * 
	 * @return Liquibase object based on the current connection settings
	 * @throws Exception
	 */
	private static Liquibase getLiquibase(Connection connection, String changeLogFile) throws Exception {
		try {
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
			FileOpener clFO = new ClassLoaderFileOpener();
			FileOpener fsFO = new FileSystemFileOpener();
			
			return new Liquibase(changeLogFile, new CompositeFileOpener(clFO, fsFO), database);
		}
		catch (Exception e) {
			// if an error occurs, close the connection
			if (connection != null)
				connection.close();
			log.error(LiquibaseManager.class + ">>>" + e.getLocalizedMessage());
			
			throw e;
		}
	}
}

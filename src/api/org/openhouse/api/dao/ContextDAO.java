package org.openhouse.api.dao;

import java.util.Properties;

import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.springframework.transaction.annotation.Transactional;


/**
 * Provides data access services for the context object.
 * 
 * @author Samuel Mbugua
 *
 */
public interface ContextDAO {
	/**
	 * Authenticate user with the given username and password.
	 * 
	 * @param username the user name.
	 * @param password the password.
	 * @return the User object if any is found with the given name and password.
	 * @throws ContextAuthenticationException
	 */
	@Transactional(readOnly=true)
	public User authenticate(String username, String password) throws OpenHouseException;
	
	/**
	 * Open session.
	 *
	 */
	public void openSession();

	
	/**
	 * Close session.
	 */
	public void closeSession();

	/**
	 * Clear session.
	 */
	@Transactional
	public void clearSession();
	
	/**
	 * Used to clear a cached object out of a session in the middle of a unit of work.
	 * 
	 * Future updates to this object will not be saved.  Future gets of this object will
	 * not fetch this cached copy
	 * 
	 * @param obj The object to evict/remove from the session
	 * @see Context.evictFromSession(Object)
	 * 
	 */
	public void evictFromSession(Object obj);
	
	/**
	 * Starts OpenHouse
	 * Should be called prior to any kind of activity
	 * @param props
	 */
	@Transactional
	public void startup(Properties props);
	
	/**
	 * Stops OpenHouse
	 * Should be called after all activity has ended and application is closing
	 */
	@Transactional
	public void shutdown();
	
	/**
	 * Closes the database connection.
	 */
	public void closeDatabaseConnection();
}

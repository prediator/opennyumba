/**
 * 
 */
package org.openhouse.api.service;

import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.springframework.security.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service is used for managing 
 * authentication of users and provide logout services.
 * 
 * @author Samuel Mbugua
 *
 */
@Transactional
public interface AuthenticationService {
	
	/**
	 * Gets a user with a particular user name and password.
	 * 
	 * @param username the user name.
	 * @param password the password.
	 * @return the user if one exists, else null.
	 */
	@Transactional(readOnly=true)
	User authenticate(String username, String password) throws OpenHouseException;
	
	/**
	 * Logs out out the currently logged in user
	 * 
	 * Invalidates the spring security context
	 * @throws AccessDeniedException Any exception that will be thrown will be wrapped around
	 * this one.
	 */
	@Transactional(readOnly=true)
	void logout()throws OpenHouseException;
}

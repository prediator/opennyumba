/**
 * 
 */
package org.openhouse.api.dao;

import java.util.List;

import org.openhouse.api.database.model.Privilege;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;

/**
 * Provides data access 
 * services to the <code>User service</code>.
 * 
 * @author Samuel Mbugua
 *
 */
public interface UserDAO {
	
	/**
	 * Gets a list of users in the database.
	 * 
	 * @return the user list.
	 */
	public List<User> getAllUsers() throws OpenHouseException;
	
	/**
	 * Retrieves a user by their log in name
	 * 
	 * @param username String
	 * @return User, or null if no match found
	 */
	User getUser(String username) throws OpenHouseException;
	
	User getUser(Integer userId) throws OpenHouseException;
	
	/**
	 * Saves a user to the database.
	 * 
	 * @param user the user to save.
	 * @throws OpenHouseException 
	 */
	void saveUser(User user) throws OpenHouseException;
	
	/**
	 * Removes a user from the database.
	 * 
	 * @param user the user to remove.
	 * 
	 */
	void deleteUser(User user) throws OpenHouseException;

	List<Privilege> getAllPrivileges();
}

package org.openhouse.api.service;

import java.util.List;

import org.openhouse.api.database.model.Privilege;
import org.openhouse.api.database.model.Role;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service is used for 
 * managing <code>Users</code> and provide user related functionality.
 * 
 * @author Samuel Mbugua
 */
@Transactional
public interface UserService {

    /**
     * Gets a user based on their user name
     * @param username String login name
     * @return User, or null if no match found
     * @throws  
     */
    @Transactional(readOnly=true)
    public User getUser(String username) throws OpenHouseException;
    
    @Transactional(readOnly=true)
    public User getUser(Integer userId) throws OpenHouseException;
    
    /**
     * Finds a user based on their email
     * @param email String 
     * @return User, or null if no match found
     */
    @Transactional(readOnly=true)
    public User getUserByUsername(String username) throws OpenHouseException;    
    	
	/**
	 * Saves a new and modified users to the database.
	 * 
	 * @param user the user to save.
	 * @throws Exception
	 */
    public void saveUser(User user) throws OpenHouseException;
	
    /**
     * Resets the user's password and saves the user
     * @param user
     * @param size
     * @throws Exception
     */
	public void resetPassword(User user, int size) throws OpenHouseException;
    
	/**
     * Gets a list of all users in the database.
     * 
     * @return the user list.
     */
	@Transactional(readOnly=true)
	public List<User> getAllUsers() throws OpenHouseException;
	
	/**
	 * Removes a user from the database.
	 * 
	 * @param user the user to remove.
	 * @throws OpenHouseException 
	 */
	public void deleteUser(User user) throws OpenHouseException;
	public User getLoggedInUser() throws OpenHouseException;
	public boolean validatePassword(User user) throws OpenHouseException;
	public User authenticate(String username, String password) throws OpenHouseException;
	
	public List<Privilege> getAllPrivileges();

	public List<Role> getAllRoles();

	public void savePrivilege(Privilege p);

	public void saveRole(Role role);
}
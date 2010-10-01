/**
 * 
 */
package org.openhouse.api.service.impl;

import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.service.AuthenticationService;

/**
 * Default implementation for <code>Authentication Service</code>.
 * 
 * @author Samuel Mbugua
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {
	
	public AuthenticationServiceImpl() {}
	
	public User authenticate(String username, String password) throws OpenHouseException{
		return Context.authenticate(username, password);
	}
	
	public void logout() throws OpenHouseException {
		Context.logOut();
	}
}

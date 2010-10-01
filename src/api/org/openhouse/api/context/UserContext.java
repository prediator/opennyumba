package org.openhouse.api.context;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openhouse.api.dao.ContextDAO;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.util.LocaleUtility;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;


/**
 * Stores the currently logged on <code>User</code>.
 * The <code>User</code> can be accessed on the browser.
 *
 * @author Samuel Mbugua
 *
 */
@Transactional
public class UserContext {

	/**
	 * Logger - shared by entire class
	 */
	//private static final Log log = LogFactory.getLog(UserContext.class);
	private static final Logger log = Logger.getLogger(UserContext.class);

	/**
	 * User object containing details about the authenticated user 
	 */
	private User user = null;
	
	private Locale locale;
	
	/**
	 * Default public constructor
	 */
	public UserContext() { }
	
	/**
	 * Retrieves this users authentication from the spring 
	 * security context for internal use.
	 * 
	 * @return spring security specific authentication object.
	 */
	private Authentication getAuthentication() {
		
		Authentication auth = null;
		SecurityContext ctx = SecurityContextHolder.getContext();
		if(ctx != null)
			auth = ctx.getAuthentication();
		
		return auth;
	}

	/**
	 * Authenticate the user to this UserContext.
	 * 
	 * @param username String login name
	 * @param password String login password
	 * @param contextDAO implementation to use for authentication
	 * @return User that has been authenticated
	 * @throws OpenHouseException 
	 */
	public User authenticate(String username, String password, ContextDAO contextDAO) throws OpenHouseException {
		if (log.isDebugEnabled())
			log.debug("Authenticating with username: " + username);
		
		this.user = contextDAO.authenticate(username, password);
		
		if (log.isDebugEnabled())
			log.debug("Authenticated as: " + this.user);
		
		return this.user;
	}
	
	/**
	 * Sets the authenticated user in the security context
	 * and also authenticates the user spring security context.
	 * 
	 * @param user user to set in the contexts.
	 */
	public void setAuthenticatedUser(User user){
		Authentication auth = getAuthentication();
		if(auth != null) {
			if(auth.isAuthenticated())
				this.user = user;
		}
	}
	
	/**
	 * Returns the authenticated user both in the user context
	 * authenticate user and the spring security context.
	 * 
	 * @return "active" user who has been authenticated, otherwise
	 *         <code>null</code>
	 */
	public User getAuthenticatedUser() {		
		Authentication auth = getAuthentication();
		if(auth != null) {
			if(auth.isAuthenticated()) {
				return this.user;
			}
		}
		return null;
	}

	/**
	 * @return true if user has been authenticated in this UserContext
	 * and the spring security context.
	 */
	public boolean isAuthenticated() {
		Authentication auth = getAuthentication();
		if(auth != null) {
			if(auth.isAuthenticated() && user != null)
				return true;
		}
		return false;
	}

	/**
	 * logs out the "active" (authenticated) user within this UserContext
	 * 
	 * @see #authenticate
	 */
	public void logout() {		

		log.debug("Clearing the spring security context");		
		Authentication auth = getAuthentication();
		if(auth != null) {
			auth.setAuthenticated(false);
		}
		
		if(SecurityContextHolder.getContext() != null)
			SecurityContextHolder.clearContext();
		
		log.debug("Successfully logged out on " + new Date());
		user = null;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * @return current locale for this context
	 */
	public Locale getLocale() {
		if (locale == null)
			locale = LocaleUtility.getDefaultLocale();
		
		return locale;
	}
}

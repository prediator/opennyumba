package org.openhouse.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.dao.ContextDAO;
import org.openhouse.api.dao.UserDAO;
import org.openhouse.api.database.model.Privilege;
import org.openhouse.api.database.model.Role;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.exception.OpenHouseSessionExpiredException;
import org.openhouse.api.security.OpenHouseUserDetails;
import org.openhouse.api.service.UserService;
import org.openhouse.util.SecurityUtil;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Default implementation for <code>UserService interface</code>.
 * 
 * @author Samuel Mbugua
 * 
 */
public class UserServiceImpl implements UserService {
    
    /** The logger. */
    private Log log = LogFactory.getLog(this.getClass());

    /** The data access object. */
    private UserDAO dao;
    
    private ContextDAO contextDao;

	public List<User> getAllUsers() throws OpenHouseException {		
		return dao.getAllUsers();
	}
	
    public User getLoggedInUser() throws OpenHouseException{
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if (auth != null) {
                if (auth.getPrincipal() instanceof OpenHouseUserDetails) {
                    OpenHouseUserDetails userDetails = (OpenHouseUserDetails)auth.getPrincipal();
                    User user = userDetails.getOpenHouseUser();
                    return getUser(user.getUsername()); // ensure that the latest copy is loaded
                } else {
                    log.debug("Auth not an instance of OpenHouseUserDetails - i.e. no logged in user. Auth="+auth);
                    throw new OpenHouseSessionExpiredException("Could not find logged in user");
                }
            }
        }
        log.debug("No Spring SecurityContext or Authentication - i.e. no logged in user");
        throw new OpenHouseSessionExpiredException("Could not find logged in user");
    }

    public User getUser(String username) throws OpenHouseException  {
        return dao.getUser(username);
    }
    
    public User getUser(Integer userId) throws OpenHouseException {
		return dao.getUser(userId);
	}

	public void saveUser(User user) throws OpenHouseException  {
		user.setSalt(SecurityUtil.getRandomToken());
		user.setPassword(SecurityUtil.encodeString(user.getClearTextPassword()+user.getSalt()));
		dao.saveUser(user);
	}
	
	public boolean validatePassword(User user) throws OpenHouseException {
	    User authUser = contextDao.authenticate(user.getUsername(), user.getPassword());
	    if (authUser != null) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
    public User authenticate(String username, String password) throws OpenHouseException {
        return contextDao.authenticate(username, password);
    }   

	public void resetPassword(User user, int size) throws OpenHouseException {
    	log.debug("UserServiceImpl resetPassword");
    	String password = UUID.randomUUID().toString();
    	password = password.replace("-", "").substring(0, size);
    	user.setClearTextPassword(password);
    	saveUser(user);
	}
    
	public void deleteUser(User user) throws OpenHouseException {
		dao.deleteUser(user);
	}
	
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * Sets the context dao
     * @param contextDao
     */
    public void setContextDao(ContextDAO contextDao) {
        this.contextDao = contextDao;
    }

	@Override
	public User getUserByUsername(String username) throws OpenHouseException {
		return null;
	}

	@Override
	public List<Privilege> getAllPrivileges() {
		return dao.getAllPrivileges();
	}

	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return dao.getAllRoles();
	}

	@Override
	public void savePrivilege(Privilege p) {
		dao.savePrivilege(p);
		
	}

	@Override
	public void saveRole(Role role) {
		dao.saveRole(role);
		
	}

}
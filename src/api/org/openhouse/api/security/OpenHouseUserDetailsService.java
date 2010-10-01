package org.openhouse.api.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.Privilege;
import org.openhouse.api.database.model.Role;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * OpenHouse UserDetailsService - used by spring security to 
 * retrieve user details and authenticate.
 * 
 * Note: the StudyManagerService.authenticate method is bypassed
 * because Spring Security implements "remember me" functionality
 * that requires the retrieval of user details without a password.
 * 
 * @author Samuel Mbugua
 *
 */
public class OpenHouseUserDetailsService implements UserDetailsService {
    
    private Logger log = Logger.getLogger(this.getClass());
    
    private UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	
        OpenHouseUserDetails userDetails = null;
        if(this.userService == null)
        	userService = Context.getUserService();
        
        User user = null;
		try {
			user = userService.getUser(username);
		} catch (OpenHouseException e) {
			log.error("Error occurred in " + this.getClass() + ".loadUserByUsername(String username)" + " >>> " + e.getLocalizedMessage());
		}
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();            	
        	if(user.isSuperUser()){
        		List<Privilege> privileges = null;            		
				privileges = Context.getUserService().getAllPrivileges();
				
            	if(privileges != null && privileges.size() > 0){
                	for(Privilege p : privileges){
                		GrantedAuthority ga = new GrantedAuthorityImpl(p.getPrivilege());
//                        log.debug("User: "+user.getName()+", GrantedAuthority: "+p.getName());
                        authorities.add(ga);
                	}
            	}
        	}
        	else{
                for (Role r : user.getRoles()) {
                	Set<Privilege> privileges = r.getPrivileges();
                    for (Privilege p : privileges) {
                        GrantedAuthority ga = new GrantedAuthorityImpl(p.getPrivilege());
                        log.debug("User: "+user.getUsername()+", GrantedAuthority: "+p.getPrivilege());
                        authorities.add(ga);
                    }
                }
        	}
            userDetails = new OpenHouseUserDetails(user,true, true, true, true,authorities.toArray(new GrantedAuthority[authorities.size()]));
        }
        return userDetails;
    }

	public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
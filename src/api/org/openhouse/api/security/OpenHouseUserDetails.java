package org.openhouse.api.security;

import org.openhouse.api.database.model.User;
import org.springframework.security.GrantedAuthority;

public class OpenHouseUserDetails extends org.springframework.security.userdetails.User {
    
    private static final long serialVersionUID = 2323963197126198664L;

    private User openHouseUser;

    public OpenHouseUserDetails(User openhouseUser,
            boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            GrantedAuthority[] authorities) throws IllegalArgumentException {
    	
        super(openhouseUser.getUsername(), openhouseUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.openHouseUser = openhouseUser;
    }

    public String getSalt() {
        return openHouseUser.getSalt();
    }
    
    public User getOpenHouseUser() {
        return openHouseUser;
    }
}

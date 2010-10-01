package org.openhouse.api.security;

import org.openhouse.util.SecurityUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;

/**
 * Converts the OpenHouse SecurityUtil so it can be
 * used in Spring Security
 * 
 * @author Samuel Mbugua
 *
 */
public class OpenHousePasswordEncoder implements PasswordEncoder {

    public String encodePassword(String password, Object salt)
            throws DataAccessException {
        return SecurityUtil.encodeString(password + salt);
    }

    public boolean isPasswordValid(String encodedPassword, String rawPassword, Object salt)
            throws DataAccessException {
        String encoded = encodePassword(rawPassword, salt);
        if (encoded.equals(encodedPassword)) {
            return true;
        } else 			
            return false;
    }
}
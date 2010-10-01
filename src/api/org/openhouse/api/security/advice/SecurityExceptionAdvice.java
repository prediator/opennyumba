package org.openhouse.api.security.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.exception.OpenHouseSecurityException;
import org.openhouse.api.exception.OpenHouseSessionExpiredException;
import org.openhouse.api.security.OpenHouseUserDetails;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Advice for capturing any <code>Exceptions</code> 
 * that can potentially occur on the <code>Service layer.</code>
 * 
 * <p>
 * Using <code>AOP</code>, we re-throw the 
 * meaningful <code>exception</code> to the client 
 * to avoid <code>"Call failed on Server" Exception</code>.
 * </p>
 * <p>Refer to the <code>applicationContext-security.xml</code>.</p>  
 * 
 * @author Samuel Mbugua
 *
 */
@Aspect
public class SecurityExceptionAdvice implements ThrowsAdvice {
    
    /** The logger. */
    private Log log = LogFactory.getLog(this.getClass());
	
	public void afterThrowing(Exception exception) throws OpenHouseException {	
		if (exception instanceof AccessDeniedException ) {
	        if (isUserLoggedIn()) {

	        	//TODO This message should be internationalized. 
	        	String exMsg = "Access to restricted operation is denied";
	        	
	    		// log the error on the server so it is not lost
	    		log.error("Caught serverside Access Denied exception, throwing new exception to the client '"+ 
	    				exception.getMessage() +"'", exception);
	    		
	    		throw new OpenHouseSecurityException(exMsg);
	            
	        } else {
	        	
	        	//TODO This message should be internationalized.	        	
	        	String exMsg = "Your session has expired. Re-Login to proceed.";
	        	
	    	    // log the error on the server so it is not lost
	    	    log.error("Caught serverside Session Expired exception, throwing new exception to the client '"+ 
	    	    		exception.getMessage()+"'", exception);
	    	    
	            throw new OpenHouseSessionExpiredException(exMsg);
	        }
	    }
	}
	
	/**
	 * Checks if the <code>User</code> is still 
	 * registered in the <code>Spring security context.</code>
	 * 
	 * @return 
	 * 		<code>true</code> 
	 * <code>if(auth.getPrincipal() instanceof OpenHouseUserDetails)</code>
	 * <p>
	 * else
	 * 	<code>false</code>
	 * </p>
	 */
	private boolean isUserLoggedIn() {
	 // see if the authentication object is intact.
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if (auth != null) {
                if (auth instanceof OpenHouseUserDetails) {
                    return true;
                }
                else if(auth.getPrincipal() != null) {
                	if(auth.getPrincipal() instanceof OpenHouseUserDetails) {
                		return true;
                	}
                }
            }
        }
        return false;
	}
}

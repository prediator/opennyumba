package org.openhouse.api.security.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.openhouse.api.exception.OpenHouseDisabledUserException;
import org.openhouse.api.exception.OpenHouseException;
import org.springframework.aop.ThrowsAdvice;

/**
 * Advice for capturing any 
 * <code>User related Exceptions</code> that can potentially occur on the 
 * <code>Service layer.</code> like disabled <code>Users</code> trying to log in.
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
public class UserExceptionAdvice implements ThrowsAdvice {
	
    /** The logger. */
    private Log log = LogFactory.getLog(this.getClass());
	
    /**
     * Re throws a known <code>exception</code>
     * to the client. 
     * <p>
     * This is achieved using 
     * <code>AOP</code> in the <code>applicationContext-security.xml</code>.
     * </p>
     * 
     * @param exception <code>User exception</code> thrown on the service layer. 
     */
	public void afterThrowing(Exception exception) throws OpenHouseException{
		if(exception instanceof OpenHouseDisabledUserException){
        	//TODO This message should be internationalized. 
        	String exMsg = "Your account has been disabled! Contact the System Administrator.";
        	
    		// log the error on the server so it is not lost
    		log.error("Caught serverside Access Denied exception, throwing new exception to the client '"+ 
    				exception.getMessage() +"'", exception);
    		
    		throw new OpenHouseDisabledUserException(exMsg);
		}
	}
}

package org.openhouse.api.exception;

/**
 * Models a custom <code>Exception</code>
 * for a disabled <code>User</code> who tries to login.
 * 
 * @author Samuel Mbugua
 *
 */
public class OpenHouseDisabledUserException extends OpenHouseSecurityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6493905445906677952L;
	
	public OpenHouseDisabledUserException(){}
	
	/**
	 * Constructor that takes an argument which is an
	 * instance any unregistered security
	 * exception that might be thrown on the server.
	 * 
	 * @param throwable the unregistered <code>exception</code> thrown on the server.
	 * Recommended exception is the spring security access denied exception.
	 */
	public OpenHouseDisabledUserException(Throwable throwable) {
		super(throwable);
		
		throwable.getLocalizedMessage();
	}
	

	/**
	 * Constructor that takes an argument which is a 
	 * <code>String </code> message for the 
	 * instance of any exception that might be thrown on the server.
	 * 
	 * @param message the message of the unregistered exception thrown on the server.
	 * Recommended exception is the spring security access denied exception.
	 */
	public OpenHouseDisabledUserException(String message){
		super(message);
	}

}

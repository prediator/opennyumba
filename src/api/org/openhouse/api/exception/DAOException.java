package org.openhouse.api.exception;


/**
 * Represents often fatal errors that occur within the database layer.
 */
public class DAOException extends APIException {
	
	private static final long serialVersionUID = -185144340435149253L;
	
	public DAOException() {
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
	
}

package org.openhouse.api.exception;

import java.io.Serializable;


/**
 * General class to handle OpenHouse exceptions specifically service layer exceptions.
 * The idea is to enable serialization of custom <code>exceptions</code> to the client.
 *
 * This class wraps those exceptions and re throws them to the client as known exceptions with 
 * meaningful messages to the user.
 * 
 * @author Samuel Mbugua
 */
public class OpenHouseException  extends Exception implements Serializable   {

	/**
	 * Generated <code>serialisation</code> ID.
	 */
	private static final long serialVersionUID = 1348971901401835418L;
	
	/**
	 * Default non arg constructor.
	 */
	public OpenHouseException(){}
	
	/**
	 * Constructor that takes an argument 
	 * which is the <code>String</code> 
	 * message to display when the exception is thrown.
	 * 
	 * @param message message to display when <code>exception</code> is thrown.
	 */
	public OpenHouseException(String message){
		super(message);
	}

	/**
	 * Constructor that takes an argument 
	 * which is an instance of <code>throwable</code>. 
	 * It is the exception that is thrown used to initialise this exception too.
	 * 
	 * @param throwable exception that is thrown.
	 */
	public OpenHouseException(Throwable throwable) {
	    super(throwable);
	}
}

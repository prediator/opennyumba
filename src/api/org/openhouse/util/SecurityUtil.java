package org.openhouse.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.exception.APIException;


/**
 * This class has utilities used during the generation of 
 * a SHA-1 hash of a user's password.
 * 
 * @author Samuel Mbugua
 *
 */
public class SecurityUtil {

	public static Log log = LogFactory.getLog(SecurityUtil.class);
	
	public static boolean hashMatches(String hashedPassword, String passwordToHash) {
		if (hashedPassword == null || passwordToHash == null)
			throw new APIException("Neither the hashed password or the password to hash cannot be null");
		
		return hashedPassword.equals(encodeString(passwordToHash))
		        || hashedPassword.equals(encodeStringSHA1(passwordToHash))
		        || hashedPassword.equals(incorrectlyEncodeString(passwordToHash));
	}

	/**
	 * This method will hash <code>strToEncode</code> using the preferred
	 * algorithm.  Currently, OpenHouse's preferred algorithm is hard coded
	 * to be SHA-1.
	 *  
	 * @param strToEncode string to encode
	 * @return the SHA-1 encryption of a given string
	 */
	public static String encodeString(String strToEncode) {
		try{
			String algorithm = "SHA1";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] input = strToEncode.getBytes(); //TODO: pick a specific character encoding, don't rely on the platform default
			return hexString(md.digest(input));
		}
		catch(NoSuchAlgorithmException ex){
			log.error(ex.getMessage(), ex);
		}

		return null;
	}
	
	
	private static String encodeStringSHA1(String strToEncode) throws APIException {
		String algorithm = "SHA1";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException e) {
			// Yikes! Can't encode password...what to do?
			log.error("Can't encode password because the given algorithm: " + algorithm + "was not found! (fail)", e);
			throw new APIException("System cannot find SHA1 encryption algorithm", e);
		}
		byte[] input = strToEncode.getBytes(); //TODO: pick a specific character encoding, don't rely on the platform default
		return hexString(md.digest(input));
	}
	
	private static String incorrectlyEncodeString(String strToEncode) throws APIException {
		String algorithm = "SHA1";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException e) {
			// Yikes! Can't encode password...what to do?
			log.error("Can't encode password because the given algorithm: " + algorithm + "was not found! (fail)", e);
			throw new APIException("System cannot find SHA1 encryption algorithm", e);
		}
		byte[] input = strToEncode.getBytes(); //TODO: pick a specific character encoding, don't rely on the platform default
		return incorrectHexString(md.digest(input));
	}

	/**
	 * Convenience method to convert a byte array to a string.
	 * This solves a bug in the above method.
	 * 
	 * @param b
	 * @return
	 */
	private static String hexString(byte[] b) {
		StringBuffer buf = new StringBuffer();
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = b.length;
		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			high = ((b[i] & 0xf0) >> 4);
			low = (b[i] & 0x0f);
			buf.append(hexChars[high]);
			buf.append(hexChars[low]);
		}
		
		return buf.toString();
	}
	
	private static String incorrectHexString(byte[] b) {
		if (b == null || b.length < 1)
			return "";
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			s.append(Integer.toHexString(b[i] & 0xFF));
		}
		return new String(s);
	}

	/**
	 * This method will generate a random string 
	 * 
	 * @return a secure random token.
	 */
	public static String getRandomToken() {
		Random rng = new Random();
		return encodeString(Long.toString(System.currentTimeMillis()) 
				+ Long.toString(rng.nextLong()));
	}
}

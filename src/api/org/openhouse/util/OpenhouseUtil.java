package org.openhouse.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 
 * OpenHouse utilities.
 * 
 * @author Samuel Mbugua
 */
public class OpenhouseUtil {

	private static Log log = LogFactory.getLog(OpenhouseUtil.class);	
	
	/**
	 * Gets the application data directory of openhouse for the logged on user.
	 * 
     * @return The path to the directory on the file system that will hold miscellaneous
     * 			data about the application (runtime properties, modules, etc)
     */
    public static String getApplicationDataDirectory() {
    	String filepath = null;
    	
    	if (OpenhouseConstants.APPLICATION_DATA_DIRECTORY != null) {
    		filepath = OpenhouseConstants.APPLICATION_DATA_DIRECTORY;
    	}
    	else {
	        if (OpenhouseConstants.OPERATING_SYSTEM_LINUX.equalsIgnoreCase(OpenhouseConstants.OPERATING_SYSTEM) || 
	        		OpenhouseConstants.OPERATING_SYSTEM_FREEBSD.equalsIgnoreCase(OpenhouseConstants.OPERATING_SYSTEM) || 
	        		OpenhouseConstants.OPERATING_SYSTEM_OSX.equalsIgnoreCase(OpenhouseConstants.OPERATING_SYSTEM))
				filepath = System.getProperty("user.home") + File.separator + ".OpenHouse";
			else
				filepath = System.getProperty("user.home") + File.separator + 
						"Application Data" + File.separator + 
						"OpenHouse";
					
			filepath = filepath + File.separator;
    	}
		
		File folder = new File(filepath);
		if (!folder.exists())
			folder.mkdirs();
		
		return filepath;
    }
    
    /**
     * Find the given folderName in the application data directory.  Or, treat
     * folderName like an absolute url to a directory 
     * 
     * @param folderName the folder name.
     * @return folder capable of storing information
     */
    public static File getDirectoryInApplicationDataDirectory(String folderName) throws Exception {
    	//  try to load the repository folder straight away.
		File folder = new File(folderName);
		
		// if the property wasn't a full path already, assume it was intended to be a folder in the 
		// application directory
		if (!folder.isAbsolute()) {
			folder = new File(getApplicationDataDirectory(), folderName);
		}
		
		// now create the directory folder if it doesn't exist
		if (!folder.exists()) {
			log.warn("'" + folder.getAbsolutePath() + "' doesn't exist.  Creating directories now.");
			folder.mkdirs();
		}
	
		if (!folder.isDirectory())
			throw new Exception("'" + folder.getAbsolutePath() + "' should be a directory but it is not");
	
		return folder;
    }
    
    public static void loadProperties(Properties props, InputStream input) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			while (reader.ready()) {
				String line = reader.readLine();
				if (line.length() > 0 && line.charAt(0) != '#') {
					int pos = line.indexOf("=");
					if (pos > 0) {
						String keyString = line.substring(0, pos);
						String valueString = line.substring(pos + 1);
						if (keyString != null && keyString.length() > 0) {
							props.put(keyString, fixPropertiesValueString(valueString));
						}
					}
				}
			}
			reader.close();
		}
		catch (UnsupportedEncodingException uee) {
			log.error("Unsupported encoding used in properties file " + uee);
		}
		catch (IOException ioe) {
			log.error("Unable to read properties from properties file " + ioe);
		}
	}
	
	private static String fixPropertiesValueString(String value) {
		String returnString = value.replace("\n", "");
		returnString = returnString.replace("\\:", ":");
		returnString = returnString.replace("\\=", "=");
		
		return returnString;
	}

	public static Map<String, String> parseParameterList(String paramList) {
		Map<String, String> ret = new HashMap<String, String>();
		if (paramList != null && paramList.length() > 0) {
			String[] args = paramList.split("\\|");
			for (String s : args) {
				int ind = s.indexOf('=');
				if (ind <= 0) {
					throw new IllegalArgumentException("Misformed argument in dynamic page specification string: '" + s
					        + "' is not 'key=value'.");
				}
				String name = s.substring(0, ind);
				String value = s.substring(ind + 1);
				ret.put(name, value);
			}
		}
		return ret;
	}
	
	public static void storeProperties(Properties properties, OutputStream outStream, String comment) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(outStream), "UTF-8");
			Writer out = new BufferedWriter(osw);
			if (comment != null)
				out.write("\n#" + comment + "\n");
			out.write("#" + new Date() + "\n");
			for (Map.Entry<Object, Object> e : properties.entrySet()) {
				out.write(e.getKey() + "=" + e.getValue() + "\n");
			}
			out.write("\n");
			out.flush();
			out.close();
		}
		catch (FileNotFoundException fnfe) {
			log.error("target file not found" + fnfe);
		}
		catch (UnsupportedEncodingException ex) { //pass
			log.error("unsupported encoding error hit" + ex);
		}
		catch (IOException ioex) {
			log.error("IO exception encountered trying to append to properties file" + ioex);
		}
		
	}
	
	public static void storeProperties(Properties properties, File file, String comment) {
		OutputStream outStream = null;
		try {
			outStream = new FileOutputStream(file, true);
			storeProperties(properties, outStream, comment);
		}
		catch (IOException ex) {
			log.error("Unable to create file " + file.getAbsolutePath() + " in storeProperties routine.");
		}
		finally {
			try {
				if (outStream != null)
					outStream.close();
			}
			catch (IOException ioe) {
				//pass
			}
		}
	}
    
}
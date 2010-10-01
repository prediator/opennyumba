package org.openhouse.util;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.User;

/**
 * Utilities for handling files.
 * 
 * @author Samuel Mbugua
 *
 */
public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);
	
	
	/**
     * Gets a unique file name in a given folder
     * 
     * If username is not provided, the username is not put into the filename.
     * 
     * Assumes dir is already created
     * 
     * @param dir directory to make the random filename in
     * @param extension the kind of extension for the file that is being picked from the directory
     * @param username optional User creating this file object 
     * @return file new file that is able to be written to
     */
    public static String getUniqueFileName(String dir,String extension, String username) {
    	
    	File outFile;
    	  	
		do {
	    	// format to print date in filenmae
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HHmm-ssSSS");
	    	
	    	StringBuilder filename = new StringBuilder();
	    
	    	// the start of the filename is the time so we can do some sorting
			filename.append(dateFormat.format(new Date()));
			
			// insert the user name if they provided it
			if (username != null) {
				filename.append("-");
				filename.append(username);
				filename.append("-");
			}
			
			// the end of the filename is a randome number between 0 and 10000
			filename.append((int)(Math.random() * 10000));
			filename.append("."+extension);
			
			outFile = new File(new File(dir), filename.toString());
			
		} while (outFile.exists());
		
		return outFile.getAbsolutePath();
    }
    
    /**
     * Replaces %Y in the string with the four digit year.
     * Replaces %M with the two digit month
     * Replaces %D with the two digit day
     * Replaces %w with week of the year
     * Replaces %W with week of the month
     * 
     * @param str String filename containing variables to replace with date strings 
     * @return String with variables replaced
     */
    public static String replaceVariables(String str) {
    	
    	Calendar calendar = Calendar.getInstance();
   		calendar.setTime(new Date());
    	
    	int year = calendar.get(Calendar.YEAR);
    	str = str.replace("%Y", Integer.toString(year));
    	
    	int month = calendar.get(Calendar.MONTH) + 1;
    	String monthString = Integer.toString(month);
    	if (month < 10)
    		monthString = "0" + monthString;
    	str = str.replace("%M", monthString);
    	
    	int day = calendar.get(Calendar.DATE);
    	String dayString = Integer.toString(day);
    	if (day < 10)
    		dayString = "0" + dayString;
		str = str.replace("%D", dayString);
    	
    	int week = calendar.get(Calendar.WEEK_OF_YEAR);
    	String weekString = Integer.toString(week);
    	if (week < 10)
    		weekString = "0" + week;
		str = str.replace("%w", weekString);
    	
    	int weekmonth = calendar.get(Calendar.WEEK_OF_MONTH);
    	String weekmonthString = Integer.toString(weekmonth);
    	if (weekmonth < 10)
    		weekmonthString = "0" + weekmonthString;
		str = str.replace("%W", weekmonthString);
    	
    	return str;
    }
    
    /**
     * Gets an out File object.  If date is not provided, the current 
     * timestamp is used.
     * 
     * If user is not provided, the user id is not put into the filename.
     * 
     * Assumes dir is already created
     * 
     * @param dir directory to make the random filename in
     * @param date optional Date object used for the name
     * @param user optional User creating this file object 
     * @return file new file that is able to be written to
     */
    public static File getOutFile(File dir, Date date, User user) {
    	
    	File outFile;
		do {
	    	// format to print date in filenmae
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HHmm-ssSSS");
	    	
	    	// use current date if none provided
	    	if (date == null)
	    		date = new Date();
	    	
	    	StringBuilder filename = new StringBuilder();
	    	
	    	// the start of the filename is the time so we can do some sorting
			filename.append(dateFormat.format(date));
			
			// insert the user id if they provided it
			if (user != null) {
				filename.append("-");
				filename.append(user.getUserId());
				filename.append("-");
			}
			
			// the end of the filename is a randome number between 0 and 10000
			filename.append((int)(Math.random() * 10000));
			filename.append(".xml");
			
			outFile = new File(dir, filename.toString());
			
			// set to null to avoid very minimal possiblity of an infinite loop
			date = null;
			
		} while (outFile.exists());
		
		return outFile;
    }
    
    /**
     * Writes form xml to a file in a particular folder.
     * 
     * @param xml the contents of the form to save.
     * @param folder the folder in which to create the file.
     * @return the full path and name of the file that has been written.
     */
    public static String saveForm(String xml,File folder){
		
    	String pathName = null;
    	
		try{
			pathName = getOutFile(folder, new Date(), Context.getAuthenticatedUser()).getAbsolutePath();
			
			FileWriter writter = new FileWriter(pathName, false);
			writter.write(xml);
			writter.close();
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
		}
		
		return pathName;
	}
	
    
    /**
     * Writes text data to a file.
     * 
     * @param data the text to write.
     * @param pathName the full path and name of the file to write to.
     */
    public static void writeToFile(String data, String pathName){
		try{
			FileWriter fw = new FileWriter(pathName);
			fw.write(data);
			fw.flush();
			fw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

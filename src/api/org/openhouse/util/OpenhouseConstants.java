package org.openhouse.util;

import java.util.HashMap;
import java.util.Map;


/**
 * This class holds constants used in the OpenHouse system.
 * 
 * @author Samuel Mbugua
 *
 */
public class OpenhouseConstants {
	
	private static final Package THIS_PACKAGE = OpenhouseConstants.class.getPackage();
	
	public static final String OPENHOUSE_VERSION = THIS_PACKAGE.getSpecificationVendor();
	
	public static final String OPENHOUSE_VERSION_SHORT = THIS_PACKAGE.getSpecificationVersion();
	
	public static final String GLOBAL_PROPERTY_DEFAULT_LOCALE = "default_locale";
	
	public static final String GLOBAL_PROPERTY_DEFAULT_LOCALE_DEFAULT_VALUE = "en_GB";

	/** Value representing a not yet set status. */
    public static final byte STATUS_NULL = -1;
    
    /** Value representing success of an action. */
    public static final byte STATUS_SUCCESS = 1;

    /** Value representing failure of an action. */
    public static final byte STATUS_FAILURE = 0;
    
	/** The text value for boolean true. */
	public static final String TRUE_TEXT_VALUE = "true";
	
	/** The text value for boolean false. */
	public static final String FALSE_TEXT_VALUE = "false";
	
	/** The batchEntry request parameter. */
	public static final String REQUEST_PARAM_BATCH_ENTRY = "batchEntry";
	
	/** The xformentry request parameter. */
	public static final String REQUEST_PARAM_XFORM_ENTRY = "xformentry";
	
	/** The content disposition http header. */
	public static final String HTTP_HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	
	/** The content type http header. */
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	
	/** The text/html http content type. */
	public static final String HTTP_HEADER_CONTENT_TYPE_TEXT_HTML = "text/html; charset=utf-8";
	
	/** The application/xhtml+xml http content type. */
	public static final String HTTP_HEADER_CONTENT_TYPE_XML = "text/xml; charset=utf-8"; //"application/xhtml+xml; charset=utf-8";
	
	/** The pdf http content type header. */
	public static final String HTTP_HEADER_CONTENT_TYPE_PDF = "application/pdf"; //"application/x-pdf";
	
	public static final String HTTP_HEADER_CONTENT_DISPOSITION_VALUE = "attachment; filename=\"";

	/** The name of the setting for the date format.*/
	public static final String SETTING_NAME_SUBMIT_DATE_FORMAT = "submitDateFormat";
	
	/** The name of the setting for the submission date format. */
	public static final String SETTING_NAME_SUBMIT_DATETIME_FORMAT = "submitDateTimeFormat";
	
	/** The name of the setting for the submission time format. */
	public static final String SETTING_NAME_SUBMIT_TIME_FORMAT = "submitTimeFormat";
	
	/** The name of the setting for the dislay date format. */
	public static final String SETTING_NAME_DISPLAY_DATE_FORMAT = "displayDateFormat";
	
	/** The name of the setting for the displat date and time format. */
	public static final String SETTING_NAME_DISPLAY_DATETIME_FORMAT = "displayDateTimeFormat";
	
	/** The name of the setting for the display time format. */
	public static final String SETTING_NAME_DISPLAY_TIME_FORMAT = "displayTimeFormat";
	
	/** The name of the setting for the user serializer class.*/
	public static final String SETTING_NAME_USER_SERIALIZER= "userSerializer";
	
	/** The name of the setting for the user serializer class.*/
	public static final String SETTING_NAME_STUDY_SERIALIZER= "studySerializer";
		
	/** The name of the form id attribute. */
	public static final String ATTRIBUTE_NAME_FORMID = "id";
	
	/** The name of the description template attribute. */
	public static final String ATTRIBUTE_NAME_DESCRIPTION_TEMPLATE = "description-template";
	
	/** The action request parameter. */
	public static final String REQUEST_PARAMETER_ACTION = "action";
	
	/** The http request action to download studies. */
	public static final String ACTION_DOWNLOAD_STUDIES = "downloadstudies";
	
	/** The http request action to download forms. */
	public static final String REQUEST_ACTION_DOWNLOAD_FORMS = "downloadforms";
	
	/** The http request action to download users. */
	public static final String REQUEST_ACTION_DOWNLOAD_USERS = "downloadusers";
	
	/** The http request action to uploda data. */
	public static final String REQUEST_ACTION_UPLOAD_DATA = "uploaddata";
	
	/** The http request paramer for user name. */
	public static final String REQUEST_PARAM_USERNAME = "uname";
	
	/** The http request parameter for user password. */
	public static final String REQUEST_PARAM_PASSWORD = "pw";
	
	/** The http request parameter for the name of the setting used for serialization of forms. */
	public static final String REQUEST_PARAM_FORM_SERIALIZER = "formser";
	
	/** The http request parameter for the name of the setting used for serialization of studies. */
	public static final String REQUEST_PARAM_STUDY_SERIALIZER = "studyser";
	
	/** The http request parameter for the name of the setting used for serialization of users. */
	public static final String REQUEST_PARAM_USER_SERIALIZER = "userser";
	
	/** The http request parameter for the locale. */
	public static final String REQUEST_PARAM_LOCALE = "locale";
	
	/** The name of the setting that has a value of the folder in which forms with errors are saved. */
	public static final String SETTING_NAME_FORM_ERROR_FOLDER = "formErrorFolder";
	
	/** The name of the setting that has a value of the folder in which a copy of forms are stored on submission. */
	public static final String SETTING_NAME_FORM_DATA_FOLDER = "formDataFolder";

	/** The name of the method for serializing studies. */
	public static final String SERIALIZER_METHOD_NAME_STUDY = "serializeStudies";
	
	/** The name of the method for serializing forms. */
	public static final String SERIALIZER_METHOD_NAME_FORM = "serializeForms";
	
	/** The name of the method for serializing users. */
	public static final String SERIALIZER_METHOD_NAME_USER = "serializeUsers";
	
	/** The name of the method for deserialzing form data. */
	public static final String DESERIALIZER_METHOD_NAME_FORMS = "deSerialize";
	
	
	public static String APPLICATION_DATA_DIRECTORY = null;
	
	public static String OPERATING_SYSTEM_KEY = "os.name";
	public static String OPERATING_SYSTEM = System.getProperty(OPERATING_SYSTEM_KEY);
	public static String OPERATING_SYSTEM_WINDOWS_XP = "Windows XP";
	public static String OPERATING_SYSTEM_WINDOWS_VISTA = "Windows Vista";
	public static String OPERATING_SYSTEM_LINUX = "Linux";
	public static String OPERATING_SYSTEM_FREEBSD = "FreeBSD";
	public static String OPERATING_SYSTEM_OSX = "Mac OS X";
	
	
	/** The default submit date format. */
	public static final String DEFAULT_DATE_SUBMIT_FORMAT = "yyyy-MM-dd";//yyyy-mm-dd
	
	/** The default submit datetime format. */
	public static final String DEFAULT_DATETIME_SUBMIT_FORMAT = "yyyy-MM-dd hh:mm:ss a";
	
	/** The default submit time format. */
	public static final String DEFAULT_TIME_SUBMIT_FORMAT = "hh:mm:ss a";
	
	/** The default display date format. */
	public static final String DEFAULT_DATE_DISPLAY_FORMAT = "dd-MM-yyyy";//yyyy-mm-dd
	
	/** The default display date format. */
	public static final String DEFAULT_DATETIME_DISPLAY_FORMAT = "dd-MM-yyyy hh:mm:ss a";
	
	/** The default display date format. */
	public static final String DEFAULT_TIME_DISPLAY_FORMAT = "hh:mm:ss a";
	
	public static final String SUPERUSER_ROLE = "Administrator";
	
	public static final String ANONYMOUS_ROLE = "Anonymous";
	
	public static final String AUTHENTICATED_ROLE = "Authenticated";
	
	/**
	 * All roles returned by this method are inserted into the database if they do not exist
	 * already. These roles are also forbidden to be deleted from the administration screens.
	 * 
	 * @return roles that are core to the system
	 */
	public static final Map<String, String> CORE_ROLES() {
		Map<String, String> roles = new HashMap<String, String>();
		
		roles.put(SUPERUSER_ROLE,
		    "Assigned to Administrators of openhouse. Gives additional access to change core aspects of the system.");
		roles.put(ANONYMOUS_ROLE, "Privileges for non-authenticated users.");
		roles.put(AUTHENTICATED_ROLE, "Privileges gained once authentication has been established.");
		
		return roles;
	}
}

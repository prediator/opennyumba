package org.openhouse.web;

public class WebConstants {
	
	public static final String INIT_REQ_UNIQUE_ID = "__INIT_REQ_UNIQUE_ID__";
	
	public static final String OPENHOUSE_CONTEXT_HTTPSESSION_ATTR = "__openhouse_context";
	
	public static final String OPENHOUSE_USER_CONTEXT_HTTPSESSION_ATTR = "__openhouse_user_context";
	
	public static final String OPENHOUSE_CLIENT_IP_HTTPSESSION_ATTR = "__openhouse_client_ip";
	
	public static final String OPENHOUSE_LOGIN_REDIRECT_HTTPSESSION_ATTR = "__openhouse_login_redirect";
	
	public static final String OPENHOUSE_MSG_ATTR = "openhouse_msg";
	
	public static final String OPENHOUSE_MSG_ARGS = "openhouse_msg_arguments";
	
	public static final String OPENHOUSE_ERROR_ATTR = "openhouse_error";
	
	public static final String OPENHOUSE_ERROR_ARGS = "openhouse_error_arguments";
	
	public static final String OPENHOUSE_LANGUAGE_COOKIE_NAME = "__openhouse_language";
	
	public static final String OPENHOUSE_USER_OVERRIDE_PARAM = "__openhouse_user_over_id";
	
	public static final String OPENHOUSE_ANALYSIS_IN_PROGRESS_ATTR = "__openhouse_analysis_in_progress";
	
	public static final String OPENHOUSE_DYNAMIC_FORM_IN_PROGRESS_ATTR = "__openhouse_dynamic_form_in_progress";
	
	public static final String OPENHOUSE_PATIENT_SET_ATTR = "__openhouse_patient_set";
	
	public static final Integer OPENHOUSE_PATIENTSET_PAGE_SIZE = 25;
	
	public static final String OPENHOUSE_DYNAMIC_FORM_KEEPALIVE = "__openhouse_dynamic_form_keepalive";
	
	public static final String OPENHOUSE_HEADER_USE_MINIMAL = "__openhouse_use_minimal_header";
	
	public static final String OPENHOUSE_PORTLET_MODEL_NAME = "model";
	
	public static final String OPENHOUSE_PORTLET_LAST_REQ_ID = "__openhouse_portlet_last_req_id";
	
	public static final String OPENHOUSE_PORTLET_CACHED_MODEL = "__openhouse_portlet_cached_model";
	
	// these vars filled in by org.openhouse.web.Listener at webapp start time
	public static String BUILD_TIMESTAMP = "";
	
	public static String WEBAPP_NAME = "openhouse";
	
	// ComplexObsHandler views specific to the web layer:
	public static final String HTML_VIEW = "html_view";
	
	public static final String HYPERLINK_VIEW = "hyperlink_view";
	
	/**
	 * Page in the webapp used for initial setup of the database connection if no valid one exists
	 */
	public static final String SETUP_PAGE_URL = "initialsetup";
	
	/**
	 * Global property name for the number of times one IP can fail at logging in before being
	 * locked out. A value of 0 for this property means no IP lockout checks.
	 * 
	 * @see org.openhouse.web.servlet.LoginServlet
	 */
	public static String GP_ALLOWED_LOGIN_ATTEMPTS_PER_IP = "security.loginAttemptsAllowedPerIP";
}

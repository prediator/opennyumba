package org.openhouse.api.context;

import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openhouse.api.dao.ContextDAO;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.APIException;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.service.AdministrationService;
import org.openhouse.api.service.AuthenticationService;
import org.openhouse.api.service.HouseholdService;
import org.openhouse.api.service.LocationService;
import org.openhouse.api.service.SchedulerService;
import org.openhouse.api.service.TaskService;
import org.openhouse.api.service.UserService;
import org.openhouse.util.LocaleUtility;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class Context {

	//private static final Log log = LogFactory.getLog(Context.class);
	//private Logger log = Logger.getLogger(this.getClass());
	private static final Logger log = Logger.getLogger(Context.class);

	private static ContextDAO contextDAO;

	//	Using "wrapper" (Object array) around UserContext to avoid ThreadLocal bug in Java 1.5
	private static final ThreadLocal<Object[] /*UserContext */> userContextHolder = new ThreadLocal<Object[] /*UserContext*/>();
	private static ServiceContext serviceContext;

	private static Properties runtimeProperties = new Properties();

	/**
	 * Default public constructor
	 * 
	 */
	public Context() { }

	/**
	 * Gets the context's data access object
	 * 
	 * @return ContextDAO
	 */
	private static ContextDAO getContextDAO() {
		return contextDAO;
	}

	/**
	 * Used to set the context's DAO for the application.
	 * 
	 * @param dao
	 */
	public void setContextDAO(ContextDAO dao) {
		contextDAO = dao;
	}

	/**
	 * Sets the user context on the thread local so that the service layer can 
	 * perform authentication/authorization checks.
	 *
	 * TODO Make thread-safe because this might be accessed by several thread at the same time.
	 * Making this thread safe might make this a bottleneck.
	 * 
	 * @param ctx
	 */
	public static void setUserContext(UserContext ctx) { 
		if (log.isTraceEnabled())
			log.trace("Setting user context " + ctx);

		Object[] arr = new Object[] {ctx};
		userContextHolder.set(arr);
	}

	/**
	 * Clears the user context from the threadlocal.
	 */
	public static void clearUserContext() {
		if (log.isTraceEnabled())
			log.trace("Clearing user context " + userContextHolder.get());

		userContextHolder.remove();
	}

	/**
	 * Gets the user context from the thread local.
	 * This might be accessed by several threads at the same time.
	 * 
	 * @return
	 */
	public static UserContext getUserContext(){
		Object[] arr = userContextHolder.get();
		
		if (log.isTraceEnabled())
			log.trace("Getting user context " + arr + " from userContextHolder " + userContextHolder);
		
		if (arr == null) {
			log.trace("userContext is null.");
			throw new APIException("A user context must first be passed to setUserContext()...use Context.openSession() (and closeSession() to prevent memory leaks!) before using the API");
		}
		return (UserContext) userContextHolder.get()[0];

	}

	/**
	 * Gets the service context.  
	 * 
	 * @return
	 */
	private static ServiceContext getServiceContext() {
		if (serviceContext == null) {
			log.error("serviceContext is null.  Creating new ServiceContext()");
			serviceContext = ServiceContext.getInstance();
		}

		if (log.isTraceEnabled())
			log.trace("serviceContext: " + serviceContext);

		return ServiceContext.getInstance();
	}

	/**
	 * Sets the service context.
	 * 
	 * @param ctx
	 */
	public void setServiceContext(ServiceContext ctx) { 
		serviceContext = ctx;
	}

	/**
	 * Used to authenticate user within the context
	 * 
	 * @param username
	 *            user's identifier token for login
	 * @param password
	 *            user's password for authenticating to context
	 * @throws OpenHouseException 
	 * @throws ContextAuthenticationException
	 */
	public static User authenticate(String username, String password) throws OpenHouseException {
		if (log.isDebugEnabled())
			log.debug("Authenticating with username: " + username);

		return getUserContext().authenticate(username, password, getContextDAO());
	}
	
	
	/**
	 * @return true if user has been authenticated in this context
	 */
	public static boolean isAuthenticated() {
		return getAuthenticatedUser() != null;
	}
	
	public static void logOut() throws OpenHouseException  {
		
		String username = "";
		if(Context.getAuthenticatedUser() != null)
			username  = Context.getAuthenticatedUser().getUsername();
		
		if (log.isDebugEnabled())
			log.debug("Logging out with username: " + username);

		getUserContext().logout();
	}
	
	public static boolean isSessionOpen() {
		return userContextHolder.get() != null;
	}
	
	public static Locale getLocale() {
		// if a session hasn't been opened, just fetch the default
		if (!isSessionOpen()) {
			return LocaleUtility.getDefaultLocale();
		}
		return getUserContext().getLocale();
	}
	
	public static void setAuthenticatedUser(User user){
		getUserContext().setAuthenticatedUser(user);
	}

	public static User getAuthenticatedUser(){
		return getUserContext().getAuthenticatedUser();
	}

	public static AdministrationService getAdministrationService() {
		return getServiceContext().getAdministrationService();
	}
	
	public static SchedulerService getSchedulerService() {
		return getServiceContext().getSchedulerService();
	}
	
	public static UserService getUserService(){
		return getServiceContext().getUserService();
	}
	
	public static LocationService getLocationService(){
		return getServiceContext().getLocationService();
	}
	
	public static AuthenticationService getAuthenticationService() {
		return getServiceContext().getAuthenticationService();
	}
	
	public static TaskService getTaskService() {
		return getServiceContext().getTaskService();
	}
	
	public static HouseholdService getHouseholdService() {
		return getServiceContext().getHouseholdService();
	}
	
	public static void openSession(){
		setUserContext(new UserContext()); // must be cleared out in closeSession()
		getContextDAO().openSession();
	}

	public static void closeSession(){
		clearUserContext(); // because we set a UserContext on the current thread in openSession()
		getContextDAO().closeSession();
	}

	public static void evictFromSession(Object obj){
		getContextDAO().evictFromSession(obj);
	}

	public static void clearSession(){
		getContextDAO().clearSession();
	}

	/**
	 * Starts OpenHouse
	 * Should be called prior to any kind of activity
	 * 
	 * @param props runtime properties to use for startup
	 */
	public static void startup(Properties props) {

		System.out.println("Starting OpenHouse v1.0");

		// do any context database specific startup
		getContextDAO().startup(props);

		// start the scheduled tasks
		//getSchedulerService().start();
	}

	/**
	 * Starts the OpenHouse in a _non-webapp_ environment
	 * 
	 * @param url database url like "jdbc:mysql://localhost:3306/openhouse?autoReconnect=true"
	 * @param username connection username
	 * @param password connection password
	 * @param properties other startup properties
	 */
	public static void startup(String url, String username, String password, Properties properties) {
		if (properties == null)
			properties = new Properties();

		properties.put("connection.url", url);
		properties.put("connection.username", username);
		properties.put("connection.password", password);
		//setRuntimeProperties(properties);

		startup(properties);
	}

	/**
	 * Stops OpenHouse
	 * Should be called after all activity has ended and application is closing
	 */
	public static void shutdown() {

		log.debug("Shutting down the scheduler");
		try {
			// Needs to be shutdown before Hibernate
			//SchedulerUtil.shutdown();
		}
		catch (Exception e) {
			log.warn("Error while shutting down scheduler service", e);
		}

		log.debug("Shutting down the modules");
		try {
			//ModuleUtil.shutdown();
		}
		catch (Exception e) {
			log.warn("Error while shutting down module system", e);
		}

		log.debug("Shutting down the context");
		try {
			ContextDAO dao = null;
			try {
				dao = getContextDAO();
			}
			catch (Exception e) {
				// pass
			}
			if (dao != null)
				dao.shutdown();
		}
		catch (Exception e) {
			log.warn("Error while shutting down context dao", e);
		}
	}

	/**
	 * Set the runtime properties to be used by this OpenHouse instance
	 * 
	 * @param props runtime properties
	 */
	public static void setRuntimeProperties(Properties props) {
		runtimeProperties = props;
	}

	/**
	 * Get the runtime properties that this OpenHouse instance was started with
	 * 
	 * @return copy of the runtime properties
	 */
	public static Properties getRuntimeProperties() {
		if (log.isTraceEnabled())
			log.trace("getting runtime properties. size: " + runtimeProperties.size());
		
		Properties props = new Properties();
		props.putAll(runtimeProperties);
		
		return props;
	}
	
	/**
	 * logs out the "active" (authenticated) user within context
	 * 
	 * @see #authenticate
	 */
	public static void logout() {
		if (!isSessionOpen())
			return; // fail early if there isn't even a session open
			
		if (log.isDebugEnabled())
			log.debug("Logging out : " + getAuthenticatedUser());
		
		getUserContext().logout();
		
		//reset the UserContext object (usually cleared out by closeSession() soon after this)
		setUserContext(new UserContext());
	}

}

package org.openhouse.api.service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;

import org.openhouse.api.GlobalPropertyListener;
import org.openhouse.api.dao.AdministrationDAO;
import org.openhouse.api.database.model.GlobalProperty;
import org.openhouse.api.exception.APIException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdministrationService {
	
	/**
	 * Used by Spring to set the specific/chosen database access implementation
	 * 
	 * @param dao The dao implementation to use
	 */
	public void setAdministrationDAO(AdministrationDAO dao);
	
	
	/**
	 * Get a global property by its uuid. There should be only one of these in the database (well,
	 * in the world actually). If multiple are found, an error is thrown.
	 * 
	 * @return the global property matching the given uuid
	 */
	@Transactional(readOnly = true)
	public GlobalProperty getGlobalPropertyByUuid(String uuid) throws APIException;
	
	/**
	 * Get a listing or important variables used in openhouse
	 * 
	 * @return a map from variable name to variable value
	 * @should return all registered system variables
	 */
	@Transactional(readOnly = true)
	//Authorized(openhouseConstants.PRIV_VIEW_ADMIN_FUNCTIONS)
	public SortedMap<String, String> getSystemVariables() throws APIException;
	
	/**
	 * Gets the global property that has the given <code>propertyName</code>.
	 */
	@Transactional(readOnly = true)
	public String getGlobalProperty(String propertyName) throws APIException;
	
	/**
	 * Gets the global property that has the given <code>propertyName</code>
	 */
	@Transactional(readOnly = true)
	public String getGlobalProperty(String propertyName, String defaultValue) throws APIException;
	
	/**
	 * Gets the global property that has the given <code>propertyName</code>
	 * 
	 * @param propertyName property key to look for
	 * @return the global property that matches the given <code>propertyName</code>
	 * @should return null when no global property match given property name
	 */
	@Transactional(readOnly = true)
	public GlobalProperty getGlobalPropertyObject(String propertyName);
	
	/**
	 * Gets all global properties that begin with <code>prefix</code>.
	 * 
	 * @param prefix The beginning of the property name to match.
	 * @return a <code>List</code> of <code>GlobalProperty</code>s that match <code>prefix</code>
	 * @since 1.5
	 * @should return all relevant global properties in the database
	 */
	@Transactional(readOnly = true)
	public List<GlobalProperty> getGlobalPropertiesByPrefix(String prefix);
	
	/**
	 * Gets all global properties that end with <code>suffix</code>.
	 * 
	 * @param prefix The end of the property name to match.
	 * @return a <code>List</code> of <code>GlobalProperty</code>s that match <code>.*suffix</code>
	 * @since 1.6
	 * @should return all relevant global properties in the database
	 */
	@Transactional(readOnly = true)
	public List<GlobalProperty> getGlobalPropertiesBySuffix(String suffix);
	
	/**
	 * Get a list of all global properties in the system
	 * 
	 * @return list of global properties
	 * @should return all global properties in the database
	 */
	@Transactional(readOnly = true)
	//@Authorized(openhouseConstants.PRIV_VIEW_GLOBAL_PROPERTIES)
	public List<GlobalProperty> getAllGlobalProperties() throws APIException;
	
	/**
	 * Save the given list of global properties to the database overwriting all values with the
	 * given values. If a value exists in the database that does not exist in the given list, that
	 * property is deleted from the database.
	 * 
	 * @param props list of GlobalProperty objects to save
	 * @return the saved global properties
	 * @should save all global properties to the database
	 * @should not fail with empty list
	 * @should delete property from database if not in list
	 * @should assign uuid to all new properties
	 */
	//@Authorized(openhouseConstants.PRIV_MANAGE_GLOBAL_PROPERTIES)
	public List<GlobalProperty> saveGlobalProperties(List<GlobalProperty> props) throws APIException;
	
	/**
	 * Completely remove the given global property from the database
	 * 
	 * @param globalProperty the global property to delete/remove from the database
	 * @throws APIException
	 * @should delete global property from database
	 */
	//@Authorized(openhouseConstants.PRIV_PURGE_GLOBAL_PROPERTIES)
	public void purgeGlobalProperty(GlobalProperty globalProperty) throws APIException;
	
	/**
	 * Save the given global property to the database
	 * 
	 * @param gp global property to save
	 * @return the saved global property
	 * @throws APIException
	 * @should create global property in database
	 * @should overwrite global property if exists
	 */
	//@Authorized(openhouseConstants.PRIV_MANAGE_GLOBAL_PROPERTIES)
	public GlobalProperty saveGlobalProperty(GlobalProperty gp) throws APIException;
	
	/**
	 * Allows code to be notified when a global property is created/edited/deleted.
	 * 
	 * @see GlobalPropertyListener
	 * @param listener The listener to register
	 */
	public void addGlobalPropertyListener(GlobalPropertyListener listener);
	
	/**
	 * Removes a GlobalPropertyListener previously registered by
	 * {@link #addGlobalPropertyListener(GlobalPropertyListener)}
	 * 
	 * @param listener
	 */
	public void removeGlobalPropertyListener(GlobalPropertyListener listener);
	
	
	
	/**
	 * Gets the list of locales which the administrator has allowed for use on the system. This is
	 * specified with a global property named
	 */
	@Transactional(readOnly = true)
	public List<Locale> getAllowedLocales();
	
	/**
	 * Gets the list of locales for which localized messages are available for the user interface
	 * (presentation layer). This set includes all the available locales (as indicated by the
	 * MessageSourceService) filtered by the allowed locales (as indicated by this
	 * AdministrationService).
	 */
	@Transactional(readOnly = true)
	public Set<Locale> getPresentationLocales();
	
}

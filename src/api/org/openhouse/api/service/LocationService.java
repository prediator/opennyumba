package org.openhouse.api.service;

import java.util.List;

import org.openhouse.api.database.model.Location;
import org.openhouse.api.database.model.LocationTag;
import org.openhouse.api.exception.APIException;
import org.springframework.transaction.annotation.Transactional;


public interface LocationService {
	
	/**
	 * Save location to database (create if new or update if changed)
	 * @param location is the location to be saved to the database
	 */
	public Location saveLocation(Location location) throws APIException;
	
	/**
	 * Returns a location given that locations primary key <code>locationId</code> A null value is
	 * returned if no location exists with this location.
	 * @param locationId integer primary key of the location to find
	 * @return Location object that has location.locationId = <code>locationId</code> passed in.
	 */
	@Transactional(readOnly = true)
	public Location getLocation(Integer locationId) throws APIException;
	
	/**
	 * Returns a location given the location's exact <code>name</code> A null value is returned if
	 * there is no location with this name
	 * @param name the exact name of the location to match on
	 * @return Location matching the <code>name</code> to Location.name
	 */
	@Transactional(readOnly = true)
	public Location getLocation(String name) throws APIException;
	
	/**
	 * Returns the default location for this implementation.
	 * @return The default location for this implementation.
	 */
	@Transactional(readOnly = true)
	public Location getDefaultLocation() throws APIException;
	
	/**
	 * Returns all locations, includes retired locations. This method delegates to the
	 * #getAllLocations(boolean) method
	 * @return locations that are in the database
	 */
	@Transactional(readOnly = true)
	public List<Location> getAllLocations() throws APIException;
	
	/**
	 * Returns all locations.
	 * @param includeRetired whether or not to include retired locations
	 */
	@Transactional(readOnly = true)
	public List<Location> getAllLocations(boolean includeRetired) throws APIException;
	
	/**
	 * Returns locations that match the beginning of the given string. A null list will never be
	 * returned. An empty list will be returned if there are no locations. Search is case
	 * insensitive. matching this <code>nameFragment</code>
	 * @param nameFragment is the string used to search for locations
	 */
	@Transactional(readOnly = true)
	public List<Location> getLocations(String nameFragment) throws APIException;
	
	/**
	 * Returns locations that contain the given tag.
	 * @param tag LocationTag criterion
	 */
	@Transactional(readOnly = true)
	public List<Location> getLocationsByTag(LocationTag tag) throws APIException;
	
	/**
	 * Returns locations that are mapped to all given tags.
	 * @param tags Set of LocationTag criteria
	 */
	@Transactional(readOnly = true)
	public List<Location> getLocationsHavingAllTags(List<LocationTag> tags) throws APIException;
	
	/**
	 * Returns locations that are mapped to any of the given tags.
	 * @param tags Set of LocationTag criteria
	 */
	@Transactional(readOnly = true)
	public List<Location> getLocationsHavingAnyTag(List<LocationTag> tags) throws APIException;
	
	/**
	 * Retires the given location. This effectively removes the location from circulation or use.
	 * @param location location to be retired
	 * @param reason is the reason why the location is being retired
	 */
	public Location retireLocation(Location location, String reason) throws APIException;
	
	/**
	 * Unretire the given location. This restores a previously retired location back into
	 * circulation and use.
	 * @param location
	 * @return the newly unretired location
	 */
	public Location unretireLocation(Location location) throws APIException;
	
	/**
	 * Completely remove a location from the database (not reversible) This method delegates to
	 * #purgeLocation(location, boolean) method
	 * @param location the Location to clean out of the database.
	 */
	public void purgeLocation(Location location) throws APIException;
	
	/**
	 * Save location tag to database (create if new or update if changed)
	 * @param tag is the tag to be saved to the database
	 */
	public LocationTag saveLocationTag(LocationTag tag) throws APIException;
	
	/**
	 * Returns a location tag given that locations primary key <code>locationTagId</code>. A null
	 * value is returned if no tag exists with this ID.
	 * @param locationTagId integer primary key of the location tag to find
	 * @return LocationTag object that has LocationTag.locationTagId = <code>locationTagId</code>
	 *         passed in.
	 */
	@Transactional(readOnly = true)
	public LocationTag getLocationTag(Integer locationTagId) throws APIException;
	
	/**
	 * Returns a location tag given the location's exact name (tag). A null value is returned if
	 * there is no tag with this name.
	 * @param tag the exact name of the tag to match on
	 * @return LocationTag matching the name to LocationTag.tag
	 */
	@Transactional(readOnly = true)
	public LocationTag getLocationTagByName(String tag) throws APIException;
	
	/**
	 * Returns all location tags, includes retired location tags. This method delegates to the
	 * #getAllLocationTags(boolean) method.
	 * @return location tags that are in the database
	 * @since 1.5
	 * @should return all location tags including retired
	 */
	@Transactional(readOnly = true)
	public List<LocationTag> getAllLocationTags() throws APIException;
	
	/**
	 * Returns all location tags.
	 * @param includeRetired whether or not to include retired location tags
	 */
	@Transactional(readOnly = true)
	public List<LocationTag> getAllLocationTags(boolean includeRetired) throws APIException;
	
	/**
	 * Returns location tags that match the beginning of the given string. A null list will never be
	 * returned. An empty list will be returned if there are no tags. Search is case insensitive.
	 * matching this <code>search</code>
	 * @param search is the string used to search for tags
	 */
	@Transactional(readOnly = true)
	public List<LocationTag> getLocationTags(String search) throws APIException;
	
	/**
	 * Retire the given location tag. This effectively removes the tag from circulation or use.
	 * @param tag location tag to be retired
	 * @param reason is the reason why the location tag is being retired
	 */
	public LocationTag retireLocationTag(LocationTag tag, String reason) throws APIException;
	
	/**
	 * Unretire the given location tag. This restores a previously retired tag back into circulation
	 * and use.
	 * @param tag
	 * @return the newly unretired location tag
	 */
	public LocationTag unretireLocationTag(LocationTag tag) throws APIException;
	
	/**
	 * Completely remove a location tag from the database (not reversible).
	 * @param tag the LocationTag to clean out of the database.
	 */
	public void purgeLocationTag(LocationTag tag) throws APIException;

}

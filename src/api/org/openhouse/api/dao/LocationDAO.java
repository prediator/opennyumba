package org.openhouse.api.dao;

import java.util.List;

import org.openhouse.api.database.model.Location;
import org.openhouse.api.database.model.LocationTag;

/**
 * Location-related database functions
 */
public interface LocationDAO {
	
	public Location saveLocation(Location location);
	
	public Location getLocation(Integer locationId);
	
	public Location getLocation(String name);
	
	public List<Location> getAllLocations(boolean includeRetired);
	
	public List<Location> getLocations(String search);
	
	public void deleteLocation(Location location);
	
	public LocationTag saveLocationTag(LocationTag tag);
	
	public LocationTag getLocationTag(Integer locationTagId);

	public LocationTag getLocationTagByName(String tag);
	
	public List<LocationTag> getAllLocationTags(boolean includeRetired);

	public List<LocationTag> getLocationTags(String search);

	public void deleteLocationTag(LocationTag tag);
}
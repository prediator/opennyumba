package org.openhouse.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openhouse.api.context.Context;
import org.openhouse.api.dao.LocationDAO;
import org.openhouse.api.database.model.Location;
import org.openhouse.api.database.model.LocationTag;
import org.openhouse.api.exception.APIException;
import org.openhouse.api.service.LocationService;
import org.springframework.util.StringUtils;

/**
 * Default implementation of the {@link LocationService}
 * <p>
 * This class should not be instantiated alone, get a service class from the Context:
 * Context.getLocationService();
 * 
 */
public class LocationServiceImpl implements LocationService {
	
	private LocationDAO dao;
	
	public void setDao(LocationDAO dao) {
		this.dao = dao;
	}
	
	public Location saveLocation(Location location) throws APIException {
		if (location.getName() == null) {
			throw new APIException("Location name is required");
		}
		
		// Check for transient tags. If found, try to match by name and overwrite, otherwise throw exception.
		if (location.getTags() != null) {
			for (LocationTag tag : location.getTags()) {
				
				if (tag.getLocationTagId() == null) {
					if (!StringUtils.hasLength(tag.getTag()))
						throw new APIException("A tag name is required");
					
					LocationTag existing = Context.getLocationService().getLocationTagByName(tag.getTag());
					if (existing != null) {
						location.removeTag(tag);
						location.addTag(existing);
					} else
						throw new APIException(
						        "Cannot add transient tags!  Save all location tags to the database before saving this location");
				}
			}
		}
		
		return dao.saveLocation(location);
	}
	
	public Location getLocation(Integer locationId) throws APIException {
		return dao.getLocation(locationId);
	}
	
	public Location getLocation(String name) throws APIException {
		return dao.getLocation(name);
	}
	
	public Location getDefaultLocation() throws APIException {
		
		// TODO The name of the default location should be configured using global properties 
		Location location = getLocation("Unknown Location");
		
		// If Unknown Location does not exist, try Unknown
		if (location == null) {
			location = getLocation("Unknown");
		}
		
		// If neither exist, get the first available location
		if (location == null) {
			location = getLocation(Integer.valueOf(1));
		}
		
		return location;
	}
	
	public List<Location> getAllLocations() throws APIException {
		return dao.getAllLocations(true);
	}

	public List<Location> getAllLocations(boolean includeRetired) throws APIException {
		return dao.getAllLocations(includeRetired);
	}
	
	public List<Location> getLocations(String nameFragment) throws APIException {
		return dao.getLocations(nameFragment);
	}
	
	public List<Location> getLocationsByTag(LocationTag tag) throws APIException {
		List<Location> locations = new ArrayList<Location>();
		
		for (Location l : dao.getAllLocations(false))
			if (l.getTags().contains(tag))
				locations.add(l);
		
		return locations;
	}
	
	public List<Location> getLocationsHavingAllTags(List<LocationTag> tags) throws APIException {
		List<Location> locations = new ArrayList<Location>();
		
		for (Location loc : dao.getAllLocations(false))
			if (loc.getTags().containsAll(tags))
				locations.add(loc);
		
		return locations;
	}
	
	public List<Location> getLocationsHavingAnyTag(List<LocationTag> tags) throws APIException {
		List<Location> locations = new ArrayList<Location>();
		
		for (Location loc : dao.getAllLocations(false)) {
			for (LocationTag t : tags) {
				if (loc.getTags().contains(t) && !locations.contains(loc))
					locations.add(loc);
			}
		}
		
		return locations;
	}
	
	public Location retireLocation(Location location, String reason) throws APIException {
		location.setRetired(true);
		location.setRetireReason(reason);
		return saveLocation(location);
	}
	
	public Location unretireLocation(Location location) throws APIException {
		location.setRetired(false);
		return saveLocation(location);
	}
	
	public void purgeLocation(Location location) throws APIException {
		dao.deleteLocation(location);
	}
	
	public LocationTag saveLocationTag(LocationTag tag) throws APIException {
		if (tag.getTag() == null) {
			throw new APIException("Tag name is required");
		}
		return dao.saveLocationTag(tag);
	}
	
	public LocationTag getLocationTag(Integer locationTagId) throws APIException {
		return dao.getLocationTag(locationTagId);
	}
	
	public LocationTag getLocationTagByName(String tag) throws APIException {
		return dao.getLocationTagByName(tag);
	}
	
	public List<LocationTag> getAllLocationTags() throws APIException {
		return dao.getAllLocationTags(true);
	}
	
	public List<LocationTag> getAllLocationTags(boolean includeRetired) throws APIException {
		return dao.getAllLocationTags(includeRetired);
	}
	
	public List<LocationTag> getLocationTags(String search) throws APIException {
		if (search == null || search.equals(""))
			return getAllLocationTags(true);
		
		return dao.getLocationTags(search);
	}
	
	public LocationTag retireLocationTag(LocationTag tag, String reason) throws APIException {
		if (tag.isRetired()) {
			return tag;
		} else {
			if (reason == null)
				throw new APIException("Reason is required");
			tag.setRetired(true);
			tag.setRetireReason(reason);
			tag.setRetiredBy(Context.getAuthenticatedUser());
			tag.setDateRetired(new Date());
			return saveLocationTag(tag);
		}
	}
	
	public LocationTag unretireLocationTag(LocationTag tag) throws APIException {
		tag.setRetired(false);
		tag.setRetireReason(null);
		tag.setRetiredBy(null);
		tag.setDateRetired(null);
		return saveLocationTag(tag);
	}
	
	public void purgeLocationTag(LocationTag tag) throws APIException {
		dao.deleteLocationTag(tag);
	}
}
package org.openhouse.api.database.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openhouse.api.context.Context;
import org.openhouse.api.exception.APIException;

public class Location extends BaseCommonData implements java.io.Serializable {
	
	public static final long serialVersionUID = 455634L;
	
	public static final int LOCATION_UNKNOWN = 1;
	
	private Integer locationId;
	
	private String address1;
	
	private String address2;
	
	private String cityVillage;
	
	private String stateProvince;
	
	private String country;
	
	private String postalCode;
	
	private String latitude;
	
	private String longitude;
	
	private String countyDistrict;
	
	private String neighborhoodCell;
	
	private String townshipDivision;
	
	private String region;
	
	private String subregion;
	
	private Location parentLocation;
	
	private Set<Location> childLocations;
	
	private Set<LocationTag> tags;
	
	// Constructors
	
	/** default constructor */
	public Location() {
	}
	
	/** constructor with id */
	public Location(Integer locationId) {
		this.locationId = locationId;
	}
	
	/**
	 * Compares two objects for similarity
	 * 
	 * @param obj
	 * @return boolean true/false whether or not they are the same objects
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			Location loc = (Location) obj;
			if (this.getLocationId() != null && loc.getLocationId() != null)
				return (this.getLocationId().equals(loc.getLocationId()));
		}
		return obj == this;
	}
	
	public int hashCode() {
		if (this.getLocationId() == null)
			return super.hashCode();
		return this.getLocationId().hashCode();
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getCityVillage() {
		return cityVillage;
	}
	
	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getStateProvince() {
		return stateProvince;
	}
	
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	
	public String toString() {
		return getName();
	}
	
	public String getCountyDistrict() {
		return countyDistrict;
	}
	
	public void setCountyDistrict(String countyDistrict) {
		this.countyDistrict = countyDistrict;
	}
	
	public String getNeighborhoodCell() {
		return neighborhoodCell;
	}
	
	public void setNeighborhoodCell(String neighborhoodCell) {
		this.neighborhoodCell = neighborhoodCell;
	}
	
	public List<Location> findPossibleValues(String searchText) {
		try {
			return Context.getLocationService().getLocations(searchText);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	public List<Location> getPossibleValues() {
		try {
			return Context.getLocationService().getAllLocations();
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	public Location hydrate(String locationId) {
		try {
			return Context.getLocationService().getLocation(Integer.valueOf(locationId));
		}
		catch (Exception e) {
			return new Location();
		}
	}
	
	public String serialize() {
		if (getLocationId() != null)
			return "" + getLocationId();
		else
			return "";
	}
	
	public String getDisplayString() {
		return getName();
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getSubregion() {
		return subregion;
	}
	
	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}
	
	public String getTownshipDivision() {
		return townshipDivision;
	}
	
	public void setTownshipDivision(String townshipDivision) {
		this.townshipDivision = townshipDivision;
	}
	
	public Location getParentLocation() {
		return parentLocation;
	}
	
	public void setParentLocation(Location parentLocationId) {
		this.parentLocation = parentLocationId;
	}
	
	public Set<Location> getChildLocations() {
		return childLocations;
	}
	
	public Set<Location> getChildLocations(boolean includeRetired) {
		Set<Location> ret = new HashSet<Location>();
		if (includeRetired)
			ret = getChildLocations();
		else if (getChildLocations() != null) {
			for (Location l : getChildLocations()) {
				if (!l.isRetired())
					ret.add(l);
			}
		}
		return ret;
	}
	
	public void setChildLocations(Set<Location> childLocations) {
		this.childLocations = childLocations;
	}
	
	public void addChildLocation(Location child) {
		if (child == null)
			return;
		
		if (getChildLocations() == null)
			childLocations = new HashSet<Location>();
		
		if (child.equals(this))
			throw new APIException("A location cannot be its own child!");
		
		// Traverse all the way up (down?) to the root, then check whether the child is already
		// anywhere in the tree
		Location root = this;
		while (root.getParentLocation() != null)
			root = root.getParentLocation();
		
		if (isInHierarchy(child, root))
			throw new APIException("Location hierarchy loop detected! You cannot add: '" + child + "' to the parent: '"
			        + this
			        + "' because it is in the parent hierarchy somewhere already and a location cannot be its own parent.");
		
		child.setParentLocation(this);
		childLocations.add(child);
	}
	
	public static Boolean isInHierarchy(Location location, Location root) {
		if (location == null || root == null)
			return false;
		if (root.equals(location))
			return true;
		if (root.getChildLocations() != null) {
			for (Location l : root.getChildLocations())
				return isInHierarchy(location, l);
		}
		
		return false;
	}
	
	public void removeChildLocation(Location child) {
		if (getChildLocations() != null)
			childLocations.remove(child);
	}
	
	public Set<LocationTag> getTags() {
		return tags;
	}
	
	public void setTags(Set<LocationTag> tags) {
		this.tags = tags;
	}

	public void addTag(LocationTag tag) {
		if (getTags() == null)
			tags = new HashSet<LocationTag>();
		if (tag != null && !tags.contains(tag))
			tags.add(tag);
	}
	
	public void removeTag(LocationTag tag) {
		if (getTags() != null)
			tags.remove(tag);
	}
	
	public Boolean hasTag(String tagToFind) {
		if (tagToFind != null && getTags() != null) {
			for (LocationTag locTag : getTags()) {
				if (locTag.getTag().equals(tagToFind)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Integer getId() {
		
		return getLocationId();
	}
	
	public void setId(Integer id) {
		setLocationId(id);
		
	}
	
}

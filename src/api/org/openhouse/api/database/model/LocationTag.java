package org.openhouse.api.database.model;

public class LocationTag extends BaseCommonData implements java.io.Serializable {
	
	public static final long serialVersionUID = 7654L;
	
	private Integer locationTagId;
	
	private String tag;
	
	// Constructors
	
	/** default constructor */
	public LocationTag() {
	}
	
	/** constructor with id */
	public LocationTag(Integer locationTagId) {
		this.locationTagId = locationTagId;
	}
	
	public LocationTag(String tag, String description) {
		this.tag = tag;
		setDescription(description);
	}
	
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LocationTag))
			return false;
		
		LocationTag locationTag = (LocationTag) obj;
		if (this.locationTagId != null && locationTag.getLocationTagId() != null)
			return (this.locationTagId.equals(locationTag.getLocationTagId()));
		else
			return this == locationTag;
	}
	
	public int hashCode() {
		if (this.getLocationTagId() == null)
			return super.hashCode();
		return this.getLocationTagId().hashCode();
	}
	
	public Integer getLocationTagId() {
		return locationTagId;
	}
	
	public void setLocationTagId(Integer locationTagId) {
		this.locationTagId = locationTagId;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String toString() {
		return tag;
	}
	
	public Integer getId() {
		return getLocationTagId();
	}
	
	public void setId(Integer id) {
		setLocationTagId(id);
		
	}
}

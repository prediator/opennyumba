package org.openhouse.api.database.model;

import java.util.Date;


/**
 * This serves as the base class for domain objects.
 * 
 * @author Samuel Mbugua
 *
 */
public class BaseCommonData {
	
	private static final long serialVersionUID = 7939951597182605859L;

	private String name;
	private String description;
	private User creator;
	private Date dateCreated;	
	private User changedBy;
	private Date dateChanged;
	private Boolean retired = Boolean.FALSE;
	private User retiredBy;
	private Date dateRetired;
	private String retireReason;
	private Boolean voided = Boolean.FALSE;
	private User voidedBy;
	private Date dateVoided;
	private String voidReason;
	private String uuid;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getChangedBy(){
		return changedBy;
	}
	
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	public Date getDateChanged(){
		return dateChanged;
	}
	
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Boolean isRetired() {
		return retired;
	}
	
	public Boolean getRetired() {
		return isRetired();
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}

	public User getRetiredBy() {
		return retiredBy;
	}

	public void setRetiredBy(User retiredBy) {
		this.retiredBy = retiredBy;
	}

	public Date getDateRetired() {
		return dateRetired;
	}

	public void setDateRetired(Date dateRetired) {
		this.dateRetired = dateRetired;
	}

	public String getRetireReason() {
		return retireReason;
	}

	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}
	
	public Boolean isVoided() {
		return voided;
	}

	public Boolean getVoided() {
		return isVoided();
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public User getVoidedBy() {
		return voidedBy;
	}

	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}

	public Date getDateVoided() {
		return dateVoided;
	}

	public void setDateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}

	public String getVoidReason() {
		return voidReason;
	}

	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}

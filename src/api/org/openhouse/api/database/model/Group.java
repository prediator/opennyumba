package org.openhouse.api.database.model;
// Generated May 6, 2010 12:58:59 PM by Hibernate Tools 3.2.0.b9


import java.util.Date;

/**
 * Group generated by hbm2java
 */
public class Group  implements java.io.Serializable {


     private int groupId;
     private Integer groupType;
     private Integer location;
     private Integer creator;
     private Date dateCreated;
     private Short voided;
     private int voidedBy;
     private Date dateVoided;
     private String voidReason;
     private String uuid;

    public Group() {
    }

	
    public Group(int groupId, int voidedBy, String uuid) {
        this.groupId = groupId;
        this.voidedBy = voidedBy;
        this.uuid = uuid;
    }
    public Group(int groupId, Integer groupType, Integer location, Integer creator, Date dateCreated, Short voided, int voidedBy, Date dateVoided, String voidReason, String uuid) {
       this.groupId = groupId;
       this.groupType = groupType;
       this.location = location;
       this.creator = creator;
       this.dateCreated = dateCreated;
       this.voided = voided;
       this.voidedBy = voidedBy;
       this.dateVoided = dateVoided;
       this.voidReason = voidReason;
       this.uuid = uuid;
    }
   
    public int getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public Integer getGroupType() {
        return this.groupType;
    }
    
    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }
    public Integer getLocation() {
        return this.location;
    }
    
    public void setLocation(Integer location) {
        this.location = location;
    }
    public Integer getCreator() {
        return this.creator;
    }
    
    public void setCreator(Integer creator) {
        this.creator = creator;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public Short getVoided() {
        return this.voided;
    }
    
    public void setVoided(Short voided) {
        this.voided = voided;
    }
    public int getVoidedBy() {
        return this.voidedBy;
    }
    
    public void setVoidedBy(int voidedBy) {
        this.voidedBy = voidedBy;
    }
    public Date getDateVoided() {
        return this.dateVoided;
    }
    
    public void setDateVoided(Date dateVoided) {
        this.dateVoided = dateVoided;
    }
    public String getVoidReason() {
        return this.voidReason;
    }
    
    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }
    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }




}


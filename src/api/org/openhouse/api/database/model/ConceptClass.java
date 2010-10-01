package org.openhouse.api.database.model;
// Generated May 6, 2010 12:58:59 PM by Hibernate Tools 3.2.0.b9


import java.util.Date;

/**
 * ConceptClass generated by hbm2java
 */
public class ConceptClass  implements java.io.Serializable {


     private int conceptClassId;
     private String name;
     private String description;
     private int creator;
     private Date dateCreated;
     private short retired;
     private Integer retiredBy;
     private Date dateRetired;
     private String retireReason;
     private String uuid;

    public ConceptClass() {
    }

	
    public ConceptClass(int conceptClassId, String name, String description, int creator, Date dateCreated, short retired, String uuid) {
        this.conceptClassId = conceptClassId;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }
    public ConceptClass(int conceptClassId, String name, String description, int creator, Date dateCreated, short retired, Integer retiredBy, Date dateRetired, String retireReason, String uuid) {
       this.conceptClassId = conceptClassId;
       this.name = name;
       this.description = description;
       this.creator = creator;
       this.dateCreated = dateCreated;
       this.retired = retired;
       this.retiredBy = retiredBy;
       this.dateRetired = dateRetired;
       this.retireReason = retireReason;
       this.uuid = uuid;
    }
   
    public int getConceptClassId() {
        return this.conceptClassId;
    }
    
    public void setConceptClassId(int conceptClassId) {
        this.conceptClassId = conceptClassId;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCreator() {
        return this.creator;
    }
    
    public void setCreator(int creator) {
        this.creator = creator;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public short getRetired() {
        return this.retired;
    }
    
    public void setRetired(short retired) {
        this.retired = retired;
    }
    public Integer getRetiredBy() {
        return this.retiredBy;
    }
    
    public void setRetiredBy(Integer retiredBy) {
        this.retiredBy = retiredBy;
    }
    public Date getDateRetired() {
        return this.dateRetired;
    }
    
    public void setDateRetired(Date dateRetired) {
        this.dateRetired = dateRetired;
    }
    public String getRetireReason() {
        return this.retireReason;
    }
    
    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
    }
    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }




}



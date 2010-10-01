package org.openhouse.api.database.model;
// Generated May 6, 2010 12:58:59 PM by Hibernate Tools 3.2.0.b9



/**
 * RoleRoleId generated by hbm2java
 */
public class RoleRoleId  implements java.io.Serializable {


     private String parentRole;
     private String childRole;

    public RoleRoleId() {
    }

    public RoleRoleId(String parentRole, String childRole) {
       this.parentRole = parentRole;
       this.childRole = childRole;
    }
   
    public String getParentRole() {
        return this.parentRole;
    }
    
    public void setParentRole(String parentRole) {
        this.parentRole = parentRole;
    }
    public String getChildRole() {
        return this.childRole;
    }
    
    public void setChildRole(String childRole) {
        this.childRole = childRole;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RoleRoleId) ) return false;
		 RoleRoleId castOther = ( RoleRoleId ) other; 
         
		 return ( (this.getParentRole()==castOther.getParentRole()) || ( this.getParentRole()!=null && castOther.getParentRole()!=null && this.getParentRole().equals(castOther.getParentRole()) ) )
 && ( (this.getChildRole()==castOther.getChildRole()) || ( this.getChildRole()!=null && castOther.getChildRole()!=null && this.getChildRole().equals(castOther.getChildRole()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getParentRole() == null ? 0 : this.getParentRole().hashCode() );
         result = 37 * result + ( getChildRole() == null ? 0 : this.getChildRole().hashCode() );
         return result;
   }   


}



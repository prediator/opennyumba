package org.openhouse.api.database.model;
// Generated May 6, 2010 12:58:59 PM by Hibernate Tools 3.2.0.b9



/**
 * UserPropertyId generated by hbm2java
 */
public class UserPropertyId  implements java.io.Serializable {


     private int userId;
     private String property;

    public UserPropertyId() {
    }

    public UserPropertyId(int userId, String property) {
       this.userId = userId;
       this.property = property;
    }
   
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getProperty() {
        return this.property;
    }
    
    public void setProperty(String property) {
        this.property = property;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserPropertyId) ) return false;
		 UserPropertyId castOther = ( UserPropertyId ) other; 
         
		 return (this.getUserId()==castOther.getUserId())
 && ( (this.getProperty()==castOther.getProperty()) || ( this.getProperty()!=null && castOther.getProperty()!=null && this.getProperty().equals(castOther.getProperty()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getUserId();
         result = 37 * result + ( getProperty() == null ? 0 : this.getProperty().hashCode() );
         return result;
   }   


}



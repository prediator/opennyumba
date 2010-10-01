package org.openhouse.api.database.model;

/**
 * Privilege
 * 
 * @version 1.0
 */
public class Privilege extends BaseCommonData implements java.io.Serializable {
	
	public static final long serialVersionUID = 312L;
	
	private String privilege;
	
	
	/** default constructor */
	public Privilege() {
	}
	
	/** constructor with id */
	public Privilege(String privilege) {
		this.privilege = privilege;
	}
	
	public Privilege(String privilege, String description) {
		this.privilege = privilege;
		setDescription(description);
	}
	
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Privilege))
			return false;
		return privilege.equals(((Privilege) obj).privilege);
	}
	
	public int hashCode() {
		if (this.getPrivilege() == null)
			return super.hashCode();
		return this.getPrivilege().hashCode();
	}
	
	/**
	 * @return Returns the privilege.
	 */
	public String getPrivilege() {
		return privilege;
	}
	
	/**
	 * @param privilege The privilege to set.
	 */
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.privilege;
	}
	
	public Integer getId() {
		throw new UnsupportedOperationException();
		
	}
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
		
	}
}

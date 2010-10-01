package org.openhouse.api.database.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.util.OpenhouseConstants;


/**
 * This class is used to represent a user who can access the system.
 * 
 * @author Samuel Mbugua
 *
 */
public class User extends Person {

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -410052012755451028L;
	
	private transient static final Log log = LogFactory.getLog(User.class);

	private int userId;
	private String systemId;
	private String username;
	private String password;
	private String salt;
	private String secretQuestion;
	private String secretAnswer;
	
	private Set<Role> roles;
	private Map<String, String> userProperties;
	
	//temporary password storage before salting
	private String clearTextPassword;
	
	public User(){
		
	}
	
	/**
	 * Constructor used to create a user with a given login name and password.
	 * 
	 * @param userName the login name.
	 * @param clearTextPassword the non hashed password.
	 */
	public User(String userName, String clearTextPassword){
		this.username = userName;
	}
	
	/**
	 * Constructor used to create a user with a given database id,login name, hashed password and salt.
	 * 
	 * @param userId the database id.
	 * @param userName the login name.
	 * @param password the hashed password
	 * @param salt the salt.
	 */
	public User(int userId,String userName, String password, String salt){
		this.userId = userId;
		this.username = userName;
		this.password = password;
		this.salt = salt;
	}
	
	/**
	 * Constructor used to create a user with a given login name.
	 * 
	 * @param userName the login name.
	 */
	public User(String userName){
		this.username = userName;
	}
	
	/**
	 * Constructor used to create a user with a given database id and login name.
	 * 
	 * @param userId the database id.
	 * @param userName the login name.
	 */
	public User(int userId,String userName){
		this.userId = userId;
		this.username = userName;
	}
	
	/** constructor with id */
	public User(Integer userId) {
		super(userId);
		this.userId = userId;
	}
	
	/** constructor with person object */
	public User(Person person) {
		super(person);
		if (person != null)
			userId = person.getPersonId();
	}
	
	/**
	 * Return true if this user has all privileges
	 * 
	 * @return true/false if this user is defined as a super user
	 */
	public boolean isSuperUser() {
		Set<Role> tmproles = getAllRoles();
		
		Role role = new Role(OpenhouseConstants.SUPERUSER_ROLE); // default administrator with
		// complete control
		
		if (tmproles.contains(role))
			return true;
		
		return false;
	}
	
	public boolean hasPrivilege(String privilege) {
		
		// All authenticated users have the "" (empty) privilege
		if (privilege == null || privilege.equals(""))
			return true;
		
		if (isSuperUser())
			return true;
		
		Set<Role> tmproles = getAllRoles();
		
		// loop over the roles and check each for the privilege
		for (Iterator<Role> i = tmproles.iterator(); i.hasNext();) {
			if (i.next().hasPrivilege(privilege))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Check if this user has the given String role
	 * 
	 * @param r String name of a role to check
	 * @return Returns true if this user has the specified role, false otherwise
	 */
	public boolean hasRole(String r) {
		return hasRole(r, false);
	}
	
	/**
	 * Checks if this user has the given String role
	 * 
	 * @param r String name of a role to check
	 * @param ignoreSuperUser If this is false, then this method will always return true for a
	 *            superuser.
	 * @return Returns true if the user has the given role, or if ignoreSuperUser is false and the
	 *         user is a superUser
	 */
	public boolean hasRole(String r, boolean ignoreSuperUser) {
		if (ignoreSuperUser == false) {
			if (isSuperUser())
				return true;
		}
		
		if (roles == null)
			return false;
		
		Set<Role> tmproles = getAllRoles();
		
		if (log.isDebugEnabled())
			log.debug("User #" + userId + " has roles: " + tmproles);
		
		Role role = new Role(r);
		
		if (tmproles.contains(role))
			return true;
		
		return false;
	}
	
	/**
	 * Get <i>all</i> privileges this user has. This delves into all of the roles that a person has,
	 * appending unique privileges
	 * 
	 * @return Collection of complete Privileges this user has
	 */
	public Collection<Privilege> getPrivileges() {
		Set<Privilege> privileges = new HashSet<Privilege>();
		Set<Role> tmproles = getAllRoles();
		
		Role role;
		for (Iterator<Role> i = tmproles.iterator(); i.hasNext();) {
			role = i.next();
			Collection<Privilege> privs = role.getPrivileges();
			if (privs != null)
				privileges.addAll(privs);
		}
		
		return privileges;
	}
	
	/**
	 * Compares two objects for similarity This must pass through to the parent object
	 * (org.openhouse.Person) in order to get similarity of person/user objects
	 * 
	 * @param obj
	 * @return boolean true/false whether or not they are the same objects
	 * @see org.openhouse.Person#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	/**
	 * The hashcode for a user/person is used to index the objects in a tree This must pass through
	 * to the parent object (org.openhouse.Person) in order to get similarity of person/user objects
	 * 
	 * @see org.openhouse.Person#hashCode()
	 */
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * Returns all roles attributed to this user by expanding the role list to include the parents
	 * of the assigned roles
	 * 
	 * @return all roles (inherited from parents and given) for this user
	 */
	public Set<Role> getAllRoles() {
		// the user's immediate roles
		Set<Role> baseRoles = new HashSet<Role>();
		
		// the user's complete list of roles including
		// the parent roles of their immediate roles
		Set<Role> totalRoles = new HashSet<Role>();
		if (getRoles() != null) {
			baseRoles.addAll(getRoles());
			totalRoles.addAll(getRoles());
		}
		
		if (log.isDebugEnabled())
			log.debug("User's base roles: " + baseRoles);
		
		try {
			for (Role r : baseRoles) {
				totalRoles.addAll(r.getAllParentRoles());
			}
		}
		catch (ClassCastException e) {
			log.error("Error converting roles for user: " + this);
			log.error("baseRoles.class: " + baseRoles.getClass().getName());
			log.error("baseRoles: " + baseRoles.toString());
			Iterator<Role> iter = baseRoles.iterator();
			while (iter.hasNext()) {
				log.error("baseRole: '" + iter.next() + "'");
			}
		}
		return totalRoles;
	}
	
	/**
	 * Add the given Role to the list of roles for this User
	 * 
	 * @param role
	 * @return Returns this user with the given role attached
	 */
	public User addRole(Role role) {
		if (roles == null)
			roles = new HashSet<Role>();
		if (!roles.contains(role) && role != null)
			roles.add(role);
		
		return this;
	}
	
	/**
	 * Remove the given Role from the list of roles for this User
	 * 
	 * @param role
	 * @return this user with the given role removed
	 */
	public User removeRole(Role role) {
		if (roles != null)
			roles.remove(role);
		
		return this;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the secretQuestion
	 */
	public String getSecretQuestion() {
		return secretQuestion;
	}

	/**
	 * @param secretQuestion the secretQuestion to set
	 */
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	/**
	 * @return the secretAnswer
	 */
	public String getSecretAnswer() {
		return secretAnswer;
	}

	/**
	 * @param secretAnswer the secretAnswer to set
	 */
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	/**
	 * @return the clearTextPassword
	 */
	public String getClearTextPassword() {
		return clearTextPassword;
	}

	/**
	 * @param clearTextPassword the clearTextPassword to set
	 */
	public void setClearTextPassword(String clearTextPassword) {
		this.clearTextPassword = clearTextPassword;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the userProperties
	 */
	public Map<String, String> getUserProperties() {
		return userProperties;
	}

	/**
	 * @param userProperties the userProperties to set
	 */
	public void setUserProperties(Map<String, String> userProperties) {
		this.userProperties = userProperties;
	}
	
	/**
	 * Convenience method. Adds the given property to the user's properties
	 */
	public void setUserProperty(String prop, String value) {
		if (getUserProperties() == null)
			userProperties = new HashMap<String, String>();
		
		userProperties.put(prop, value);
	}
	
	/**
	 * Convenience method. Removes the given property from the user's properties
	 */
	public void removeUserProperty(String prop) {
		if (getUserProperties() != null && userProperties.containsKey(prop))
			userProperties.remove(prop);
	}
	
	/**
	 * Get prop property from this user's properties. If prop is not found in properties, return
	 * empty string
	 * 
	 * @param prop
	 * @return property value
	 */
	public String getUserProperty(String prop) {
		if (getUserProperties() != null && userProperties.containsKey(prop))
			return userProperties.get(prop);
		
		return "";
	}
	
	/**
	 * Get prop property from this user's properties. If prop is not found in properties, return
	 * <code>defaultValue</code>
	 * 
	 * @param prop
	 * @param defaultValue
	 * @return property value
	 * @see #getUserProperty(java.lang.String)
	 */
	public String getUserProperty(String prop, String defaultValue) {
		if (getUserProperties() != null && userProperties.containsKey(prop))
			return userProperties.get(prop);
		
		return defaultValue;
	}

	public String getFullName(){
		String fullName = "";
		
		if(getFirstName() != null)
			fullName += getFirstName()  + " ";
		if(getMiddleName() != null)
			fullName += getMiddleName()  + " ";
		if(getLastName() != null)
			fullName += getLastName();
		
		return fullName;
	}

	/**
	 * Ascertains if the current <code>User</code> 
	 * is the default administrator that ships with the system.
	 * 
	 * @return <code>true if(user is default administrator)</code>
	 */
	public boolean isDefaultAdministrator() {
		return this.username.equalsIgnoreCase("admin");
	}
}
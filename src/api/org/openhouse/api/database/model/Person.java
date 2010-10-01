package org.openhouse.api.database.model;


import java.util.Date;

public class Person extends BaseCommonData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int personId;
	private String personIdentifier;
	private Integer remotePersonId;
	private String remotePersonUuid;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private Date birthdate;
	private Boolean birthdateEstimated = false;
	private Date deathDate;
	private User personCreator;
	private Date personDateCreated;
	private User personChangedBy;
	private Date personDateChanged;
	private Boolean personVoided = false;
	private User personVoidedBy;
	private Date personDateVoided;
	private String personVoidReason;
	
	private boolean isUser;

	public Person() {
	}
	
	public Person(Integer personId) {
		this.personId = personId;
	}

	public Person(int personId, String personIdentifier,
			String remotePersonUuid, Boolean birthdateEstimated, User creator,
			Date dateCreated, Boolean voided) {
		this.personId = personId;
		this.personIdentifier = personIdentifier;
		this.remotePersonUuid = remotePersonUuid;
		this.birthdateEstimated = birthdateEstimated;
		this.personCreator = creator;
		this.personDateCreated = dateCreated;
		this.personVoided = voided;
	}
	
	public Person(Person person) {
		if (person == null)
			return;
		
		personId = person.getPersonId();
		personIdentifier = person.getPersonIdentifier();
		remotePersonId = person.getRemotePersonId();
		remotePersonUuid = person.getRemotePersonUuid();
		firstName = person.getFirstName();
		middleName = person.getMiddleName();
		lastName = person.getLastName();
		
		gender = person.getGender();
		birthdate = person.getBirthdate();
		birthdateEstimated = person.getBirthdateEstimated();
		deathDate = person.getDeathDate();
		
		// base creator/voidedBy/changedBy info is not copied here
		// because that is specific to and will be recreated
		// by the subobject upon save
		
		setPersonCreator(person.getPersonCreator());
		setPersonDateCreated(person.getPersonDateCreated());
		setPersonChangedBy(person.getPersonChangedBy());
		setPersonDateChanged(person.getPersonDateChanged());
		setPersonVoided(person.getPersonVoided());
		setPersonVoidedBy(person.getPersonVoidedBy());
		setPersonDateVoided(person.getPersonDateVoided());
		setPersonVoidReason(person.getPersonVoidReason());
	}

	public int getPersonId() {
		return this.personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getPersonIdentifier() {
		return this.personIdentifier;
	}

	public void setPersonIdentifier(String personIdentifier) {
		this.personIdentifier = personIdentifier;
	}

	public Integer getRemotePersonId() {
		return this.remotePersonId;
	}

	public void setRemotePersonId(Integer remotePersonId) {
		this.remotePersonId = remotePersonId;
	}

	public String getRemotePersonUuid() {
		return this.remotePersonUuid;
	}

	public void setRemotePersonUuid(String remotePersonUuid) {
		this.remotePersonUuid = remotePersonUuid;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Boolean getBirthdateEstimated() {
		return this.birthdateEstimated;
	}

	public void setBirthdateEstimated(Boolean birthdateEstimated) {
		this.birthdateEstimated = birthdateEstimated;
	}

	public Date getDeathDate() {
		return this.deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	/**
	 * @return the personCreator
	 */
	public User getPersonCreator() {
		return personCreator;
	}

	/**
	 * @param personCreator the personCreator to set
	 */
	public void setPersonCreator(User personCreator) {
		this.personCreator = personCreator;
	}

	/**
	 * @return the personDateCreated
	 */
	public Date getPersonDateCreated() {
		return personDateCreated;
	}

	/**
	 * @param personDateCreated the personDateCreated to set
	 */
	public void setPersonDateCreated(Date personDateCreated) {
		this.personDateCreated = personDateCreated;
	}

	/**
	 * @return the personChangedBy
	 */
	public User getPersonChangedBy() {
		return personChangedBy;
	}

	/**
	 * @param personChangedBy the personChangedBy to set
	 */
	public void setPersonChangedBy(User personChangedBy) {
		this.personChangedBy = personChangedBy;
	}

	/**
	 * @return the personDateChanged
	 */
	public Date getPersonDateChanged() {
		return personDateChanged;
	}

	/**
	 * @param personDateChanged the personDateChanged to set
	 */
	public void setPersonDateChanged(Date personDateChanged) {
		this.personDateChanged = personDateChanged;
	}

	/**
	 * @return the personVoided
	 */
	public Boolean getPersonVoided() {
		return personVoided;
	}

	/**
	 * @param personVoided the personVoided to set
	 */
	public void setPersonVoided(Boolean personVoided) {
		this.personVoided = personVoided;
	}

	/**
	 * @return the personVoidedBy
	 */
	public User getPersonVoidedBy() {
		return personVoidedBy;
	}

	/**
	 * @param personVoidedBy the personVoidedBy to set
	 */
	public void setPersonVoidedBy(User personVoidedBy) {
		this.personVoidedBy = personVoidedBy;
	}

	/**
	 * @return the personDateVoided
	 */
	public Date getPersonDateVoided() {
		return personDateVoided;
	}

	/**
	 * @param personDateVoided the personDateVoided to set
	 */
	public void setPersonDateVoided(Date personDateVoided) {
		this.personDateVoided = personDateVoided;
	}

	/**
	 * @return the personVoidReason
	 */
	public String getPersonVoidReason() {
		return personVoidReason;
	}

	/**
	 * @param personVoidReason the personVoidReason to set
	 */
	public void setPersonVoidReason(String personVoidReason) {
		this.personVoidReason = personVoidReason;
	}
	
	/**
	 * @return true/false whether this person is a user or not
	 */
	public boolean isUser() {
		return isUser;
	}
	
	/**
	 * This should only be set by the database layer by looking at whether a row exists in the user
	 * table
	 * 
	 * @param isUser whether this person is a user or not
	 */
	@SuppressWarnings("unused")
	private void setUser(boolean isUser) {
		this.isUser = isUser;
	}
}
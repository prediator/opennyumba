package org.openhouse.api.context;

import org.apache.log4j.Logger;
import org.openhouse.api.service.AdministrationService;
import org.openhouse.api.service.AuthenticationService;
import org.openhouse.api.service.HouseholdService;
import org.openhouse.api.service.LocationService;
import org.openhouse.api.service.ObsService;
import org.openhouse.api.service.PersonService;
import org.openhouse.api.service.SchedulerService;
import org.openhouse.api.service.TaskService;
import org.openhouse.api.service.UserService;

public class ServiceContext {
	
	private static final Logger log = Logger.getLogger(ServiceContext.class);
	
	private static ServiceContext instance;
	
	private SchedulerService schedulerService;
	private UserService userService;
	private TaskService taskService;
	private AuthenticationService authenticationService;
	private HouseholdService householdService;
	private AdministrationService administrationService;
	private LocationService locationService;
	private PersonService personService;
	private ObsService obsService;
	

	/**
	 * The default constructor is private so as to keep only one instance 
	 * per java vm.
	 * 
	 * @see ServiceContext#getInstance()
	 */
	private ServiceContext() { 
		log.debug("Instantiating service context");
	}
	
	/**
	 * There should only be one ServiceContext per openhouse (java virtual machine).
	 * 
	 * This method should be used when wanting to fetch the service context
	 * 
	 * Note: The ServiceContext shouldn't be used independently.  All calls
	 * should go through the Context
	 * 
	 * @return This VM's current ServiceContext.
	 * 
	 */
	public static ServiceContext getInstance() {
		if (instance == null)
			instance = new ServiceContext();
		
		return instance;
	}
	
	public SchedulerService getSchedulerService() {
		return schedulerService;
	}
	
	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	public void setUserService(UserService userService){
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param authenticationService the authenticationService to set
	 */
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/**
	 * @return the authenticationService
	 */
	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * @return
	 */
	public TaskService getTaskService() {
		return this.taskService;
	}
	
	public HouseholdService getHouseholdService() {
		return householdService;
	}

	public void setHouseholdService(HouseholdService householdService) {
		this.householdService = householdService;
	}

	/**
	 * @return the administrationService
	 */
	public AdministrationService getAdministrationService() {
		return administrationService;
	}

	/**
	 * @param administrationService the administrationService to set
	 */
	public void setAdministrationService(AdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	/**
	 * @return the locationService
	 */
	public LocationService getLocationService() {
		return locationService;
	}

	/**
	 * @param locationService the locationService to set
	 */
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	/**
	 * 
	 * @return
	 */
	public PersonService getPersonService() {
		return personService;
	}
	/**
	 * 
	 * @param personService
	 */

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public ObsService getObsService() {
		return obsService;
	}

	public void setObsService(ObsService obsService) {
		this.obsService = obsService;
	}


	
}

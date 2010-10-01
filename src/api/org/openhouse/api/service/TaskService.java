/**
 * 
 */
package org.openhouse.api.service;

import java.util.List;

import org.openhouse.api.database.model.TaskDef;
import org.openhouse.api.exception.OpenHouseException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service is used for 
 * managing <code>Tasks</code>.
 * 
 * @author Samuel Mbugua
 *
 */
@Transactional
public interface TaskService {
	
	/**
	 * Gets a list of task definitions from the database.
	 * 
	 * @return the task list.
	 * @throws OpenHouseException 
	 */
	@Transactional(readOnly=true)
	List<TaskDef> getTasks() throws OpenHouseException;
	
	/**
	 * Saves a task definition to the database.
	 * 
	 * @param task the task definition to save.
	 * @throws OpenHouseException
	 */
	void saveTask(TaskDef taskDef) throws OpenHouseException;
	
	/**
	 * Deletes a task definition from the database.
	 * 
	 * @param task the task definition to delete.
	 * @throws OpenHouseException 
	 */
	void deleteTask(TaskDef taskDef) throws OpenHouseException;
	
	/**
	 * Starts running a given task.
	 * 
	 * @param taskDef the task definition.
	 * @return true if the tasks starts successfully, else false.
	 */
	@Transactional(readOnly=true)
	Boolean startTask(TaskDef taskDef) throws OpenHouseException ;
	
	/**
	 * Stops a task from running.
	 * 
	 * @param taskDef the task definition object.
	 * @return true if the tasks starts successfully, else false.
	 */
	@Transactional(readOnly=true)
	Boolean stopTask(TaskDef taskDef) throws OpenHouseException;

}

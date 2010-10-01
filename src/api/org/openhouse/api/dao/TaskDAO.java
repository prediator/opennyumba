/**
 * 
 */
package org.openhouse.api.dao;

import java.util.List;

import org.openhouse.api.database.model.TaskDef;
import org.openhouse.api.exception.OpenHouseException;

/**
 * Provides data access 
 * services to the <code>Task service</code>.
 * 
 * @author Samuel Mbugua
 *
 */
public interface TaskDAO {

	/**
	 * Gets a list of task definitions from the database.
	 * 
	 * @return the task list.
	 */
	List<TaskDef> getTasks() throws OpenHouseException;
	
	/**
	 * Saves a task definition to the database.
	 * 
	 * @param taskDef the task definition to save.
	 * @throws Exception
	 */
	void saveTask(TaskDef taskDef) throws OpenHouseException;
	
	/**
	 * Deletes a task definition from the database.
	 * 
	 * @param taskDef the task definition to delete.
	 */
	void deleteTask(TaskDef taskDef) throws OpenHouseException;
	
	/**
	 * Retrieves a task definition given the identifying name
	 * @param taskName String name of the task
	 * @return TaskDef found, null if no task found
	 * @throws OpenHouseException
	 */
	TaskDef getTask(String taskName) throws OpenHouseException;
	
}

package org.openhouse;

import org.openhouse.api.database.model.TaskDef;

/**
 * This interface should be implemented by all schedulable tasks to enable
 * the scheduler framework manage tasks.
 * 
 * @author Samuel Mbugua
 *
 */
public interface Task {

	/**
	 * Initializes the task before it is run.
	 * 
	 * @param taskDef the task definition object.
	 */
	public void init(TaskDef taskDef);
	
	/**
	 * Stops the task from running.
	 */
	public void stop();
	
	/**
	 * Gets the task definition for the task.
	 * 
	 * @return the task definition.
	 */
	public TaskDef getTaskDef();
	
	/**
	 * Checks if a task is running.
	 * 
	 * @return true if running, else false.
	 */
	public boolean isRunning();
}

/**
 * 
 */
package org.openhouse.api.service.impl;

import java.util.List;

import org.openhouse.api.context.Context;
import org.openhouse.api.dao.TaskDAO;
import org.openhouse.api.database.model.TaskDef;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.service.SchedulerService;
import org.openhouse.api.service.TaskService;

/**
 * Default implementation for <code>Task Service</code>.
 * 
 * @author Samuel Mbugua
 *
 */
public class TaskServiceImpl implements TaskService {

	/** The data access object. */
	private TaskDAO dao;


	/**
	 * Creates a new instance of the Task Service.
	 */
	public TaskServiceImpl(){}

	public TaskServiceImpl(TaskDAO dao) {
		super();
		this.dao = dao;
	}

	public TaskDAO getDao() {
		return dao;
	}

	public void setDao(TaskDAO dao) {
		this.dao = dao;
	}

	
	public void deleteTask(TaskDef taskDef) throws OpenHouseException {
		dao.deleteTask(taskDef);
		
	}

	public List<TaskDef> getTasks() throws OpenHouseException {		
		List<TaskDef> taskDefs = dao.getTasks();
		if(taskDefs != null){
			SchedulerService service = Context.getSchedulerService();
			for(TaskDef taskDef : taskDefs)
				taskDef.setRunning(service.isTaskRunning(taskDef));
		}
		return taskDefs;
	}
	
	public void saveTask(TaskDef taskDef) throws OpenHouseException {
		dao.saveTask(taskDef);
		
	}

	public Boolean startTask(TaskDef taskDef) {
		return Context.getSchedulerService().startTask(taskDef);
	}

	public Boolean stopTask(TaskDef taskDef) {
		return Context.getSchedulerService().stopTask(taskDef);
	}

}

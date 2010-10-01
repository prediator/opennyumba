package org.openhouse.api.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.openhouse.Task;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.TaskDef;
import org.openhouse.api.service.SchedulerService;
import org.openhouse.api.service.TaskService;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;



/**
 * Default implementation for scheduler service.
 * 
 * @author Samuel Mbugua
 *
 */
public class SchedulerServiceImpl implements SchedulerService{

	/** The quart scheduler. */
	private Scheduler scheduler;
	
	/** Map of tasks keyed by their task identifiers. */
	private Hashtable<Integer, Task> tasks = new Hashtable<Integer, Task>();

	
	/**
	 * Creates a new instance of the scheduler service.
	 */
	public SchedulerServiceImpl(){
		try{
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * @see org.openhouse.api.service.SchedulerService#start()
	 */
	public void start(){
		try{
			if(scheduler == null)
				return;

			TaskService taskService = Context.getTaskService();

			List<TaskDef> taskDefs = taskService.getTasks();

			if (taskDefs != null) {
				for(TaskDef taskDef : taskDefs)
					scheduleTask(taskDef);
			}

			scheduler.start();
			System.out.println("Started Scheduling Service...............");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * @see org.openhouse.api.service.SchedulerService#stop()
	 */
	public void stop(){
		try{
			if(scheduler != null)
				scheduler.shutdown();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * @see org.openhouse.api.service.SchedulerService#startTask(org.openhouse.api.database.model.TaskDef)
	 */
	public boolean startTask(TaskDef taskDef){
		return scheduleTask(taskDef);
	}

	/**
	 * @see org.openhouse.api.service.SchedulerService#stopTask(org.openhouse.api.database.model.TaskDef)
	 */
	public boolean stopTask(TaskDef taskDef){
		Task task = getTask(taskDef);
		if(task == null){
			System.out.println("Attempted to stop a non registered task");
			return false;
		}

		task.stop();

		try{
			scheduler.unscheduleJob(taskDef.getName(),"DEFAULT");
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.openhouse.api.service.SchedulerService#stopTask(org.openhouse.api.Task)
	 */

	public void registerTaskRunningInstance(Task task){
		//tasks.put(task.getTaskDef().getTaskId(),task);
	}

	/**
	 * @see org.openhouse.api.service.SchedulerService#isTaskRunning(org.openhouse.api.database.model.TaskDef)
	 */
	public boolean isTaskRunning(TaskDef taskDef){
		Task task = getTask(taskDef);
		if(task == null)
			return false;
		
		return task.isRunning();
	}
	
	/**
	 * Schedules a task as identified by the task definition object.
	 * 
	 * @param taskDef the task definition object.
	 * @return true if the task was scheduled successfully, else false.
	 */
	private boolean scheduleTask(TaskDef taskDef){
		try{
			Class<?> cls = getClass(taskDef);
			if(cls == null){
				System.out.println("Service class not found for: " + taskDef.getName());
				return false;
			}

			JobDetail jobDetail = new JobDetail();
			jobDetail.setJobClass(cls);
			jobDetail.setName(taskDef.getName());

			JobDataMap dataMap = new JobDataMap();
			dataMap.put("taskdef", taskDef);
			jobDetail.setJobDataMap(dataMap);

			Trigger trigger = null;
			String cronExpression = taskDef.getCronExpression();
			if(cronExpression != null && cronExpression.trim().length() > 0 && !taskDef.isStartOnStartup()){
				trigger = new CronTriggerBean();
				((CronTriggerBean)trigger).setJobDetail(jobDetail);
				((CronTriggerBean)trigger).setCronExpression(cronExpression); // /*"1 * * * * ?"*/
			}
			else if(taskDef.isStartOnStartup())
				trigger = TriggerUtils.makeImmediateTrigger(0, 0);
			else
				System.out.println("Task " + taskDef.getName() + " not scheduled..........");

			if(trigger != null){
				trigger.setName(taskDef.getName());
				scheduler.scheduleJob(jobDetail,trigger);
				return true;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Gets the Class responsible for running a task identified by the task definition.
	 * 
	 * @param taskDef the task definition.
	 * @return the Class object for the task class.
	 */
	private Class<?> getClass(TaskDef taskDef){
		try{
			
			return Class.forName(taskDef.getTaskClass());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the task identified by a task definition object.
	 * 
	 * @param taskDef the task definition object.
	 * @return the task object.
	 */
	private Task getTask(TaskDef taskDef){
		return tasks.get(taskDef.getTaskId());
	}
}


package org.openhouse.api.dao.hibernate;

import java.util.List;

import org.openhouse.api.dao.TaskDAO;
import org.openhouse.api.database.model.TaskDef;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.util.HibernateUtil;
import org.springframework.security.annotation.Secured;

/**
 * Provides a hibernate implementation
 * of the <code>TaskDAO</code> data access <code> interface.</code>
 * 
 * @author Samuel Mbugua
 *
 */
public class HibernateTaskDAO implements TaskDAO {

	public HibernateTaskDAO() {}
	

	@Secured("Perm_Delete_Tasks")
	public void deleteTask(TaskDef task) throws OpenHouseException {
		HibernateUtil.deleteItem(task);
	}

	@SuppressWarnings("unchecked")
	public List<TaskDef> getTasks() throws OpenHouseException {
		return (List<TaskDef>) HibernateUtil.getItems("TaskDef order by name");
	}

	@Secured("Perm_Add_Tasks")
	public void saveTask(TaskDef task) throws OpenHouseException {
		HibernateUtil.saveItem(task);
	}
	
	@SuppressWarnings("unchecked")
	@Secured("Perm_View_Tasks")
	public TaskDef getTask(String taskName) throws OpenHouseException {
	    List<TaskDef> tasks = (List<TaskDef>) HibernateUtil.getItems("TaskDef", "name", taskName);
	    if (tasks != null && tasks.size() > 0) {
	        return tasks.get(0);
	    } else {
	        return null;
	    }
	}

}

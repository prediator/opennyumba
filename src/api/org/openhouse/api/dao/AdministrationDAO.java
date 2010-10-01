package org.openhouse.api.dao;

import java.util.List;

import org.openhouse.api.database.model.GlobalProperty;
import org.openhouse.api.exception.DAOException;

/**
 * Database methods for the AdministrationService
 * 
 */
public interface AdministrationDAO {
	
	public String getGlobalProperty(String propertyName) throws DAOException;
	
	public GlobalProperty getGlobalPropertyObject(String propertyName);
	
	public List<GlobalProperty> getAllGlobalProperties() throws DAOException;
	
	public GlobalProperty getGlobalPropertyByUuid(String uuid) throws DAOException;
	
	public List<GlobalProperty> getGlobalPropertiesByPrefix(String prefix);
	
	public List<GlobalProperty> getGlobalPropertiesBySuffix(String suffix);
	
	public void deleteGlobalProperty(GlobalProperty gp) throws DAOException;
	
	public GlobalProperty saveGlobalProperty(GlobalProperty gp) throws DAOException;
	
	
}

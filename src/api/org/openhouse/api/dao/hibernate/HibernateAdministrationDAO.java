package org.openhouse.api.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openhouse.api.dao.AdministrationDAO;
import org.openhouse.api.database.model.GlobalProperty;
import org.openhouse.api.exception.DAOException;

/**
 * Hibernate specific database methods for the AdministrationService
 */
public class HibernateAdministrationDAO implements AdministrationDAO {
	
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	public HibernateAdministrationDAO() {
	}
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#getGlobalProperty(java.lang.String)
	 */
	public String getGlobalProperty(String propertyName) throws DAOException {
		GlobalProperty gp = (GlobalProperty) sessionFactory.getCurrentSession().get(GlobalProperty.class, propertyName);
		
		// if no gp exists, return a null value
		if (gp == null)
			return null;
		
		return gp.getPropertyValue();
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#getGlobalPropertyObject(java.lang.String)
	 */
	public GlobalProperty getGlobalPropertyObject(String propertyName) {
		GlobalProperty gp = (GlobalProperty) sessionFactory.getCurrentSession().get(GlobalProperty.class, propertyName);
		
		// if no gp exists, hibernate returns a null value
		
		return gp;
	}
	
	public GlobalProperty getGlobalPropertyByUuid(String uuid) throws DAOException {
		GlobalProperty gp = (GlobalProperty) sessionFactory.getCurrentSession().createQuery(
		    "from GlobalProperty t where t.uuid = :uuid").setString("uuid", uuid).uniqueResult();
		
		return gp;
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#getAllGlobalProperties()
	 */
	@SuppressWarnings("unchecked")
	public List<GlobalProperty> getAllGlobalProperties() throws DAOException {
		return sessionFactory.getCurrentSession().createCriteria(GlobalProperty.class).list();
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#getGlobalPropertiesByPrefix(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<GlobalProperty> getGlobalPropertiesByPrefix(String prefix) {
		return sessionFactory.getCurrentSession().createCriteria(GlobalProperty.class).add(
		    Restrictions.like("property", prefix, MatchMode.START)).list();
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#getGlobalPropertiesBySuffix(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<GlobalProperty> getGlobalPropertiesBySuffix(String suffix) {
		return sessionFactory.getCurrentSession().createCriteria(GlobalProperty.class).add(
		    Restrictions.like("property", suffix, MatchMode.END)).list();
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#deleteGlobalProperty(GlobalProperty)
	 */
	public void deleteGlobalProperty(GlobalProperty property) throws DAOException {
		sessionFactory.getCurrentSession().delete(property);
	}
	
	/**
	 * @see org.openhouse.api.db.AdministrationDAO#saveGlobalProperty(org.openhouse.GlobalProperty)
	 */
	public GlobalProperty saveGlobalProperty(GlobalProperty gp) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(gp);
		return gp;
	}
	
	
}

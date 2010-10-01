package org.openhouse.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhouse.api.context.Context;
import org.openhouse.api.exception.OpenHouseException;


/**
 * Hibernate data access utilities.
 * 
 * @author Samuel Mbugua
 *
 */
public class HibernateUtil {

	/** Reference to the hibernate session factory. */
	private static SessionFactory sessionFactory;
	
	/** The logger*/
	private static Logger log = Logger.getLogger(HibernateUtil.class);
	
	
	/**
	 * Gets the hibernate session factory.
	 * @return a hibernate session factory.
	 */
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	/**
	 * Sets the hibernate session factory.
	 * 
	 * @param sesnFactory the session factory to set.
	 */
	public void setSessionFactory(SessionFactory sesnFactory) {
		sessionFactory = sesnFactory;
	}
	
	/**
	 * Deletes an object from the database.
	 * 
	 * @param item the object to delete.
	 */
	public static void deleteItem(Object item) {	
		Session session = sessionFactory.getCurrentSession();
		session.delete(item);
	}

	/**
	 * Gets a list of objects from the database.
	 * 
	 * @param name the name of the class, without package, for the items to get from the database.
	 * @return a list of the objects.
	 */
	public static List<?> getItems(String name) {
		try{
			Session session = sessionFactory.getCurrentSession();
			
			List<?> items = session.createQuery("from "+ name).list();
			return items;
		}
		catch(Exception ex){			
			log.error("Caught server side error in HibernateUtil.getItems() :" + ex.getLocalizedMessage(), ex);
			Context.closeSession();
		}
		return null;
	}

	/**
	 * Saves an object to the database.
	 * 
	 * @param item the object to save.
	 * @throws Exception 
	 */
	public static void saveItem(Object item) {
		try {
			saveItem(item,false);
		} catch (Exception e) {
			log.error("Caught server side error in saveItem() :" + e.getLocalizedMessage(), e);
			Context.closeSession();
		}
	}
	
	/**
	 * Saves as object to the database, with a flag which tells whether to begin a
	 * new transaction before saving.
	 * 
	 * @param item the object to save.
	 * @param inTransaction set to true to begin a new transaction before saving.
	 * @throws OpenHouseException Any exception that might occur.
	 */
	public static void saveItem(Object item, boolean inTransaction) throws OpenHouseException{
		try{
			Session session = sessionFactory.getCurrentSession();
			
			if(inTransaction)
				session.beginTransaction();
			
			try{
				session.saveOrUpdate(item);
			}
			catch(NonUniqueObjectException e){
				session.merge(item);
			}
			
			if(inTransaction)
				session.getTransaction().commit();
		}
		catch(Exception ex){
			log.error("Caught server side exception in saveItem() :" + ex.getLocalizedMessage(), ex);
			Context.closeSession();
		}
	}
	
	/**
	 * Gets a list of objects, that match a particular field value, from the database.
	 * 
	 * @param className the name of the class, without package, for the objects to fetch.
	 * @param fieldName the name of the field whose value to match.
	 * @param id the value of the field to match.
	 * @return the object list.
	 */
	public static List<?> getItems(String className, String fieldName, int id){
		
		String sql = "select * from " + className + " where " + fieldName + " = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(sql).setInteger("id",id);
		
		return q.list();
	}
	
	/**
	 * Gets a list of objects, that match a particular field value, from the database.
     * @param className the name of the class, without package, for the objects to fetch.
     * @param fieldName the name of the field whose value to match.
	 * @param fieldValue the value of the matching field
	 * @return the object list.
	 */
	public static List<?> getItems(String className, String fieldName, Object fieldValue) {
	    String sql = "from " + className + " where " + fieldName + " = :fieldValue";
	    Query q = sessionFactory.getCurrentSession().createQuery(sql).setParameter("fieldValue", fieldValue);
	    return q.list();
	}
}

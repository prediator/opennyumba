/**
 * 
 */
package org.openhouse.api.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.openhouse.api.dao.UserDAO;
import org.openhouse.api.database.model.Privilege;
import org.openhouse.api.database.model.Role;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.util.HibernateUtil;

/**
 * Provides a hibernate implementation
 * of the <code>UserDAO</code> data access <code> interface.</code>
 * 
 * @author Samuel Mbugua
 *
 */
public class HibernateUserDAO implements UserDAO {

	/**
	 * The hibernate session factory.
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Set session factory.
	 * 
	 * @param sessionFactory sessionFactory to set.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void deleteUser(User user) throws OpenHouseException {
		HibernateUtil.deleteItem(user);
	}

	public User getUser(String username) throws OpenHouseException {
    
	    User user = (User)sessionFactory.getCurrentSession().createCriteria(User.class).
	    add(Expression.eq("username",username)).uniqueResult();
	    
	    return user;
	}
	
	public User getUser(Integer userId) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
		
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() throws OpenHouseException {
		return (List<User>) HibernateUtil.getItems("User order by username");
	}

	public void saveUser(User user) throws OpenHouseException {		
		HibernateUtil.saveItem(user);
	}

	@SuppressWarnings("unchecked")
	public List<Privilege> getAllPrivileges() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Privilege.class);
		return criteria.list();
	}

	@Override
	public List<Role> getAllRoles() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
		return criteria.list();
	}

	@Override
	public void savePrivilege(Privilege p) {
		HibernateUtil.saveItem(p);
		
	}

	@Override
	public void saveRole(Role role) {
		HibernateUtil.saveItem(role);
		
	}
}

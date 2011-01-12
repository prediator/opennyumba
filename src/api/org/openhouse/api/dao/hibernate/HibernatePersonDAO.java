package org.openhouse.api.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.openhouse.api.dao.PersonDAO;
import org.openhouse.api.database.model.GroupMembership;
import org.openhouse.api.database.model.GroupMembershipType;
import org.openhouse.api.database.model.Person;

public class HibernatePersonDAO implements PersonDAO{
	
	protected final static Log log = LogFactory.getLog(HibernatePersonDAO.class);
	
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public Person getPerson(int id) {
		return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
	}
	@Override
	public void purgePerson(Person person) {
		sessionFactory.getCurrentSession().delete(person);
		
	}
	@Override
	public void savePerson(Person person) {
		sessionFactory.getCurrentSession().saveOrUpdate(person);
	}
	@Override
	public List<Person> getAllPersons() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
		return criteria.list();
	}
	@Override
	public List<GroupMembership> getGroupMembership(boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GroupMembership.class);
		return criteria.list();
	}
	@Override
	public GroupMembership getGroupMembership(GroupMembership groupMemebership) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<GroupMembershipType> getGroupMembershiptype(
			boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GroupMembershipType getGroupMembershiptype(int groupMembershipTypeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GroupMembership saveGroupMembership(GroupMembership groupMembership) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GroupMembershipType saveGroupMembershiptype(
			GroupMembershipType groupMembershipType) {
		// TODO Auto-generated method stub
		return null;
	}

}

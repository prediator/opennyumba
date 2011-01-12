package org.openhouse.api.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openhouse.api.dao.HouseholdDAO;
import org.openhouse.api.database.model.Group;
import org.openhouse.api.database.model.GroupMap;
import org.openhouse.api.database.model.GroupMapType;
import org.openhouse.api.database.model.GroupType;

public class HibernateHouseholdDAO implements HouseholdDAO{
	
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
	public List<GroupType> getAllGroupTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getAllHouseholds(boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveHousehold(Group group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveGroupMap(GroupMap groupMap) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveGroupType(GroupType groupType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<GroupMap> getAllGroupMaps(boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupType> getAllGroupTypes(boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupType getGroupMap(int groupMapId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupMapType> getAllGroupMapTypes(boolean includeVoided) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupType getGroupMapType(int groupMapTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupType getGroupType(int groupType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getHousehold(int householdId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveGroupMapType(GroupMapType groupMapType) {
		// TODO Auto-generated method stub
		
	}

}

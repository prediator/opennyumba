package org.openhouse.api.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.dao.HouseholdDAO;
import org.openhouse.api.database.model.Group;
import org.openhouse.api.database.model.GroupMap;
import org.openhouse.api.database.model.GroupMapType;
import org.openhouse.api.database.model.GroupType;
import org.openhouse.api.service.HouseholdService;

public class HouseholdServiceImpl implements HouseholdService {
	private static Log log = LogFactory.getLog(HouseholdServiceImpl.class);

	private HouseholdDAO dao;

	public HouseholdServiceImpl() {
	}

	public void setDao(HouseholdDAO dao) {
		this.dao = dao;
	}

	
	@Override
	public List<Group> getAllHouseholds(boolean includeVoided) {
		return this.dao.getAllHouseholds(includeVoided);
	}

	@Override
	public void saveHousehold(Group group) {
		this.dao.saveHousehold(group);
		
	}

	@Override
	public void saveGroupMap(GroupMap groupMap) {
		this.dao.saveGroupMap(groupMap);
		
	}

	@Override
	public void saveGroupType(GroupType groupType) {
		this.dao.saveGroupType(groupType);
		
	}

	@Override
	public List<GroupMapType> getAllGroupMapTypes(boolean includeVoided) {
		return this.dao.getAllGroupMapTypes(includeVoided);
	}

	@Override
	public List<GroupMap> getAllGroupMaps(boolean includeVoided) {
		return this.dao.getAllGroupMaps(includeVoided);
	}

	@Override
	public List<GroupType> getAllGroupTypes(boolean includeVoided) {
		return this.dao.getAllGroupTypes(includeVoided);
	}

	@Override
	public GroupType getGroupMap(int groupMapId) {
		return this.dao.getGroupMap(groupMapId);
	}

	@Override
	public GroupType getGroupMapType(int groupMapTypeId) {
		return this.dao.getGroupMapType(groupMapTypeId);
	}

	@Override
	public GroupType getGroupType(int groupType) {
		return this.dao.getGroupType(groupType);
	}

	@Override
	public Group getHousehold(int householdId) {
		// TODO Auto-generated method stub
		return this.dao.getHousehold(householdId);
	}

	@Override
	public void saveGroupMapType(GroupMapType groupMapType) {
		this.dao.saveGroupMapType(groupMapType);
		
	}

}

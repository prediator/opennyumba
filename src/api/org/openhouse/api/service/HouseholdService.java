package org.openhouse.api.service;

import java.util.List;

import org.openhouse.api.database.model.Group;
import org.openhouse.api.database.model.GroupMap;
import org.openhouse.api.database.model.GroupMapType;
import org.openhouse.api.database.model.GroupType;


public interface HouseholdService {
	
	public void saveHousehold(Group group);
	public Group getHousehold(int householdId);
	public List<Group>getAllHouseholds(boolean includeVoided);
	
	
	public void saveGroupType(GroupType groupType);
	public GroupType getGroupType(int groupType);
	public List<GroupType>getAllGroupTypes(boolean includeVoided);
	
	public void saveGroupMap(GroupMap groupMap);
	public GroupType getGroupMap(int groupMapId);
	public List<GroupMap> getAllGroupMaps(boolean includeVoided);
	
	public void saveGroupMapType(GroupMapType groupMapType);
	public GroupType getGroupMapType(int groupMapTypeId);
	public List<GroupMapType> getAllGroupMapTypes(boolean includeVoided);

	
}

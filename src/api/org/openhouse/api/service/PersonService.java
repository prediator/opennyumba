package org.openhouse.api.service;

import java.util.List;

import org.openhouse.api.database.model.GroupMembership;
import org.openhouse.api.database.model.GroupMembershipType;
import org.openhouse.api.database.model.Person;

public interface PersonService {
	
	public void savePerson(Person person);
	public Person getPerson(int id);
	public List<Person>getAllPersons();
	public void purgePerson(Person person);
	
	public List<GroupMembership>getGroupMembership(boolean includeVoided);
	public GroupMembership saveGroupMembership(GroupMembership groupMembership);
	public GroupMembership getGroupMembership(GroupMembership groupMemebership);
	
	public GroupMembershipType saveGroupMembershiptype(GroupMembershipType groupMembershipType);
	public List<GroupMembershipType> getGroupMembershiptype(boolean includeVoided);
	public GroupMembershipType getGroupMembershiptype(int groupMembershipTypeId);
	

}

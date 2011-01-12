package org.openhouse.api.dao;

import java.util.List;

import org.openhouse.api.database.model.GroupMembership;
import org.openhouse.api.database.model.GroupMembershipType;
import org.openhouse.api.database.model.Person;

public interface PersonDAO {

	public Person getPerson(int id);

	public void purgePerson(Person person);

	public void savePerson(Person person);

	public List<Person> getAllPersons();

	public List<GroupMembership> getGroupMembership(boolean includeVoided);

	public GroupMembership getGroupMembership(GroupMembership groupMemebership);

	public List<GroupMembershipType> getGroupMembershiptype(
			boolean includeVoided);

	public GroupMembershipType getGroupMembershiptype(int groupMembershipTypeId);

	public GroupMembership saveGroupMembership(GroupMembership groupMembership);

	public GroupMembershipType saveGroupMembershiptype(GroupMembershipType groupMembershipType);


}

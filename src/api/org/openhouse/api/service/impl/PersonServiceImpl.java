package org.openhouse.api.service.impl;

import java.util.List;

import org.openhouse.api.dao.PersonDAO;
import org.openhouse.api.database.model.GroupMembership;
import org.openhouse.api.database.model.GroupMembershipType;
import org.openhouse.api.database.model.Person;
import org.openhouse.api.service.PersonService;

public class PersonServiceImpl implements PersonService{
	private PersonDAO dao;

	public void setDao(PersonDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<Person> getAllPersons() {
		return this.dao.getAllPersons();
	}

	@Override
	public Person getPerson(int id) {
		return this.dao.getPerson(id);
	}

	@Override
	public void purgePerson(Person person) {
		this.dao.purgePerson(person);
		
	}

	@Override
	public void savePerson(Person person) {
		this.dao.savePerson(person);
		
	}

	@Override
	public List<GroupMembership> getGroupMembership(boolean includeVoided) {
		return this.dao.getGroupMembership(includeVoided);
	}

	@Override
	public GroupMembership getGroupMembership(GroupMembership groupMemebership) {
		return this.dao.getGroupMembership(groupMemebership);
	}

	@Override
	public List<GroupMembershipType> getGroupMembershiptype(
			boolean includeVoided) {
		return this.dao.getGroupMembershiptype(includeVoided);
	}

	@Override
	public GroupMembershipType getGroupMembershiptype(int groupMembershipTypeId) {
		return this.dao.getGroupMembershiptype(groupMembershipTypeId);
	}

	@Override
	public GroupMembership saveGroupMembership(GroupMembership groupMembership) {
		return this.dao.saveGroupMembership(groupMembership);
	}

	@Override
	public GroupMembershipType saveGroupMembershiptype(
			GroupMembershipType groupMembershipType) {
		return this.dao.saveGroupMembershiptype(groupMembershipType);
	}

}

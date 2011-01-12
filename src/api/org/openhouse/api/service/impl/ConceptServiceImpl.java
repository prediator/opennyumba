package org.openhouse.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.openhouse.api.context.Context;
import org.openhouse.api.dao.ConceptDAO;
import org.openhouse.api.dao.HouseholdDAO;
import org.openhouse.api.database.model.Concept;
import org.openhouse.api.database.model.ConceptAnswer;
import org.openhouse.api.database.model.ConceptClass;
import org.openhouse.api.database.model.ConceptComplex;
import org.openhouse.api.database.model.ConceptDatatype;
import org.openhouse.api.database.model.ConceptName;
import org.openhouse.api.database.model.ConceptNameTag;
import org.openhouse.api.database.model.ConceptNumeric;
import org.openhouse.api.database.model.ConceptSet;
import org.openhouse.api.exception.APIException;
import org.openhouse.api.exception.ConceptNameInUseException;
import org.openhouse.api.exception.ConceptsLockedException;
import org.openhouse.api.service.ConceptService;
import org.openhouse.util.LocaleUtility;
import org.openhouse.util.OpenhouseConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

public class ConceptServiceImpl implements ConceptService {
	private ConceptDAO dao;
	
	private static Concept trueConcept;
	
	private static Concept falseConcept;
	

	public ConceptServiceImpl() {
		super();
	}
	
	

	public ConceptDAO getDao() {
		return dao;
	}



	public void setDao(ConceptDAO dao) {
		this.dao = dao;
	}

	public void checkIfLocked() throws ConceptsLockedException {
		String locked = Context.getAdministrationService().getGlobalProperty(
		    OpenhouseConstants.GLOBAL_PROPERTY_CONCEPTS_LOCKED, "false");
		if (locked.toLowerCase().equals("true"))
			throw new ConceptsLockedException();
	}

	@Override
	public List<ConceptClass> getAllConceptClasses() throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConceptClass> getAllConceptClasses(boolean includeRetired)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConceptDatatype> getAllConceptDatatypes() throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConceptDatatype> getAllConceptDatatypes(boolean includeRetired)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concept> getAllConcepts() throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concept> getAllConcepts(String sortBy, boolean asc,
			boolean includeRetired) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept getConcept(Integer conceptId) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept getConcept(String conceptIdOrName) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptAnswer getConceptAnswerByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept getConceptByName(String name) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept getConceptByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptClass getConceptClass(Integer conceptClassId)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptClass getConceptClassByName(String name) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptClass getConceptClassByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptComplex getConceptComplex(Integer conceptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptDatatype getConceptDatatype(Integer i) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptDatatype getConceptDatatypeByName(String name)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptDatatype getConceptDatatypeByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptName getConceptName(Integer conceptNameId)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptName getConceptNameByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptNameTag getConceptNameTagByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptNumeric getConceptNumeric(Integer conceptId)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptNumeric getConceptNumericByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptSet getConceptSetByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConceptSet> getConceptSetsByConcept(Concept concept)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concept> getConceptsByAnswer(Concept concept)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concept> getConceptsByClass(ConceptClass cc)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concept> getConceptsByConceptSet(Concept concept)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concept> getConceptsByName(String name) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept getNextConcept(Concept concept) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept getPrevConcept(Concept concept) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConceptSet> getSetsContainingConcept(Concept concept)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasAnyObservation(ConceptName conceptName) throws APIException {
		List<ConceptName> conceptNames = new Vector<ConceptName>();
		conceptNames.add(conceptName);
		Integer count = Context.getObsService().getObservationCount(conceptNames, true);
		return count > 0;
	}

	@Override
	public void purgeConcept(Concept concept) throws APIException {
		checkIfLocked();
		
		if (concept.getConceptId() != null) {
			for (ConceptName conceptName : concept.getNames()) {
				if (hasAnyObservation(conceptName))
					throw new ConceptNameInUseException("Can't delete concept with id : " + concept.getConceptId()
					        + " because it has a name '" + conceptName.getName()
					        + "' which is being used by some observation(s)");
			}
		}
		
		dao.purgeConcept(concept);
	}

	@Override
	public void purgeConceptClass(ConceptClass cc) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purgeConceptDatatype(ConceptDatatype cd) throws APIException {
		// TODO Auto-generated method stub

	}
	/*
	 * 
	 */
	@Override
	public boolean hasAnyObservation(Concept concept) {
		List<Concept> concepts = new Vector<Concept>();
		concepts.add(concept);
		Integer count = Context.getObsService().getObservationCount(null, null, concepts, null, null, null, null, null,
		    null, true);
		return count > 0;
	}

	@Override
	public Concept retireConcept(Concept conceptOrConceptNumeric, String reason)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concept saveConcept(Concept concept) throws APIException {
		return null;
	}

	@Override
	public ConceptClass saveConceptClass(ConceptClass cc) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptDatatype saveConceptDatatype(ConceptDatatype cd)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConceptDAO(ConceptDAO dao) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateConceptSetDerived(Concept concept) throws APIException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateConceptSetDerived() throws APIException {
		// TODO Auto-generated method stub

	}

}

package org.openhouse.api.dao;

import org.openhouse.api.database.model.Concept;

public interface ConceptDAO {

	Concept saveConcept(Concept concept);

	void purgeConcept(Concept concept);

}

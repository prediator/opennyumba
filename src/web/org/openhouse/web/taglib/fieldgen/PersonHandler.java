package org.openhouse.web.taglib.fieldgen;

import org.openhouse.api.database.model.Person;

public class PersonHandler extends AbstractFieldGenHandler implements FieldGenHandler {
	
	private String defaultUrl = "patient.field";
	
	public void run() {
		setUrl(defaultUrl);
		// the following lines are adapted from UserHandler:
		checkEmptyVal((Person) null);
		if (fieldGenTag != null) {
			Object initialValue = this.fieldGenTag.getVal();
			setParameter("initialValue", initialValue == null ? "" : initialValue);
		}
	}
}

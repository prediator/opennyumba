package org.openhouse.api.database;

import liquibase.ChangeSet;


/**
 * @author Samuel Mbugua
 *
 */
public interface ChangeSetExecutorCallback {
	
	/**
	 * This method is called after each changeset is executed.
	 * 
	 * @param changeSet the liquibase changeset that was just run
	 * @param numChangeSetsToRun the total number of changesets in the current file
	 */
	public void executing(ChangeSet changeSet, int numChangeSetsToRun);
}

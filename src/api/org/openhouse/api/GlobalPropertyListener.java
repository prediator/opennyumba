package org.openhouse.api;

import org.openhouse.api.database.model.GlobalProperty;

/**
 * This interface allows code to be run when global properties are created, edited, or deleted. <br/>
 * TODO: Make sure listeners are notified if a global property's name is changed.
 * 
 */
public interface GlobalPropertyListener {
	
	/**
	 * Asks this listener whether it wants to be notified about the given property name
	 * 
	 * @param propertyName
	 * @return whether this listener wants its action methods to be notified of properties with the
	 *         given name
	 */
	public boolean supportsPropertyName(String propertyName);
	
	/**
	 * Called after a global property is created or updated
	 * 
	 * @param newValue the new value of the property that was just saved
	 */
	public void globalPropertyChanged(GlobalProperty newValue);
	
	/**
	 * Called after a global property is deleted
	 * 
	 * @param propertyName the name of the property that was just deleted
	 */
	public void globalPropertyDeleted(String propertyName);
	
}

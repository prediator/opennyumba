package org.openhouse.web.taglib.fieldgen;

import java.util.Map;

/**
 * This factory stores and returns the fieldgen handlers These variables are set in the
 * openhouse-servlet.xml and are populated via spring injection
 * 
 * @see FieldGenHandler
 */
public class FieldGenHandlerFactory {
	
	private Map<String, String> handlers = null;
	
	private static FieldGenHandlerFactory singleton;
	
	/**
	 * Generic constructor
	 */
	public FieldGenHandlerFactory() {
		if (singleton == null)
			singleton = this;
	}
	
	/**
	 * Auto generated method comment
	 * 
	 * @return
	 */
	public static FieldGenHandlerFactory getSingletonInstance() {
		if (singleton == null)
			throw new RuntimeException("Not Yet Instantiated");
		else
			return singleton;
	}
	
	/**
	 * @return Returns the handlers.
	 */
	public Map<String, String> getHandlers() {
		return singleton.handlers;
	}
	
	/**
	 * Appends the given handlers to the current map of handlers
	 * 
	 * @param handlers The handlers to set.
	 */
	public void setHandlers(Map<String, String> handlers) {
		if (singleton.handlers == null)
			singleton.handlers = handlers;
		else
			singleton.handlers.putAll(handlers);
	}
	
	/**
	 * Auto generated method comment
	 * 
	 * @param className
	 * @return
	 */
	public String getHandlerByClassName(String className) {
		if (className != null) {
			if (singleton.handlers.containsKey(className)) {
				return singleton.handlers.get(className);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}

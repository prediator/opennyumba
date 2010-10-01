package org.openhouse.api.dao.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.util.ConfigHelper;
import org.openhouse.api.context.Context;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;


/**
 * 
 * This class is responsible for configuring the hibernate session factory.
 * 
 * @author Samuel Mbugua
 *
 */
public class HibernateSessionFactoryBean extends LocalSessionFactoryBean{

	private Logger log = Logger.getLogger(this.getClass());

	/** List of hibernate mapping files. */
	protected Set<String> tmpMappingResources = new HashSet<String>(); 
	
	
	/**
	 * Creates a new hibernate configuration.
	 */
	public Configuration newConfiguration() throws HibernateException {
		Configuration config = super.newConfiguration();
		
		log.debug("Configuring hibernate sessionFactory properties");
		
		Properties properties = Context.getRuntimeProperties();
		
		// loop over runtime properties and override each in the configuration
		for (Object key : properties.keySet()) {
			String prop = (String)key;
			String value = (String)properties.get(key);
			log.debug("Setting property: " + prop + ":" + value);
			config.setProperty(prop, value);
			if (!prop.startsWith("hibernate"))
				config.setProperty("hibernate." + prop, value);
		}
		
		// load in the default hibernate properties
		try {
			InputStream propertyStream = ConfigHelper.getResourceAsStream("/hibernate.default.properties");
			Properties props = new Properties();
			props.load(propertyStream);
			propertyStream.close();
	
			// Only load in the default properties if they don't exist
			config.mergeProperties(props);
		}
		catch (IOException e) {
			log.fatal("Unable to load default hibernate properties", e);
		}
		
		// check database connection before configuring session factory
		// If not done, Hibernate blocks until a successful connection is made
		String driver = config.getProperty("hibernate.connection.driver_class");
		String username = config.getProperty("hibernate.connection.username");
		String password = config.getProperty("hibernate.connection.password");
		String url = config.getProperty("hibernate.connection.url");
		int check = checkDatabaseConnection(driver, username, password, url);
		
		if (check == 0)
			return config;
		else{
			System.out.println("Problem connecting to database usename="+username+"&password="+password+"&");
			log.error("Error connecting to database. See error log for details.");
		}
		
		return null;
	}
	
	/**
	 * Non zero represents an error and should prevent hibernate from starting
	 * 
	 * @param driver
	 * @param user
	 * @param pw
	 * @param url
	 * @return int
	 */
	private int checkDatabaseConnection(String driver, String user, String pw, String url) {
		
		try {
			Class.forName(driver).newInstance();
		}
		catch (Exception e) {
			log.error("Error while starting up. Bad driver class: " + driver, e);
			log.error(e.getMessage()); //System.err.println(e.getMessage());
			log.error("Could not find driver_class '" + driver + "'.  Can be set with runtime property: 'connection.driver_class'");
			//System.err.println("Could not find driver_class '" + driver + "'.  Can be set with runtime property: 'connection.driver_class'");
			return 1;
		}
		
		log.debug("checking database connection");
		try {
			Connection db_connection = DriverManager.getConnection(url, user, pw);
			log.debug("Successful database connection");
			db_connection.close();
		}
		catch (Exception e) {
			log.error("Error while starting up.  Unable to connection using ", e);
			log.error(e.getMessage()); //System.err.println(e.getMessage());
			pw = pw.replaceAll(".", "*");
			log.error("Could not connect to database using url '" + url + "', username '" + user + "', and pw '" + pw + "'. Connection properties can be set with runtime property: 'connection.username', 'connection.password', and 'connection.url'");
			//System.err.println("Could not connect to database using url '" + url + "', username '" + user + "', and pw '" + pw + "'. Connection properties can be set with runtime property: 'connection.username', 'connection.password', and 'connection.url'");
			
			log.error(e.getMessage(), e);//e.printStackTrace();
			return 1;
		}
		
		return 0;
	}

	/** 
	 * Collect the mapping resources for future use because the mappingResources object is
	 * defined as 'private' instead of 'protected'
	 * 
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#setMappingResources(java.lang.String[])
	 */
	@Override
	public void setMappingResources(String[] mappingResources) {
		for (String resource : mappingResources) {
			tmpMappingResources.add(resource);
		}
		
		super.setMappingResources(tmpMappingResources.toArray(new String[] {}));
	}

	/**
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#destroy()
	 */
	@Override
	public void destroy() throws HibernateException {
		try {
			super.destroy();
		}
		catch (IllegalStateException e) {
			// ignore errors sometimes thrown by the CacheManager trying to shut down twice
			// see net.sf.ehcache.CacheManager#removeShutdownHook()
		}
	}
}

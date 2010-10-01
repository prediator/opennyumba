package org.openhouse.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.DatabaseManager;
import org.openhouse.util.OpenhouseUtil;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * This class runs at startup when the web application is being started and before
 * any servlets are created.
 * 
 * @author Samuel Mbugua
 *
 */
public class Listener extends ContextLoaderListener{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent event) {

		log.info("*** Initializing OpenHouse ***");

		try{	
			
			ServletContext servletContext = event.getServletContext();
			Properties appSettings = getAppSettings(servletContext);
			Context.setRuntimeProperties(appSettings);	
			
			loadConstants(servletContext);


			try{
				DatabaseManager.updateDatabase();
			}
			catch(Exception ex){
				
				//TODO Need to resolve this in a smarter way.
				//This is most probably because of people who had databases before
				//liquibase was used.

				log.error(ex.getMessage(), ex);
			}

			try {
				// start the spring context
				super.contextInitialized(event);
			}
			catch (CannotLoadBeanClassException e) {
				log.warn("Stacktrace: ", e);

				// Spring places a throwable in the servlet context as the wac if an error occurs.
				// we need to remove it in order to refresh the wac
				servletContext.removeAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

				// retry starting the spring context
				getContextLoader().initWebApplicationContext(servletContext);
			}

			Context.startup(appSettings);
		}
		catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		try {	    	
			Context.shutdown();
		}
		catch (Exception e) {
			log.warn("Error while shutting down OpenHouse", e);
		}

		super.contextDestroyed(event);
	}

	/**
	 * Gets the application settings like database connecting settings, and more.
	 * 
	 * @param servletContext the servlet context.
	 * @return the Properties object with the application settings.
	 */
	public static Properties getAppSettings(ServletContext servletContext){
		Log log = LogFactory.getLog(Listener.class);

		Properties props = new Properties();

		try {
			FileInputStream propertyStream = null;

			// Look for environment variable {WEBAPP.NAME}_SETTINGS
			String env = "OPENHOUSE_RUNTIME";

			String filepath = System.getenv(env);

			if (filepath != null) {
				log.debug("Atempting to load settings: " + filepath + " ");
				try {
					propertyStream = new FileInputStream(filepath);
				}
				catch (IOException e) {
					log.warn("Unable to load settings file with path: " + filepath + ". (derived from environment variable " + env + ")", e);
				}
			} else {
				log.warn("Couldn't find an environment variable named " + env);
				log.debug("Available environment variables are named: " + System.getenv().keySet());
			}

			// env is the name of the file to look for in the directories
			String filename = env + ".properties";

			if (propertyStream == null) {
				filepath = OpenhouseUtil.getApplicationDataDirectory() + filename;
				log.debug("Attempting to load settings file: " + filepath);
				try {
					propertyStream = new FileInputStream(filepath);
					System.out.println("Using settings file: " + filepath);
				}
				catch (IOException e) {
					log.warn("Unable to load settings file: " + filepath, e);
				}	
			}

			// look in current directory last
			if (propertyStream == null) {
				filepath = servletContext.getRealPath("") + File.separatorChar + filename;
				log.debug("Attempting to load settings file in directory: " + filepath);
				try {
					propertyStream = new FileInputStream(filepath);
					System.out.println("Using settings file: " + filepath);
				}
				catch (IOException e) {
					log.warn("Unable to load settings file: " + new File(filepath).getAbsolutePath(), e);
				}
			}

			if (propertyStream == null)
				throw new IOException("Could not open '" + filename + "' in user or local directory.");

			props.load(propertyStream);
			propertyStream.close();
			log.warn("Using settings file: " + filepath);

		} catch (Throwable t) {
			log.warn("Unable to load settings file. Starting with default settings.", t);
		}
		return props;
	}
	
	private void loadConstants(ServletContext servletContext) {
		WebConstants.BUILD_TIMESTAMP = servletContext.getInitParameter("build.timestamp");
		WebConstants.WEBAPP_NAME = getContextPath(servletContext);
	}
	
	private String getContextPath(ServletContext servletContext) {
		// Get the context path without the request.
		String contextPath = "";
		try {
			String path = servletContext.getResource("/").getPath();
			contextPath = path.substring(0, path.lastIndexOf("/"));
			contextPath = contextPath.substring(contextPath.lastIndexOf("/"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// trim off initial slash if it exists
		if (contextPath.indexOf("/") != -1)
			contextPath = contextPath.substring(1);
		
		return contextPath;
	}
}

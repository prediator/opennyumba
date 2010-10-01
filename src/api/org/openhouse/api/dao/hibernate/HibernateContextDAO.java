package org.openhouse.api.dao.hibernate;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhouse.api.context.Context;
import org.openhouse.api.dao.ContextDAO;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseDisabledUserException;
import org.openhouse.api.exception.OpenHouseSecurityException;
import org.openhouse.util.SecurityUtil;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;



/**
 * Provides a hibernate implementation of the context data access interface.
 * 
 * @author Samuel Mbugua
 *
 */
public class HibernateContextDAO implements ContextDAO{

	/** The logger. */
	//private static Log log = LogFactory.getLog(HibernateContextDAO.class);
	private static final Logger log = Logger.getLogger(HibernateContextDAO.class);

	private boolean participate = false;


	/**
	 * Hibernate session factory.
	 */
	private SessionFactory sessionFactory;

	/**
	 * Default public constructor
	 */
	public HibernateContextDAO() {	}

	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @throws OpenHouseDisabledUserException <code>if(user.isDisabled() == true) </code>
	 * @see org.openhouse.api.dao.ContextDAO#authenticate(java.lang.String, java.lang.String)
	 */
	public User authenticate(String login, String password) throws OpenHouseSecurityException {

		String errorMsg = "Invalid username and/or password: " + login;
		
		Session session = sessionFactory.getCurrentSession();
		
		User candidateUser = null;
		
		if (login != null) {
			
			// loginWithoutDash is used to compare to the system id
			String loginWithDash = login;
			if (login.matches("\\d{2,}"))
				loginWithDash = login.substring(0, login.length() - 1) + "-" + login.charAt(login.length() - 1);
			
			try {
				candidateUser = (User) session.createQuery(
				    "from User u where (u.username = ? or u.systemId = ? or u.systemId = ?) and u.voided = 0").setString(0,
				    login).setString(1, login).setString(2, loginWithDash).uniqueResult();
			}
			catch (HibernateException he) {
				log.error("Got hibernate exception while logging in: '" + login + "'", he);
			}
			catch (Exception e) {
				log.error("Got regular exception while logging in: '" + login + "'", e);
			}
		}
			
		// only continue if this is a valid username and a nonempty password
		if (candidateUser != null && password != null) {
			if (log.isDebugEnabled())
				log.debug("Candidate user id: " + candidateUser.getUserId());
			
			String passwordOnRecord = (String) session.createSQLQuery("select password from users where user_id = ?")
			        .addScalar("password", Hibernate.STRING).setInteger(0, candidateUser.getUserId()).uniqueResult();
			
			String saltOnRecord = (String) session.createSQLQuery("select salt from users where user_id = ?").addScalar(
			    "salt", Hibernate.STRING).setInteger(0, candidateUser.getUserId()).uniqueResult();
				
				// if the username and password match, hydrate the user and return it
				if (passwordOnRecord != null && SecurityUtil.hashMatches(passwordOnRecord, password + saltOnRecord)) {
					// hydrate the user object
					candidateUser.getAllRoles().size();
					candidateUser.getUserProperties().size();
					candidateUser.getPrivileges().size();
					
					return candidateUser;
				} 
			}
			
			// throw this exception only once in the same place with the same
			// message regardless of username/pw combo entered
			log.info("Failed login attempt (login=" + login + ") - " + errorMsg);
			throw new OpenHouseSecurityException(errorMsg);
	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#openSession()
	 */
	public void openSession() {
		log.debug("HibernateContext: Opening Hibernate Session");
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			if (log.isDebugEnabled())
				log.debug("Participating in existing session ("
						+ sessionFactory.hashCode() + ")");
			participate = true; 
		} else {
			if (log.isDebugEnabled())
				log.debug("Registering session with synchronization manager ("
						+ sessionFactory.hashCode() + ")");
			Session session = SessionFactoryUtils.getSession(sessionFactory,
					true);
			session.setFlushMode(FlushMode.MANUAL);
			TransactionSynchronizationManager.bindResource(sessionFactory,
					new SessionHolder(session));
		}
	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#closeSession()
	 */
	public void closeSession() {
		log.debug("HibernateContext: closing Hibernate Session");
		if (!participate) {
			log.debug("Unbinding session from synchronization mangaer ("
					+ sessionFactory.hashCode() + ")");

			if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
				Object value = TransactionSynchronizationManager.unbindResource(sessionFactory);
				try {
					if (value instanceof SessionHolder) {
						Session session = ((SessionHolder)value).getSession();
						SessionFactoryUtils.releaseSession(session, sessionFactory);
					}
				} catch (RuntimeException e) {
					log.error("Unexpected exception on closing Hibernate Session", e);
				}
			}
		} else {
			log.debug("Participating in existing session, so not releasing session through synchronization manager");
		}
	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#clearSession()
	 */
	public void clearSession() {
		sessionFactory.getCurrentSession().clear();
	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#evictFromSession(java.lang.Object)
	 */
	public void evictFromSession(Object obj) {
		sessionFactory.getCurrentSession().evict(obj);
	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#startup(java.util.Properties)
	 */
	public void startup(Properties properties) {

	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#shutdown()
	 */
	public void shutdown() {

		if (sessionFactory != null) {

			// session is closed by spring on session end

			log.debug("Closing any open sessions");
			// closeSession();
			log.debug("Shutting down threadLocalSession factory");

			// sessionFactory.close();
			log.debug("The threadLocalSession has been closed");

			log.debug("Setting static variables to null");
			// sessionFactory = null;
		} else
			log.error("SessionFactory is null");


		//If running embedded derby, we need to shutdown the database engine.
		String driver = Context.getRuntimeProperties().getProperty("hibernate.connection.driver_class");
		if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
			boolean gotSQLExc = false;
			try {
				DriverManager.getConnection("jdbc:derby:;shutdown=true");
			} 
			catch (SQLException se)  {
				if ( se.getSQLState().equals("XJ015") )
					gotSQLExc = true;
			}
			if (!gotSQLExc)
				log.debug("Database did not shut down normally");//System.out.println("Database did not shut down normally"); 
			else
				log.debug("Database shut down normally");//System.out.println("Database shut down normally");
		}

	}

	/**
	 * @see org.openhouse.api.dao.ContextDAO#closeDatabaseConnection()
	 */
	public void closeDatabaseConnection() {
		sessionFactory.close();
	}
}

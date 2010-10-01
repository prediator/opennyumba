package org.openhouse.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.context.Context;
import org.openhouse.api.context.UserContext;
import org.openhouse.api.database.model.User;
import org.openhouse.web.WebConstants;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 
 * This is the custom filter. It is defined as the filter of choice in the web.xml file. All
 * page/object calls run through the doFilter method so we can wrap every session with the user's
 * userContext (which holds the user's authenticated info). This is needed because the API
 * keeps authentication information on the current Thread. Web applications use a different thread
 * per request, so before each request this filter will make sure that the UserContext (the
 * authentication information) is on the Thread.
 * 
 * @author Samuel Mbugua
 *
 */
public class OpenHouseFilter extends OncePerRequestFilter{
	
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * This method is called for every request for a page/image/javascript file/etc The main point
	 * of this is to make sure the user's current userContext is on the session and on the current
	 * thread
	 * 
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws ServletException, IOException {
		
		HttpSession httpSession = httpRequest.getSession();
		
		// used by htmlInclude tag
		httpRequest.setAttribute(WebConstants.INIT_REQ_UNIQUE_ID, String.valueOf(new Date().getTime()));
		
		if (log.isDebugEnabled()) {
			log.debug("requestURI " + httpRequest.getRequestURI());
			log.debug("requestURL " + httpRequest.getRequestURL());
			log.debug("request path info " + httpRequest.getPathInfo());
		}
		
		// User context is created if it doesn't already exist and added to the session
		UserContext userContext = (UserContext) httpSession.getAttribute("__openhouse_user_context");
		
		// default the session username attribute to anonymous
		httpSession.setAttribute("username", "-anonymous user-");
		
		// if there isn't a userContext on the session yet, create one
		// and set it onto the session
		if (userContext == null) {
			userContext = new UserContext();
			httpSession.setAttribute("__openhouse_user_context", userContext);
		} 
		else{
			// set username as attribute on session so parent servlet container 
			// can identify sessions easier
			User user;
			if ((user = userContext.getAuthenticatedUser()) != null)
				httpSession.setAttribute("username", user.getUsername());
		}
		
		// set the locale on the session (for the servlet container as well)
		//httpSession.setAttribute("locale", userContext.getLocale());
		
		// Add the user context to the current thread 
		Context.setUserContext(userContext);
		
		// continue the filter chain (going on to spring, authorization, etc)
		try {
			chain.doFilter(httpRequest, httpResponse);
		}
		finally {
			Context.clearUserContext();
		}
	}
}

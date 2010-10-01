package org.openhouse.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openhouse.api.context.Context;

/**
 * Servlet called by the logout link in the webapp. This will call Context.logout() and then make
 * sure the current user's http session is cleaned up and ready for another user to log in
 * 
 * @see Context#logout()
 */
public class LogoutServlet extends HttpServlet {
	
	public static final long serialVersionUID = 123423L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession httpSession = request.getSession();
		
		Context.logout();
		
		httpSession.removeAttribute(WebConstants.OPENHOUSE_USER_CONTEXT_HTTPSESSION_ATTR);
		httpSession.setAttribute(WebConstants.OPENHOUSE_MSG_ATTR, "auth.logged.out");
		httpSession.setAttribute(WebConstants.OPENHOUSE_LOGIN_REDIRECT_HTTPSESSION_ATTR, request.getContextPath());
		response.sendRedirect(request.getContextPath() + "/login.form");
		
		httpSession.invalidate();
	}
	
}

package org.openhouse.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Intercepter which checks if the user is trying to access a restricted page without a valid login
 * @author Samuel Mbugua
 *
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = Logger.getLogger(LoginInterceptor.class);
    private String loginPage;
    private String urlParameterName;
    private Set<String> allowedPaths;

    //If we need to implement counting of strikes, use this method as first link in preHandle
    /*private int incrementStrikes(HttpServletRequest request) {
        Integer strikesAsInteger = (Integer) WebUtils.getSessionAttribute(request, N_STRIKES);
        if (strikesAsInteger == null) {
            strikesAsInteger = new Integer(0);
        } else {
            strikesAsInteger = new Integer(strikesAsInteger.intValue() + 1);
        }
        WebUtils.setSessionAttribute(request, N_STRIKES, strikesAsInteger);
        return strikesAsInteger.intValue();
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        log.debug("Checking for user information");
        //User user = (User) WebUtils.getSessionAttribute(request,"CONNECTED_USER");
        User user=Context.getAuthenticatedUser();
        String url = request.getServletPath();
        if (user == null && !allowedPaths.contains(url)) {
            String query = request.getQueryString();
            log.info("Anonymous user trying to login: " + url + ", with query: " + query);
            ModelAndView modelAndView = new ModelAndView(loginPage);
            if (query != null) {
                modelAndView.addObject(urlParameterName, url + "?" + query);
            } else {
                modelAndView.addObject(urlParameterName, url);
            }
            throw new ModelAndViewDefiningException(modelAndView);
        } else {
            return true;
        }
    }

    /**
     * Set the url path for login page.
     */
    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    /**
     * Set the parameter name for which the user is trying to look for.
     */
    public void setUrlParameterName(String urlParameterName) {
        this.urlParameterName = urlParameterName;
    }

    /**
     * Set all the allowed paths that user can access without logging in.
     */
    public void setAllowedPaths(Set<String> allowedPaths) {
        this.allowedPaths = allowedPaths;
    }

}
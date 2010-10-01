package org.openhouse.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.openhouse.api.context.Context;
import org.openhouse.api.exception.OpenHouseException;
import org.openhouse.api.exception.OpenHouseSecurityException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller to handle Login.
 *
 * @author Samuel Mbugua
 *
 */
@Controller
@RequestMapping("/login.form")
public class LoginController {
    private static Logger log = Logger.getLogger(LoginController.class);

    /**
     * Get the form object for login information.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpSession httpSession, @RequestParam(value = "targetUrl", required = false) String targetUrl) {
        if (Context.isAuthenticated()) {
			log.info(Context.getAuthenticatedUser().getFullName() + " is authenticated with locale  " + 
					Context.getLocale());
			if(targetUrl != null && !targetUrl.equals("")) {
				//return a target url 
				return "redirect:" + targetUrl;
			}else
				return "redirect:homepage.form";
		}
		return "/login";
    }

    /**
     * Process the form submission and authenticate the user. If there is
     * targetUrl then the user wants to go somewhere, so after authentication
     * do redirection.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String authenticate(HttpSession httpSession, @RequestParam String username, @RequestParam String password,
    		@RequestParam(value = "targetUrl", required = false) String targetUrl) {
        log.info("Am authenticating user with name: " + username);

        try {
			
			// only try to authenticate if they actually typed in a username
			if (username == null || username.length() == 0)
				throw new OpenHouseSecurityException("Unable to authenticate with an empty username");
			
			Context.authenticate(username, password);
			
			if (Context.isAuthenticated()) {
				log.info(username + " is now authenticated with locale  " + 
						Context.getLocale());
				if(targetUrl != null && !targetUrl.equals("")) {
					//return a target url 
					return "redirect:" + targetUrl;
				}else
					return "redirect:homepage.form";
			}
		
					
				
		}
		catch (OpenHouseException e) {
			// set the error message for the user telling them
			// to try again
		}
		return "login";
    }
}
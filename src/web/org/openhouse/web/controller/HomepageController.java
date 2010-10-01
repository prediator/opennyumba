package org.openhouse.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller to handle Login.
 *
 * @author Samuel Mbugua
 *
 */
@Controller
@RequestMapping("/homepage.form")
public class HomepageController {
    @SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(HomepageController.class);

    /**
     * Get the form object for login information.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm() {
        return "homepage";
    }
}
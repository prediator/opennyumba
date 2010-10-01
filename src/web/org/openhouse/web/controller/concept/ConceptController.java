package org.openhouse.web.controller.concept;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConceptController {

    @RequestMapping(value="/concept/concept", method = RequestMethod.GET)
    public String setupForm() {
		return "/concept/concept";
    }
}

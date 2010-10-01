package org.openhouse.web.controller.persons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

    @RequestMapping(value="/persons/persons", method = RequestMethod.GET)
    public String setupForm() {
		return "/persons/persons";
    }
}

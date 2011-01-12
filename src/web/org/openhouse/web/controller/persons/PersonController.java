package org.openhouse.web.controller.persons;

import java.util.List;

import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

	@ModelAttribute("persons")
    @RequestMapping(value="/persons/persons", method = RequestMethod.GET)
    public List<Person> setupForm() {
    	return Context.getPersonService().getAllPersons();
    }
}

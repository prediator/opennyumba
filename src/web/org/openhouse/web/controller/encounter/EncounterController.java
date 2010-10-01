package org.openhouse.web.controller.encounter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EncounterController {

    @RequestMapping(value="/encounter/encounter", method = RequestMethod.GET)
    public String setupForm() {
		return "/encounter/encounter";
    }
}

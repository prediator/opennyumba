package org.openhouse.web.controller.obs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ObsController {

    @RequestMapping(value="/obs/obs", method = RequestMethod.GET)
    public String setupForm() {
		return "/obs/obs";
    }
}

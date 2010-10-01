package org.openhouse.web.controller.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ServerController {

    @RequestMapping(value="/server/server", method = RequestMethod.GET)
    public String setupForm() {
		return "/server/server";
    }
}

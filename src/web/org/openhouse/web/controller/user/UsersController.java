package org.openhouse.web.controller.user;

import java.util.List;

import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller to handle Users.
 *
 * @author Samuel Mbugua
 */
@Controller
public class UsersController {
    //private static Logger log = Logger.getLogger(UsersController.class);
    
    @ModelAttribute("users")
    @RequestMapping(value="/user/users", method = RequestMethod.GET)
    public List<User> setupForm() throws OpenHouseException {
		return Context.getUserService().getAllUsers();
    }
}
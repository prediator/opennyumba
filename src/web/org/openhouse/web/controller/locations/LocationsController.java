package org.openhouse.web.controller.locations;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.Location;
import org.openhouse.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocationsController {
	
	private static Logger log = Logger.getLogger(LocationsController.class);
	
	@ModelAttribute("locations")
    @RequestMapping(value="/locations/locations", method = RequestMethod.GET)
    public List<Location> setupForm() {
		return Context.getLocationService().getAllLocations();
    }
	
	
	@RequestMapping(value="/locations/locations", method = RequestMethod.POST)
	public String deleteLocations(HttpSession httpSession,
			@RequestParam(value = "locationId", required = false) List<Integer> locationsIds) {
		log.debug("Am deleting " + locationsIds.size() + " locations");
		if (locationsIds.size()<1){
			httpSession.setAttribute(WebConstants.OPENHOUSE_ERROR_ATTR, "An Object already exists with similar name" );
			return "redirect:/locations/locations.list";
		}
		for (Integer locationId : locationsIds) {
			Location loc = Context.getLocationService().getLocation(locationId);
			Context.getLocationService().purgeLocation(loc);
			httpSession.setAttribute(WebConstants.OPENHOUSE_MSG_ATTR, "Successfully deleted location " + locationId);
		}
		return "redirect:/locations/locations.list";
	}
}

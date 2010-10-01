package org.openhouse.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.context.Context;
import org.openhouse.api.database.model.User;
import org.openhouse.api.exception.OpenHouseException;

public class UserWidgetTag extends TagSupport {
	
	public static final long serialVersionUID = 1L;
	
	private final Log log = LogFactory.getLog(getClass());
	
	private Integer userId;
	
	private String size = "normal";
	
	public UserWidgetTag() {
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public int doStartTag() {
		
		try {
			User user = Context.getUserService().getUser(userId);
			JspWriter w = pageContext.getOut();
			w.print(user.getFullName());
			if ("full".equals(size)) {
				w.print(" <i>(" + user.getUsername() + ")</i>");
			}
		}
		catch (IOException ex) {
			log.error("Error while starting userWidget tag", ex);
		} catch (OpenHouseException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() {
		userId = null;
		size = "normal";
		return EVAL_PAGE;
	}
	
}

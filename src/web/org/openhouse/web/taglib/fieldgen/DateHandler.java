package org.openhouse.web.taglib.fieldgen;

public class DateHandler extends AbstractFieldGenHandler implements FieldGenHandler {
	
	private String defaultUrl = "date.field";
	
	public void run() {
		setUrl(defaultUrl);
		
		String needScript = "true";
		
		if (getRequest().getAttribute("org.openhouse.widget.dateField.needScript") != null) {
			needScript = "false";
		} else {
			getRequest().setAttribute("org.openhouse.widget.dateField.needScript", "false");
		}
		
		setParameter("needScript", needScript);
	}
}

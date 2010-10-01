package org.openhouse.util;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhouse.api.context.Context;
import org.springframework.util.StringUtils;

/**
 * A utility class for working with Locales.
 */
public class LocaleUtility {
	
	private static Log log = LogFactory.getLog(LocaleUtility.class);
	
	public static Locale getDefaultLocale() {
		if (Context.isSessionOpen()) {
			try {
				String locale = Context.getAdministrationService().getGlobalProperty(
				    OpenhouseConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE);
				
				if (StringUtils.hasLength(locale)) {
					try {
						return fromSpecification(locale);
					}
					catch (Exception t) {
						log.warn("Unable to parse default locale global property value: " + locale, t);
					}
				}
			}
			catch (Throwable t) {
				// swallow most of the stack trace for most users
				log.warn("Unable to get locale global property value. " + t.getMessage());
				log.trace("Unable to get locale global property value", t);
			}
		}
		
		return fromSpecification(OpenhouseConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE_DEFAULT_VALUE);
	}
	
	public static boolean areCompatible(Locale lhs, Locale rhs) {
		if (lhs.equals(rhs)) {
			return true;
		} else if ((lhs.getCountry() == "") || (rhs.getCountry() == "")) {
			// no country specified, so language match is good enough
			if (lhs.getLanguage().equals(rhs.getLanguage())) {
				return true;
			}
		}
		return false;
	}

	public static Locale fromSpecification(String localeSpecification) {
		Locale createdLocale = null;
		
		localeSpecification = localeSpecification.trim();
		
		String[] localeComponents = localeSpecification.split("_");
		if (localeComponents.length == 1) {
			createdLocale = new Locale(localeComponents[0]);
		} else if (localeComponents.length == 2) {
			createdLocale = new Locale(localeComponents[0], localeComponents[1]);
		} else if (localeComponents.length > 2) {
			String variant = localeSpecification.substring(localeSpecification.indexOf(localeComponents[2])); // gets everything after the second underscore
			createdLocale = new Locale(localeComponents[0], localeComponents[1], variant);
		}
		
		return createdLocale;
	}
}

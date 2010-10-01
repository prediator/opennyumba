package org.openhouse.web.taglib.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;

public class Sort {
	
	/**
	 * This method will sort a collection based on the natural order of it's elements
	 * 
	 * @param c
	 * @return
	 */
	public static <T> List<T> sort(Collection<T> c) {
		return sort(c, null, false);
	}
	
	/**
	 * This method will sort a collection based on the natural order of it's elements
	 * 
	 * @param c
	 * @param isDescending
	 * @return
	 */
	public static <T extends Comparable<T>> List<T> sort(Collection<T> c, Boolean reverseOrder) {
		List<T> l = new ArrayList<T>(c);
		Collections.sort(l);
		return l;
	}
	
	/**
	 * This method will sort a passed Collection
	 * 
	 * @param c: The collection to sort
	 * @param sortProperty: The javabean property to sort the elements of the Collection by
	 * @param isDescending: Boolean indicating whether or not to reverse the order of the collection
	 * @return: A sorted List of the passed elements
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> sort(Collection<T> c, String sortProperty, Boolean reverseOrder) {
		if (sortProperty == null || sortProperty.equals("")) {
			throw new IllegalArgumentException("sortProperty = " + sortProperty);
		}
		List<T> l = new ArrayList<T>(c);
		Comparator comp = new BeanComparator(sortProperty, new ComparableComparator());
		Collections.sort(l, comp);
		if (reverseOrder) {
			Collections.reverse(l);
		}
		return l;
	}
}

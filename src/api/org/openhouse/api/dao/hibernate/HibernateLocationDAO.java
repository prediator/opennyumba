package org.openhouse.api.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.openhouse.api.dao.LocationDAO;
import org.openhouse.api.database.model.Location;
import org.openhouse.api.database.model.LocationTag;

/**
 * Hibernate location-related database functions
 */
public class HibernateLocationDAO implements LocationDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Location saveLocation(Location location) {
		if (location.getChildLocations() != null && location.getLocationId() != null) {
			// hibernate has a problem updating child collections
			// if the parent object was already saved so we do it 
			// explicitly here
			for (Location child : location.getChildLocations())
				if (child.getLocationId() == null)
					saveLocation(child);
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(location);
		return location;
	}
	
	public Location getLocation(Integer locationId) {
		return (Location) sessionFactory.getCurrentSession().get(Location.class, locationId);
	}
	
	@SuppressWarnings("unchecked")
	public Location getLocation(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Location.class).add(
		    Expression.eq("name", name));
		
		List<Location> locations = criteria.list();
		if (null == locations || locations.isEmpty()) {
			return null;
		}
		return locations.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Location> getAllLocations(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Location.class);
		if (!includeRetired) {
			criteria.add(Expression.like("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Location> getLocations(String search) {
		if (search == null || search.equals(""))
			return getAllLocations(true);
		
		return sessionFactory.getCurrentSession().createCriteria(Location.class)
		// 'ilike' case insensitive search
		        .add(Expression.ilike("name", search, MatchMode.START)).addOrder(Order.asc("name")).list();
	}
	
	public void deleteLocation(Location location) {
		sessionFactory.getCurrentSession().delete(location);
	}
	
	public LocationTag saveLocationTag(LocationTag tag) {
		sessionFactory.getCurrentSession().saveOrUpdate(tag);
		return tag;
	}
	
	public LocationTag getLocationTag(Integer locationTagId) {
		return (LocationTag) sessionFactory.getCurrentSession().get(LocationTag.class, locationTagId);
	}
	
	@SuppressWarnings("unchecked")
	public LocationTag getLocationTagByName(String tag) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LocationTag.class).add(
		    Expression.eq("tag", tag));
		
		List<LocationTag> tags = criteria.list();
		if (null == tags || tags.isEmpty()) {
			return null;
		}
		return tags.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<LocationTag> getAllLocationTags(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LocationTag.class);
		if (!includeRetired) {
			criteria.add(Expression.like("retired", false));
		}
		criteria.addOrder(Order.asc("tag"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<LocationTag> getLocationTags(String search) {
		return sessionFactory.getCurrentSession().createCriteria(LocationTag.class)
		// 'ilike' case insensitive search
		        .add(Expression.ilike("tag", search, MatchMode.START)).addOrder(Order.asc("tag")).list();
	}
	
	public void deleteLocationTag(LocationTag tag) {
		sessionFactory.getCurrentSession().delete(tag);
	}
	
	
}

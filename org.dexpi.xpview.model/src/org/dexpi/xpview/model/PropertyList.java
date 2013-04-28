package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PropertyList implements Iterable<Property> {

	protected String name;
	protected ArrayList<Property> properties;
	protected PropertyData propertyData;
	
	public PropertyList(PropertyData propertyData, String name) {
		this.properties = new ArrayList<Property>();
		this.propertyData = propertyData;
		this.name = name;
	}
	
	public List<Property> getProperties() {
		return properties;
	}
	
	@Override
	public Iterator<Property> iterator() {
		return properties.iterator();
	}
	
	public void addProperty(Property property) {
		this.properties.add(property);
	}
	
	public void addProperty(PropertyType type, String name, String value) {
		Property property = new Property(this, type, name, value, null, null);
		this.properties.add(property);
	}

	public void addProperty(PropertyType type, String name, String value, String format, String uri) {
		Property property = new Property(this, type, name, value, format, uri);
		this.properties.add(property);
	}
	
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPropertyValue(String name) {
		String result = "";
		Property property = getProperty(name);
		if (property != null) {
			result = property.getValue();
		}
		return result;
	}
	
	public Property getProperty(String name) {
		Property result = null;
		for (Property property : properties) {
			if (property.getName().equals(name)) {
				result = property;
				break;
			}
		}
		return result;
	}
}

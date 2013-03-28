package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class PropertyData {

	protected PropertyItem propertyItem;
	protected ArrayList<PropertyList> properyLists;
	
	public PropertyData(PropertyItem propertyItem) {
		this.propertyItem = propertyItem;
		this.properyLists = new ArrayList<PropertyList>();
	}
	
	public PropertyList addPropertyList(String name) {
		PropertyList propList = new PropertyList(this, name);
		properyLists.add(propList);
		return propList;
	}
	
	public List<PropertyList> getProperyLists() {
		return properyLists;
	}
	
	public PropertyList getProperyList(String name) {
		PropertyList result = null;
		for (PropertyList propList : properyLists) {
			if (propList.getName().equals(name)) {
				result = propList;
			}
		}
		return result;
	}
	
	public PropertyItem getPropertyItem() {
		return propertyItem;
	}
	
	public Property getProperty(String name) {
		Property result = null;
		for (PropertyList propList : properyLists) {
			result = propList.getProperty(name);
			if (result != null) {
				break;
			}
		}
		return result;
	}
	
	public String getPropertyValue(String name) {
		Property property = null;
		for (PropertyList propList : properyLists) {
			property = propList.getProperty(name);
			if (property != null) {
				break;
			}
		}
		String result = "<UNDEFINED>";
		if (property != null) {
			result = property.getValue();
		}
		return result;
	}
}

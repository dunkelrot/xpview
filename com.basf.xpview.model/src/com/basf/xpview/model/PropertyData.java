package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class PropertyData {

	protected ArrayList<PropertyList> properyLists;
	
	public PropertyData(PropertyItem propertyItem) {
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
	
}
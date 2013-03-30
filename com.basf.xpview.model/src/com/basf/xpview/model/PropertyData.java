package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage the Properties. The {@link Property} objects
 * are stored in one or more {@link PropertyList} instances which are managed 
 * by this.
 * 
 * All objects which have properties should implement {@link PropertyItem}.
 * 
 * @author Arndt Teinert
 *
 */
public class PropertyData {

	protected PropertyItem propertyItem;
	protected ArrayList<PropertyList> properyLists;
	
	/**
	 * Constructor, nothing special about this.
	 * @param propertyItem the owning {@link PropertyItem}
	 */
	public PropertyData(PropertyItem propertyItem) {
		this.propertyItem = propertyItem;
		this.properyLists = new ArrayList<PropertyList>();
	}

	/**
	 * Creates a new {@link PropertyList} and adds it to this instance.
	 * @param name the name of the new {@link PropertyList}
	 * @return the newly created {@link PropertyList}
	 */
	public PropertyList addPropertyList(String name) {
		PropertyList propList = new PropertyList(this, name);
		properyLists.add(propList);
		return propList;
	}
	
	/**
	 * Returns the list of {@link PropertyList} objects.
	 * @return a list of {@link PropertyList} objects
	 */
	public List<PropertyList> getProperyLists() {
		return properyLists;
	}
	
	/**
	 * Returns a {@link PropertyList} by name.
	 * @param name the name of the {@link PropertyList} to find
	 * @return a {@link PropertyList} instance or null
	 */
	public PropertyList getProperyList(String name) {
		PropertyList result = null;
		for (PropertyList propList : properyLists) {
			if (propList.getName().equals(name)) {
				result = propList;
			}
		}
		return result;
	}
	
	/**
	 * Returns the owning {@link PropertyItem} object.
	 * @return the owning {@link PropertyItem} object
	 */
	public PropertyItem getPropertyItem() {
		return propertyItem;
	}
	
	/**
	 * Returns a {@link Property} by name. The first found {@link Property} is returned.
	 * 
	 * @param name the name of the {@link Property} to find
	 * @return the found {@link Property} or null
	 */
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
	
	/**
	 * Returns the value of {@link Property} by name. 
	 * The first found {@link Property} is used to return it's value.
	 * 
	 * @param name the name of the {@link Property} to find
	 * @return the value of the found {@link Property} or &lt;UNDEFINED&gt; in case no {@link Property} was found 
	 */
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

package com.basf.xpview.model;

public interface PropertyItem {

	PropertyData getPropertyData();
	
	/**
	 * Returns the first found property or null.
	 *  
	 * @param name
	 * @return property instance of null
	 */
	Property getProperty(String name);
	
	/**
	 * Returns the value from the given property.
	 * The first found property is used to get the value.
	 * In case no property is found "<UNDEFINED>" is returned.
	 *  
	 * @param name
	 * @return value or <UNDEFINED>
	 */
	String getPropertyValue(String name);
}

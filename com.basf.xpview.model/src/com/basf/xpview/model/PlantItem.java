package com.basf.xpview.model;

/**
 * Base class for all elements in a plant.
 * 
 * @author Arndt Teinert
 *
 */
public abstract class PlantItem extends Thing implements PropertyItem {

	protected PropertyData propertyData;
	protected PlantItem parent;
	
	protected String tagName;
	protected String className;
	protected String type;
	
	public PlantItem(String name) {
		this(name, null);
	}
	
	public PlantItem(String name, PlantItem parent) {
		super(name);
		this.parent = parent;
		this.propertyData = new PropertyData(this);
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public PlantItem getParent() {
		return parent;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}

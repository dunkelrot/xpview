package com.basf.xpview.model;

/**
 * Base class for all elements in a plant.
 * 
 * @author Arndt Teinert
 *
 */
public abstract class PlantItem extends Thing implements PropertyProvider {

	protected PropertyData propertyData;
	
	protected String tagName;
	protected String className;
	protected String type;
	protected PlantItemContainer container;
	
	public PlantItem(String name, PlantItemContainer container) {
		super(name);
		this.container = container;
		this.propertyData = new PropertyData(this);
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
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
	
	public PlantItemContainer getContainer() {
		return container;
	}
}
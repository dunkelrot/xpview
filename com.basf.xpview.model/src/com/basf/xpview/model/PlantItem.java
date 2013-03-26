package com.basf.xpview.model;

/**
 * Base class for all elements in a plant.
 * 
 * @author Arndt Teinert
 *
 */
public abstract class PlantItem implements PropertyItem {

	protected PropertyData propertyData;
	protected Representation represenation;
	protected PlantItem parent;
	
	protected String name;

	public PlantItem(String name) {
		this(name, null);
	}
	
	public PlantItem(String name, PlantItem parent) {
		this.name = name;
		this.parent = parent;
		this.propertyData = new PropertyData(this);
		this.represenation = new Representation();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Representation getRepresenation() {
		return represenation;
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public PlantItem getParent() {
		return parent;
	}
}

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
	
	protected String name;

	public PlantItem(String name) {
		this.name = name;
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
}

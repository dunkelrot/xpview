package com.basf.xpview.model;

/**
 * A Component is a light-weight equipment.
 * 
 * @author Arndt Teinert
 *
 */
public class Component extends PlantItem {

	protected PlantItemContainer container;
	
	public Component(String name, String tagId, PlantItemContainer container) {
		super(name);
		this.container = container;
	}
	
	public PlantItemContainer getContainer() {
		return container;
	}
	
}

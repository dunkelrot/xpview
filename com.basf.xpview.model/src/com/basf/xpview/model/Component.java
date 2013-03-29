package com.basf.xpview.model;

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

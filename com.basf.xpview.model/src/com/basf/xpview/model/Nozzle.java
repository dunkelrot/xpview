package com.basf.xpview.model;

public class Nozzle extends PlantItem implements TaggedItem {

	protected String tagId;
	protected PlantItemContainer container;
	
	public Nozzle(String name, String tagId, PlantItem parent) {
		super(name, parent);
		this.tagId = tagId;
	}

	public Nozzle(String name, String tagId, PlantItemContainer container) {
		super(name);
		this.tagId = tagId;
		this.container = container;
	}
	
	@Override
	public String getTagId() {
		return tagId;
	}
	
	public PlantItemContainer getContainer() {
		return container;
	}
	
}

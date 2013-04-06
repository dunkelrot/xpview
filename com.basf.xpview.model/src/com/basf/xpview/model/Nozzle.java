package com.basf.xpview.model;

public class Nozzle extends PlantItem implements TaggedItem {

	protected String tagId;
	
	public Nozzle(String name, String tagId, PlantItemContainer container) {
		super(name, container);
		this.tagId = tagId;
	}

	@Override
	public String getTagId() {
		return tagId;
	}
	
	
}

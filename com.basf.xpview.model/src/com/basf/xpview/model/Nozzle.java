package com.basf.xpview.model;

public class Nozzle extends PlantItem implements TaggedItem {

	protected String tagId;
	
	public Nozzle(PlantItem parent, String name, String tagId) {
		super(name, parent);
		this.tagId = tagId;
	}

	@Override
	public String getTagId() {
		return tagId;
	}
	
}

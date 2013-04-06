package com.basf.xpview.model;

public class CenterLine extends Thing implements PropertyItem {

	protected PropertyData propertyData;
	protected PipingSegment parent;
	
	public CenterLine(PipingSegment parent) {
		super("CenterLine");
		this.propertyData = new PropertyData(this);
		this.parent = parent;
	}

	@Override
	public PropertyData getPropertyData() {
		return null;
	}
	
	public PipingSegment getParent() {
		return parent;
	}
	
}

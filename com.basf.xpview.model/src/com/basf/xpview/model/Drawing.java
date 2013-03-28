package com.basf.xpview.model;

public class Drawing extends Thing implements PropertyItem {
	
	protected PropertyData propertyData;
	protected DrawingList drawingList;

	public Drawing(DrawingList drawingList, String name) {
		super(name);
		this.drawingList = drawingList;
		this.propertyData = new PropertyData(this);
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	@Override
	public Property getProperty(String name) {
		return propertyData.getProperty(name);
	}
	
	@Override
	public String getPropertyValue(String name) {
		return propertyData.getPropertyValue(name);
	}
	
	public DrawingList getDrawingList() {
		return drawingList;
	}
	
}

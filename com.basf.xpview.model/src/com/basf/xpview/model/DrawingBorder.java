package com.basf.xpview.model;

public class DrawingBorder extends Thing implements PropertyItem {

	protected PropertyData propertyData;
	protected Drawing drawing;
	
	public DrawingBorder(Drawing drawing) {
		super("Border");
		this.drawing = drawing;
		this.propertyData = new PropertyData(this);
	}
	
	public Drawing getDrawing() {
		return drawing;
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
}

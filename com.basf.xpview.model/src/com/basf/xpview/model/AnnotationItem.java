package com.basf.xpview.model;

public class AnnotationItem extends Thing implements PropertyItem {

	protected PropertyData propertyData;
	protected AnnotationContainer container;
	
	public AnnotationItem(String name, AnnotationContainer container) {
		super(name);
		this.propertyData = new PropertyData(this);
		this.container = container;
	}

	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public AnnotationContainer getContainer() {
		return container;
	}
	
}

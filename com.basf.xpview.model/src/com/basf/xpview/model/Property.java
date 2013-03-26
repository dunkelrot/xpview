package com.basf.xpview.model;

public class Property {

	protected String name;
	protected String value;
	protected String format;
	protected String URI;
	protected PropertyList propertyList;
	protected PropertyType type;
	
	public Property(PropertyList propertyList, PropertyType type, String name, String value, String format, String URI) {
		this.propertyList = propertyList;
		this.name = name;
		this.value = value;
		this.format = format;
		this.type = type;
		this.URI = URI;
	}
	
	public String getName() {
		return name;
	}

	public String getFormat() {
		return format;
	}
	
	public PropertyType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getURI() {
		return URI;
	}
	
	public PropertyList getPropertyList() {
		return propertyList;
	}
	
	public boolean hasURI() {
		return URI != null;
	}
	
}

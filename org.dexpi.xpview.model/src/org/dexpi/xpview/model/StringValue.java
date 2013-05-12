package org.dexpi.xpview.model;

public class StringValue {

	protected String value;
	protected String unit;
	
	public StringValue(String value, String unit) {
		this.value = value;
		this.unit = unit;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public String getValue() {
		return value;
	}
	
}

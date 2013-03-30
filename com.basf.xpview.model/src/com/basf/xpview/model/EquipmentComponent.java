package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class EquipmentComponent implements PropertyItem {

	protected PropertyData propertyData;
	protected String name;
	protected ArrayList<EquipmentComponent> components;
	
	public EquipmentComponent(String name) {
		this.name = name;
		this.components = new ArrayList<EquipmentComponent>();
		this.propertyData = new PropertyData(this);
	}
	
	public List<EquipmentComponent> getComponents() {
		return components;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
}

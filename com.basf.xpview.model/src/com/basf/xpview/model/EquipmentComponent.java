package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class EquipmentComponent implements PropertyItem {

	String name;
	protected ArrayList<EquipmentComponent> components;
	
	public EquipmentComponent(String name) {
		this.name = name;
		this.components = new ArrayList<EquipmentComponent>();
	}
	
	public List<EquipmentComponent> getComponents() {
		return components;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public PropertyData getPropertyData() {
		// TODO Auto-generated method stub
		return null;
	}
}

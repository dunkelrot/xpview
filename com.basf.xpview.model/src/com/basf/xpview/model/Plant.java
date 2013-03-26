package com.basf.xpview.model;

/**
 * Root element of the data model.
 * 
 * @author Arndt Teinert
 *
 */
public class Plant extends PlantSection {

	protected String name;
	protected EquipmentList equipmentList;
	
	public Plant(String name) {
		super(name, null);
		this.equipmentList = new EquipmentList(this);
	}
	
	public EquipmentList getEquipmentList() {
		return equipmentList;
	}
	
}

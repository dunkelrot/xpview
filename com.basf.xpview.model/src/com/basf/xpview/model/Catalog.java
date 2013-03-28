package com.basf.xpview.model;

import java.util.ArrayList;

public class Catalog extends Thing implements EquipmentContainer {

	protected ArrayList<Equipment> equipments;
	protected CatalogList catalogList;
	
	public Catalog(CatalogList catalogList, String name) {
		super(name);
		this.catalogList = catalogList;
		this.equipments = new ArrayList<Equipment>();
	}
	
	@Override
	public void addEquipment(Equipment equipment) {
		equipments.add(equipment);
	}
	
	public CatalogList getParent() {
		return catalogList;
	}
	
	public Equipment findEquipment(String name) {
		Equipment result = null;
		for (Equipment equipment : equipments) {
			if (equipment.getName().equals(name)) {
				result = equipment;
				break;
			}
		}
		return result;
	}
	
}

package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Root element of the data model.
 * 
 * @author Arndt Teinert
 *
 */
public class Plant extends PlantSection {

	protected String name;
	protected EquipmentList equipmentList;
	protected CatalogList catalogList;
	
	public Plant(String name) {
		super(name, null);
		this.equipmentList = new EquipmentList(this);
		this.catalogList = new CatalogList(this);
	}
	
	public EquipmentList getEquipmentList() {
		return equipmentList;
	}
	
	public List<PlantItem> getAllPlantItems() {
		ArrayList<PlantItem> plantItems = new ArrayList<PlantItem>();
		for (Equipment equipment : equipmentList) {
			plantItems.add(equipment);
			for (Nozzle nozzle : equipment.getNozzles()) {
				plantItems.add(nozzle);
			}
		}
		return plantItems;
	}
	
	@Override
	public PlantSection getRootSection() {
		return this;
	}
	
	public CatalogList getCatalogList() {
		return catalogList;
	}
	
}

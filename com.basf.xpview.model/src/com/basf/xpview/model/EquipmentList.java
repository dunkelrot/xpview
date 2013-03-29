package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class EquipmentList implements PlantItemContainer {

	protected ArrayList<Equipment> equipments;
	protected Plant plant;
	
	public EquipmentList(Plant plant) {
		this.equipments = new ArrayList<Equipment>();
		this.plant = plant;
	}
	
	public List<Equipment> getEquipments() {
		return equipments;
	}
	
	public Plant getPlant() {
		return plant;
	}

	public void addEquipment(Equipment equipment) {
		equipments.add(equipment);	
	}
	
	public Plant getParent() {
		return plant;
	}

	@Override
	public void addPlantItem(PlantItem plantItem) {
		equipments.add((Equipment) plantItem);	
	}
	
}

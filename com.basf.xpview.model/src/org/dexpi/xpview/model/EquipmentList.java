package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@link EquipmentList} manages a list of {@link Equipment}s. It is used
 * to have a better structured {@link Plant} data model.
 * 
 * @author Arndt Teinert
 *
 */
public class EquipmentList implements PlantItemContainer, Iterable<Equipment> {

	protected ArrayList<Equipment> equipments;
	protected Plant plant;
	
	/**
	 * Constructor, nothing special
	 * @param plant the owning {@link Plant}
	 */
	public EquipmentList(Plant plant) {
		this.equipments = new ArrayList<Equipment>();
		this.plant = plant;
	}
	
	/**
	 * Returns a list with all {@link Equipment}s contained in this {@link EquipmentList} 
	 * @return the list of all {@link Equipment}s 
	 */
	public List<Equipment> getEquipments() {
		return equipments;
	}
	
	/**
	 * Returns the owning {@link Plant}
	 * @return the owning {@link Plant}
	 */
	public Plant getPlant() {
		return plant;
	}
	
	/**
	 * Adds the given {@link Equipment} to this list.
	 * Note that the caller must take care to set this instance as the parent for the added {@link Equipment}!
	 * @param equipment the {@link Equipment} to add
	 */
	public void addEquipment(Equipment equipment) {
		equipments.add(equipment);	
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		equipments.add((Equipment) plantItem);	
	}

	@Override
	public Iterator<Equipment> iterator() {
		return equipments.iterator();
	}
	
	@Override
	public List<Equipment> getPlantItems() {
		return equipments;
	}
	
}

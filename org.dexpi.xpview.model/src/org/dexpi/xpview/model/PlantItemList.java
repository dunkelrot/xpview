package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlantItemList implements PlantItemContainer, Iterable<PlantItem> {

	protected ArrayList<PlantItem> items;
	protected Plant plant;
	
	/**
	 * Constructor, nothing special
	 * @param plant the owning {@link Plant}
	 */
	public PlantItemList(Plant plant) {
		this.items = new ArrayList<PlantItem>();
		this.plant = plant;
	}
	
	/**
	 * Returns the owning {@link Plant}
	 * @return the owning {@link Plant}
	 */
	public Plant getPlant() {
		return plant;
	}
	
	/**
	 * Adds the given PlantItem to this list.
	 * Note that the caller must take care to set this instance as the parent for the added {@link PlantItem}!
	 * @param item the {@link PlantItem} to add
	 */
	public void addItem(PlantItem item) {
		items.add(item);	
	}
	
	@Override
	public void addPlantItem(PlantItem item) {
		items.add(item);	
	}

	@Override
	public Iterator<PlantItem> iterator() {
		return items.iterator();
	}
	
	@Override
	public List<PlantItem> getPlantItems() {
		return items;
	}

}

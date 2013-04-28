package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Component is a light-weight equipment.
 * 
 * @author Arndt Teinert
 *
 */
public class Component extends PlantItem implements PlantItemContainer {

	protected ArrayList<Component> components;
	
	public Component(String name, PlantItemContainer container) {
		super(name, container);
		this.components = new ArrayList<Component>();
	}

	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof Component) {
			components.add((Component) plantItem);
		}
	}

	@Override
	public List<? extends PlantItem> getPlantItems() {
		return components;
	}
	
}

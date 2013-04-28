package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class ProcessInstrument extends PlantItem implements PlantItemContainer {

	protected ArrayList<ProcessInstrument> processInstruments;
	protected ArrayList<Component> components;
	
	public ProcessInstrument(String name, PlantItemContainer container) {
		super(name, container);
		this.processInstruments = new ArrayList<ProcessInstrument>();
		this.components = new ArrayList<Component>();
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof ProcessInstrument) {
			processInstruments.add((ProcessInstrument) plantItem);
		} else if (plantItem instanceof ProcessInstrument) {
			components.add((Component) plantItem);
		}
	}

	@Override
	public List<PlantItem> getPlantItems() {
		ArrayList<PlantItem> plantItems = new ArrayList<PlantItem>();
		plantItems.addAll(components);
		plantItems.addAll(processInstruments);
		return plantItems;
	}

}

package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class InstrumentList implements PlantItemContainer {

	protected ArrayList<Instrument> instruments;
	protected Plant plant;
	
	/**
	 * Constructor, nothing special
	 * @param plant the owning {@link Plant}
	 */
	public InstrumentList(Plant plant) {
		this.instruments = new ArrayList<Instrument>();
		this.plant = plant;
	}
	
	/**
	 * Returns the owning {@link Plant}
	 * @return the owning {@link Plant}
	 */
	public Plant getPlant() {
		return plant;
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof Instrument) {
			instruments.add((Instrument) plantItem);
		}
	}
	
	@Override
	public List<? extends PlantItem> getPlantItems() {
		return instruments;
	}
	
}

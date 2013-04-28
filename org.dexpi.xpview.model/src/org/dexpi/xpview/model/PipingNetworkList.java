package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipingNetworkList implements PlantItemContainer, Iterable<PipingNetwork> {

	protected ArrayList<PipingNetwork> pipingNetworks;
	protected Plant plant;
	
	/**
	 * Constructor, nothing special
	 * @param plant the owning {@link Plant}
	 */
	public PipingNetworkList(Plant plant) {
		this.pipingNetworks = new ArrayList<PipingNetwork>();
		this.plant = plant;
	}
	
	/**
	 * Returns a list with all {@link PipingNetwork}s contained in this {@link PipingNetworkList} 
	 * @return the list of all {@link PipingNetwork}s 
	 */
	public List<PipingNetwork> getPipingNetworks() {
		return pipingNetworks;
	}
	
	/**
	 * Returns the owning {@link Plant}
	 * @return the owning {@link Plant}
	 */
	public Plant getPlant() {
		return plant;
	}
	
	/**
	 * Adds the given {@link PipingNetwork} to this list.
	 * Note that the caller must take care to set this instance as the parent for the added {@link PipingNetwork}!
	 * @param equipment the {@link PipingNetwork} to add
	 */
	public void addPipingNetwork(PipingNetwork equipment) {
		pipingNetworks.add(equipment);	
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		pipingNetworks.add((PipingNetwork) plantItem);	
	}

	@Override
	public Iterator<PipingNetwork> iterator() {
		return pipingNetworks.iterator();
	}

	@Override
	public List<PipingNetwork> getPlantItems() {
		return pipingNetworks;
	}
	
}

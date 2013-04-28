package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class PipingComponent extends PlantItem implements PlantItemContainer {

	protected ArrayList<PipingComponent> pipingComponents;
	
	public PipingComponent(String name, PipingSegment pipingSegment) {
		super(name, pipingSegment);
		this.pipingComponents = new ArrayList<PipingComponent>(); 
	}
	
	public PipingComponent(String name, PipingComponent pipingComponent) {
		super(name, pipingComponent);
		this.pipingComponents = new ArrayList<PipingComponent>(); 
	}
	
	public PipingComponent(String name, PlantItemContainer container) {
		super(name, container);
		this.pipingComponents = new ArrayList<PipingComponent>(); 
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof PipingComponent) {
			pipingComponents.add((PipingComponent) plantItem);
		}
	}
	
	public List<PipingComponent> getPipingComponents() {
		return pipingComponents;
	}
	
	@Override
	public List<PipingComponent> getPlantItems() {
		return pipingComponents;
	}

}

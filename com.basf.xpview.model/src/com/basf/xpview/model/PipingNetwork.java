package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class PipingNetwork extends PlantItem implements PlantItemContainer {

	protected ArrayList<PipingSegment> segments;

	public PipingNetwork(String name, PipingNetworkList container) {
		super(name, container);
		this.segments = new ArrayList<PipingSegment>();
	}
	
	public List<PipingSegment> getSegments() {
		return segments;
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof PipingSegment) {
			segments.add((PipingSegment) plantItem);
		}
	}
	
	@Override
	public List<PipingSegment> getPlantItems() {
		return segments;
	}
	
}

package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class PlantItemList {

	protected ArrayList<PlantItem> plantItems;
	
	public PlantItemList() {
		this.plantItems = new ArrayList<PlantItem>();
	}
	
	public List<PlantItem> getPlantItems() {
		return plantItems;
	}
	
}

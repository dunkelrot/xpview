package com.basf.xpview.model;

import java.util.ArrayList;

public class Catalog extends Thing implements PlantItemContainer {

	protected ArrayList<PlantItem> plantItems;
	
	protected CatalogList catalogList;
	
	public Catalog(CatalogList catalogList, String name) {
		super(name);
		this.catalogList = catalogList;
		this.plantItems = new ArrayList<PlantItem>();
	}
	
	@Override
	public void addPlantItem(PlantItem plantItem) {
		plantItems.add(plantItem);
	}
	
	public CatalogList getParent() {
		return catalogList;
	}
	
	public ArrayList<PlantItem> getPlantItems() {
		return plantItems;
	}
	
	public PlantItem findPlantItem(String name) {
		PlantItem result = null;
		for (PlantItem plantItem : plantItems) {
			if (plantItem.getName().equals(name)) {
				result = plantItem;
				break;
			}
		}
		return result;
	}
	
}



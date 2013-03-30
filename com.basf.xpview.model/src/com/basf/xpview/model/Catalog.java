package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Catalog is used to collect PlantItems which are defined in a symbol catalog.
 * The actual representation (the graphics) is stored via the RepresentationManager.
 * 
 * @author Arndt Teinert
 *
 */
public class Catalog extends Thing implements PlantItemContainer {

	protected ArrayList<PlantItem> plantItems;
	
	protected CatalogList catalogList;
	
	/**
	 * Constructor, nothing special about this.
	 * @param catalogList the managing catalog list
	 * @param name the name
	 */
	public Catalog(CatalogList catalogList, String name) {
		super(name);
		this.catalogList = catalogList;
		this.plantItems = new ArrayList<PlantItem>();
	}

	@Override
	public void addPlantItem(PlantItem plantItem) {
		plantItems.add(plantItem);
	}
	
	/**
	 * 
	 * @return the managing {@link CatalogList}
	 */
	public CatalogList getParent() {
		return catalogList;
	}
	
	/**
	 * 
	 * @return returns all stored {@link PlantItem}s 
	 */
	public List<PlantItem> getPlantItems() {
		return plantItems;
	}
	
	/**
	 * Finds a {@link PlantItem} by name. The first found {@link PlantItem} is
	 * returned. Note that the {@link Catalog} does not enforce that the
	 * items have different names.
	 *  
	 * @param name the name of the {@link PlantItem} to find
	 * @return the found {@link PlantItem} or null
	 */
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



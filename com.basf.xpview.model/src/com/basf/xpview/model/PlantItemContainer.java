package com.basf.xpview.model;

/**
 * Classes which contain a list of {@link PlantItem}s should implement this interface. 
 * 
 * @author Arndt Teinert
 *
 */
public interface PlantItemContainer {

	/**
	 * Adds the given {@link PlantItem} to this container.
	 * It depends on the implementation how the {@link PlantItem} is stored.
	 *  
	 * @param plantItem the item to add
	 */
	void addPlantItem(PlantItem plantItem);
}


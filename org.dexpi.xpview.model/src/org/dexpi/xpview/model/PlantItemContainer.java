package org.dexpi.xpview.model;

import java.util.List;

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
	
	List<? extends PlantItem> getPlantItems();
	
}


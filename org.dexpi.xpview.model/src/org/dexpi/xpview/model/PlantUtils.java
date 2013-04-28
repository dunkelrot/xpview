package org.dexpi.xpview.model;


/**
 * Utility functions go here. 
 * 
 * @author Arndt Teinert
 *
 */
public class PlantUtils {

	/**
	 * Tries to return the {@link Plant} for a given {@link Thing)
	 * 
	 * @param thing 
	 * @return the owning {@link Plant} or null
	 */
	static public Plant getPlant(Thing thing) {
		Plant result = null;
		if (thing instanceof Drawing) {
			Drawing drawing = (Drawing) thing;
			result = (Plant) drawing.getDrawingList().getPlantSection().getRootSection();
		}
		return result;
	}
}

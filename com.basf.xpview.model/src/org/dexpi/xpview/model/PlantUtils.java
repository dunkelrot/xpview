package com.basf.xpview.model;

public class PlantUtils {

	static public Plant getPlant(Thing thing) {
		Plant result = null;
		if (thing instanceof Drawing) {
			Drawing drawing = (Drawing) thing;
			result = (Plant) drawing.getDrawingList().getPlantSection().getRootSection();
		}
		return result;
	}
}

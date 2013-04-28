package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The list of all {@link Drawing}s which belong to a {@link PlantSection}.
 * 
 * @author Arndt Teinert
 *
 */
public class DrawingList {
	
	protected ArrayList<Drawing> drawings;
	protected PlantSection plantSection;
	
	/**
	 * Constructor.
	 * @param plantSection the owning {@link PlantSection}
	 */
	public DrawingList(PlantSection plantSection) {
		this.plantSection = plantSection;
		this.drawings = new ArrayList<Drawing>();
	}
	
	/**
	 * Returns all {@link Drawing}s
	 * @return all {@link Drawing}s
	 */
	public List<Drawing> getDrawings() {
		return drawings;
	}
	
	/**
	 * Returns the owning {@link PlantSection}
	 * @return the owning {@link PlantSection}
	 */
	public PlantSection getPlantSection() {
		return plantSection;
	}
	
	/**
	 * Adds a new {@link Drawing} to this list.
	 * @param name the name of the {@link Drawing}
	 * @return the newly created {@link Drawing}
	 */
	public Drawing addDrawing(String name) {
		Drawing drawing = new Drawing(this, name);
		drawings.add(drawing);
		return drawing;
	}
	
}

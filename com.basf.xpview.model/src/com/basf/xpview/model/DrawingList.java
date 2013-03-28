package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class DrawingList {
	
	protected ArrayList<Drawing> drawings;
	protected PlantSection plantSection;
	
	public DrawingList(PlantSection plantSection) {
		this.plantSection = plantSection;
		this.drawings = new ArrayList<Drawing>();
	}
	
	public List<Drawing> getDrawings() {
		return drawings;
	}
	
	public PlantSection getPlantSection() {
		return plantSection;
	}
	
	public Drawing addDrawing(String name) {
		Drawing drawing = new Drawing(this, name);
		drawings.add(drawing);
		return drawing;
	}
	
}

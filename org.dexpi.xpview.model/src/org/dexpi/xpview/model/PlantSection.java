package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * PlantSections are used to structure the plant in a hierarchical way. 
 * A section might contain other plant sections and plant items.
 *  
 * It is recommended to give each PlantSections a unique name, at least at
 * the same hierarchy level.
 * 
 * @author Arndt Teinert
 *
 */
public class PlantSection extends Thing {
	
	protected DrawingList drawings;
	protected ArrayList<PlantSection> sections;
	protected ArrayList<PlantItem> items;
	protected PlantSection parent;
	
	public PlantSection(String name, PlantSection parent) {
		super(name);
		this.sections = new ArrayList<PlantSection>();
		this.items = new ArrayList<PlantItem>();
		this.drawings = new DrawingList(this);
		this.parent = parent;
	}
	
	public List<PlantSection> getSections() {
		return sections;
	}
	
	public List<PlantItem> getItems() {
		return items;
	}
	
	public PlantSection getParent() {
		return parent;
	}
	
	public DrawingList getDrawings() {
		return drawings;
	}
	
	public PlantSection getRootSection() {
		if (parent instanceof Plant) {
			return parent;
		} else {
			return parent.getRootSection();
		}
	}

}

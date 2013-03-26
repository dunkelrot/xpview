package com.basf.xpview.model;

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
public class PlantSection {

	String name;
	
	protected ArrayList<PlantSection> sections;
	protected ArrayList<PlantItem> items;
	protected PlantSection parent;
	
	public PlantSection(String name, PlantSection parent) {
		this.sections = new ArrayList<PlantSection>();
		this.items = new ArrayList<PlantItem>();
		this.name = name;
		this.parent = parent;
	}
	
	public List<PlantSection> getSections() {
		return sections;
	}
	
	public List<PlantItem> getItems() {
		return items;
	}
	
	public String getName() {
		return name;
	}
	
	public PlantSection getParent() {
		return parent;
	}
}

package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An equipment is a single device like a heat exchanger, a vessel or a pump.
 * An equipment may be divided into several sub-components.
 *  
 * @author Arndt Teinert
 *
 */
public class Equipment extends PlantItem implements TaggedItem {

	protected ArrayList<EquipmentComponent> components;
	protected EquipmentList equipmentList;
	
	protected String tagId;
	
	public Equipment(String name, String tagId, EquipmentList equipmentList) {
		super(name);
		this.tagId = tagId;
		this.equipmentList = equipmentList;
		this.components = new ArrayList<EquipmentComponent>();
	}
	
	public List<EquipmentComponent> getComponents() {
		return components;
	}
	
	@Override
	public String getTagId() {
		return tagId;
	}
	
	public EquipmentList getEquipmentList() {
		return equipmentList;
	}
}

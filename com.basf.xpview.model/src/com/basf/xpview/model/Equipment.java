package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An equipment is a single device like a heat exchanger, a vessel or a pump.
 * An equipment may be divided into several sub-components and sub-equipments.
 * Zero or more nozzles may be attached to an equipment.
 *  
 * @author Arndt Teinert
 *
 */
public class Equipment extends PlantItem implements TaggedItem {

	protected ArrayList<Equipment> subEquipments;
	protected ArrayList<EquipmentComponent> components;
	protected ArrayList<Nozzle> nozzles;
	
	protected EquipmentList equipmentList;
	
	protected String tagId;
	
	public Equipment(String name, String tagId, EquipmentList equipmentList) {
		super(name);
		this.tagId = tagId;
		this.equipmentList = equipmentList;
		this.components = new ArrayList<EquipmentComponent>();
		this.subEquipments = new ArrayList<Equipment>();
		this.nozzles = new ArrayList<Nozzle>();
	}
	
	public Equipment(String name, String tagId, Equipment equipment) {
		super(name, equipment);
		this.tagId = tagId;
		this.equipmentList = equipmentList;
		this.components = new ArrayList<EquipmentComponent>();
		this.subEquipments = new ArrayList<Equipment>();
		this.nozzles = new ArrayList<Nozzle>();
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
	
	public List<Nozzle> getNozzles() {
		return nozzles;
	}
	
	public Nozzle addNozzle(String name, String tagId) {
		Nozzle nozzle = new Nozzle(this, name, tagId);
		nozzles.add(nozzle);
		return nozzle;
	}
	
	public List<Equipment> getSubEquipments() {
		return subEquipments;
	}
	
	public Equipment addSubEquipment(String name, String tagId) {
		Equipment subEquipment = new Equipment(name, tagId, this);
		subEquipments.add(subEquipment);
		return subEquipment;
	}
	
}


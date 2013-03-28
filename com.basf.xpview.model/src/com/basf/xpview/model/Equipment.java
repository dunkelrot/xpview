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
public class Equipment extends PlantItem implements TaggedItem, EquipmentContainer {

	protected ArrayList<Equipment> subEquipments;
	protected ArrayList<EquipmentComponent> components;
	protected ArrayList<Nozzle> nozzles;
	
	protected EquipmentContainer container;
	protected String tagId;
	
	public Equipment(String name, String tagId, EquipmentContainer container) {
		super(name);
		this.tagId = tagId;
		this.container = container;
		this.components = new ArrayList<EquipmentComponent>();
		this.subEquipments = new ArrayList<Equipment>();
		this.nozzles = new ArrayList<Nozzle>();
	}
	
	public Equipment(String name, String tagId, Equipment equipment) {
		super(name, equipment);
		this.tagId = tagId;
		this.container = equipment;
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
	
	/**
	 * The list of all nozzles which belong directly to this equipment.
	 * @return list of nozzles
	 */
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

	@Override
	public void addEquipment(Equipment equipment) {
		subEquipments.add(equipment);
	}
	
	public EquipmentContainer getContainer() {
		return container;
	}
	
}


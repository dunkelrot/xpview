package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An equipment is a single device like a heat exchanger, a vessel or a pump.
 * An equipment may be divided into several sub-components and sub-equipments.
 * Zero or more {@link Nozzle}s may be attached to an equipment.
 * 
 * @author Arndt Teinert
 *
 */
public class Equipment extends PlantItem implements TaggedItem, PlantItemContainer {

	protected ArrayList<Equipment> subEquipments;
	protected ArrayList<Nozzle> nozzles;
	protected String tagId;
	
	/**
	 * Constructor, nothing special about this.
	 * @param name the name for this {@link Equipment}
	 * @param tagId the TAGID for this {@link Equipment}
	 * @param container the owning container, can be null
	 */
	public Equipment(String name, String tagId, PlantItemContainer container) {
		super(name, container);
		this.tagId = tagId;
		this.subEquipments = new ArrayList<Equipment>();
		this.nozzles = new ArrayList<Nozzle>();
	}

	@Override
	public String getTagId() {
		return tagId;
	}
	
	/**
	 * The list of all {@link Nozzle}s which belong directly to this equipment.
	 * @return list of {@link Nozzle}s
	 */
	public List<Nozzle> getNozzles() {
		return nozzles;
	}
	
	/**
	 * Adds a {@link Nozzle} to this {@link Equipment}.
	 * @param name the name of the nozzle
	 * @param tagId the TAGID of the nozzle
	 * @return the newly created {@link Nozzle}
	 */
	public Nozzle addNozzle(String name, String tagId) {
		Nozzle nozzle = new Nozzle(name, tagId, this);
		nozzles.add(nozzle);
		return nozzle;
	}
	
	/**
	 * Returns the list of sub-equipments.
	 * @return the list of sub-equipments
	 */
	public List<Equipment> getSubEquipments() {
		return subEquipments;
	}
	
	/**
	 * Adds a sub-equipment to this equipment.
	 * @param name the name of the sub-equipment
	 * @param tagId the TAGID of the sub-equipment
	 * @return the newly created {@link Equipment}
	 */
	public Equipment addSubEquipment(String name, String tagId) {
		Equipment subEquipment = new Equipment(name, tagId, this);
		subEquipments.add(subEquipment);
		return subEquipment;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * The given PlantItem is handled like this:
	 * In case it is of type {@link Equipment} it is added to the list of sub-equipments.
	 * In case it is of type {@link Nozzle} it is added to the list of {@link Nozzle}s.
	 */
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof Equipment) {
			subEquipments.add((Equipment) plantItem);
		} else if (plantItem instanceof Nozzle) {
			nozzles.add((Nozzle) plantItem);
		}
	}

	@Override
	public List<Nozzle> getPlantItems() {
		return nozzles;
	}
	
}


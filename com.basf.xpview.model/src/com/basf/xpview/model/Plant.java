package com.basf.xpview.model;

/**
 * Root element of the data model.
 * 
 * @author Arndt Teinert
 *
 */
public class Plant extends PlantSection {

	protected String name;
	protected EquipmentList equipmentList;
	protected CatalogList catalogList;
	
	public Plant(String name) {
		super(name, null);
		this.equipmentList = new EquipmentList(this);
		this.catalogList = new CatalogList(this);
	}
	
	public EquipmentList getEquipmentList() {
		return equipmentList;
	}
	
	@Override
	public PlantSection getRootSection() {
		return this;
	}
	
	public CatalogList getCatalogList() {
		return catalogList;
	}
	
}

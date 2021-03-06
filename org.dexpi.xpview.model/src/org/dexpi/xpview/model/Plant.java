package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Root element of the data model.
 * 
 * @author Arndt Teinert
 *
 */
public class Plant extends PlantSection {

	protected String name;
	protected EquipmentList equipmentList;
	protected InstrumentList instrumentList;
	protected CatalogList catalogList;
	protected PipingNetworkList pipingNetworkList;
	
	public Plant(String name) {
		super(name, null);
		this.equipmentList = new EquipmentList(this);
		this.catalogList = new CatalogList(this);
		this.pipingNetworkList = new PipingNetworkList(this);
		this.instrumentList = new InstrumentList(this); 
	}
	
	public EquipmentList getEquipmentList() {
		return equipmentList;
	}
	
	public InstrumentList getInstrumentList() {
		return instrumentList;
	}
	
	public List<PlantItem> getAllPlantItems() {
		ArrayList<PlantItem> plantItems = new ArrayList<PlantItem>();
		for (Equipment equipment : equipmentList) {
			plantItems.add(equipment);
			for (Nozzle nozzle : equipment.getNozzles()) {
				plantItems.add(nozzle);
			}
		}
		for (PipingNetwork pipingNetwork : pipingNetworkList) {
			plantItems.addAll(pipingNetwork.getSegments());
			for (PipingSegment pipingSegment : pipingNetwork.getSegments()) {
				plantItems.addAll(pipingSegment.getPlantItems());
			}
		}
		return plantItems;
	}
	
	@Override
	public PlantSection getRootSection() {
		return this;
	}
	
	public CatalogList getCatalogList() {
		return catalogList;
	}
	
	public PipingNetworkList getPipingNetworkList() {
		return pipingNetworkList;
	}
	
}

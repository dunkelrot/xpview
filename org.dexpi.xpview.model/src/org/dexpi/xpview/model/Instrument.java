package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class Instrument extends PlantItem implements TaggedItem, PlantItemContainer {

	protected ArrayList<Instrument> subInstruments;
	protected String tagId;
	
	/**
	 * Constructor, nothing special about this.
	 * @param name the name for this {@link Equipment}
	 * @param tagId the TAGID for this {@link Equipment}
	 * @param container the owning container, can be null
	 */
	public Instrument(String name, String tagId, PlantItemContainer container) {
		super(name, container);
		this.tagId = tagId;
		this.subInstruments = new ArrayList<Instrument>();
	}

	@Override
	public String getTagId() {
		return tagId;
	}

	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof Instrument) {
			subInstruments.add((Instrument) plantItem);
		}
	}

	@Override
	public List<? extends PlantItem> getPlantItems() {
		return subInstruments;
	}

}

package org.dexpi.xpview.model;

import org.dexpi.xpview.model.modifier.EquipmentModifier;

/**
 * Root of the application data model.
 * 
 * @author Arndt Teinert
 *
 */
public class Workspace {

	private static Workspace instance;
	
	protected EquipmentModifier modifier;
	
	protected Plant plant;
	
	private Workspace() {
		this.modifier = new EquipmentModifier();
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(modifier);
	}
	
	public static Workspace getInstance() {
		if (instance == null) {
			instance = new Workspace();
		}
		return instance;
	}
	
	public Plant getPlant() {
		return plant;
	}
	
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
}

package com.basf.xpview.model;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.basf.xpview.model.modifier.EquipmentModifier;

public class Workspace {

	private static Workspace instance;
	
	protected EquipmentModifier modifier;
	
	protected Plant plant;
	
	private Workspace() {
		this.modifier = new EquipmentModifier();
		/*
		IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				  .getNode("com.basf.xpview.model.preferences");
		preferences.addPreferenceChangeListener(modifier);
		*/
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

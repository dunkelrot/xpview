package org.dexpi.xpview.model.modifier;


import org.eclipse.jface.util.PropertyChangeEvent;

import org.dexpi.xpview.core.Event;
import org.dexpi.xpview.core.EventManager;
import org.dexpi.xpview.model.Activator;
import org.dexpi.xpview.model.Equipment;
import org.dexpi.xpview.model.Nozzle;
import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.Workspace;
import org.dexpi.xpview.model.events.EventTypes;
import org.dexpi.xpview.model.graphics.RepresentationManager;
import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.model.graphics.SoTransformation;

public class EquipmentModifier implements org.eclipse.jface.util.IPropertyChangeListener {

	public void setPositionStatus(Plant plant, boolean enabled) {
		for (Equipment equipment : plant.getEquipmentList().getEquipments()) {
			SoNode node = RepresentationManager.getInstance().getNode(equipment);
			if (node instanceof SoTransformation) {
				SoTransformation transform = (SoTransformation) node;
				transform.getPosition().setEnabled(enabled);
			}
			
			for (Nozzle nozzle : equipment.getNozzles()) {
				SoNode nozzleNode = RepresentationManager.getInstance().getNode(nozzle);
				if (nozzleNode instanceof SoTransformation) {
					SoTransformation transform = (SoTransformation) node;
					transform.getPosition().setEnabled(enabled);
				}
			}
		}
		EventManager.getInstance().sendEvent(new Event(this, EventTypes.SceneGraphModified, null));
	}
	
	public void setPositionStatusForNozzles(Plant plant, boolean enabled) {
		for (Equipment equipment : plant.getEquipmentList().getEquipments()) {
			for (Nozzle nozzle : equipment.getNozzles()) {
				SoNode node = RepresentationManager.getInstance().getNode(nozzle);
				if (node instanceof SoTransformation) {
					SoTransformation transform = (SoTransformation) node;
					transform.getPosition().setEnabled(enabled);
				}
			}
		}
		EventManager.getInstance().sendEvent(new Event(this, EventTypes.SceneGraphModified, null));
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(Activator.AVEVA_APPLY_EQUIPMENT_POSITION)) {
			boolean enabled = ((Boolean) event.getNewValue()).booleanValue();
			setPositionStatus(Workspace.getInstance().getPlant(), enabled);
		}
		if (event.getProperty().equals(Activator.AUTODESK_APPLY_EQUIPMENT_POSITION)) {
			boolean enabled = ((Boolean) event.getNewValue()).booleanValue();
			setPositionStatusForNozzles(Workspace.getInstance().getPlant(), enabled);
		}
	}
	
}

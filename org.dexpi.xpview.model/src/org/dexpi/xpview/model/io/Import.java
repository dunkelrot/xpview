package org.dexpi.xpview.model.io;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.graphics.RepresentationManager;

public abstract class Import {

	protected boolean equipmentPositionEnabled = false;
	protected boolean textPositionEnabled = true;
	protected boolean labelPositionEnabled = false;
	
	protected boolean flatSoEquipment = false;
	protected boolean useCatalog = true;
	
	abstract public boolean canRead(File file);
	
	abstract public Plant read(File file, RepresentationManager repManager, IProgressMonitor progressMonitor) throws Exception;
	
	public void setEquipmentPositionEnabled(boolean equipmentPositionEnabled) {
		this.equipmentPositionEnabled = equipmentPositionEnabled;
	}
	
	public boolean isEquipmentPositionEnabled() {
		return equipmentPositionEnabled;
	}
	
	public void setLabelPositionEnabled(boolean labelPositionEnabled) {
		this.labelPositionEnabled = labelPositionEnabled;
	}
	
	public void setTextPositionEnabled(boolean textPositionEnabled) {
		this.textPositionEnabled = textPositionEnabled;
	}
	
	public boolean isLabelPositionEnabled() {
		return labelPositionEnabled;
	}
	
	public boolean isTextPositionEnabled() {
		return textPositionEnabled;
	}
	
	public void setFlatSoEquipment(boolean flatSoEquipment) {
		this.flatSoEquipment = flatSoEquipment;
	}
	
	public boolean isFlatSoEquipment() {
		return flatSoEquipment;
	}
	
	public boolean isUseCatalog() {
		return useCatalog;
	}
	
	public void setUseCatalog(boolean useCatalog) {
		this.useCatalog = useCatalog;
	}
}

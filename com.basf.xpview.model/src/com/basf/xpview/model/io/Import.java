package com.basf.xpview.model.io;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import com.basf.xpview.model.Plant;
import com.basf.xpview.model.graphics.RepresentationManager;

public abstract class Import {

	protected boolean equipmentPositionEnabled = false;
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

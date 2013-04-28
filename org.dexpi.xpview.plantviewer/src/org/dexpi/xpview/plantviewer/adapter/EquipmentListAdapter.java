package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.EquipmentList;
import org.dexpi.xpview.plantviewer.Activator;

public class EquipmentListAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;

	public EquipmentListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/package.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		EquipmentList equipmentList = (EquipmentList) o;
		return equipmentList.getEquipments().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		return "Equipments";
	}

	@Override
	public Object getParent(Object o) {
		EquipmentList equipmentList = (EquipmentList) o;
		return equipmentList.getPlant();
	}

}

package com.basf.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.EquipmentList;

public class EquipmentListAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		EquipmentList equipmentList = (EquipmentList) o;
		return equipmentList.getEquipments().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
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
